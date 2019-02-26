package com.firehook.locationstore.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public abstract class FirestoreAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements EventListener<QuerySnapshot> {

    private Query mQuery;
    private ListenerRegistration mRegistration;

    private ArrayList<DocumentSnapshot> mSnapshots = new ArrayList<>();

    public FirestoreAdapter(Query query) {
        mQuery = query;
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            onError(e);
            return;
        }
        for (DocumentChange change : queryDocumentSnapshots.getDocumentChanges()) {

            if (change.getType().equals(DocumentChange.Type.ADDED)) {
                onDocumentAdded(change);
            } else {
                if (change.getType().equals(DocumentChange.Type.REMOVED)) {
                    onDocumentRemoved(change);
                }
            }
        }
        onDataChanged();
    }

    public void startListening() {
        if (mQuery != null && mRegistration == null) {
            mRegistration = mQuery.addSnapshotListener(this);
        }
    }

    public void stopListening() {
        if (mRegistration != null) {
            mRegistration.remove();
            mRegistration = null;
        }

        mSnapshots.clear();
        notifyDataSetChanged();
    }

    public void setQuery(Query query) {
        stopListening();

        mSnapshots.clear();
        notifyDataSetChanged();

        mQuery = query;
        startListening();
    }

    @Override
    public int getItemCount() {
        return mSnapshots.size();
    }

    protected DocumentSnapshot getSnapshot(int index) {
        return mSnapshots.get(index);
    }

    protected void onDocumentAdded(DocumentChange change) {
        mSnapshots.add(change.getNewIndex(), change.getDocument());
        notifyItemInserted(change.getNewIndex());
    }

    protected void onDocumentRemoved(DocumentChange change) {
        mSnapshots.remove(change.getOldIndex());
        notifyItemRemoved(change.getOldIndex());
    }

    protected void onError(FirebaseFirestoreException e) {

    }

    protected void onDataChanged() {}

}
