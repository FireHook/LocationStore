package com.firehook.locationstore.mvp.rx;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Vladyslav Bondar on 03.03.2019
 * Skype: diginital
 */

public final class RxBus {

    private static final BehaviorSubject<Object> behaviorSubject = BehaviorSubject.create();

    public static Disposable subscribe(@NonNull Consumer<Object> object, @NonNull Consumer<Throwable> throwable) {
        return behaviorSubject.subscribe(object, throwable);
    }

    public static void publish(@NonNull Object message) {
        behaviorSubject.onNext(message);
    }
}
