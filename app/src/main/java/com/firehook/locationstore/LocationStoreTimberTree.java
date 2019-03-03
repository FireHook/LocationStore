package com.firehook.locationstore;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

import timber.log.Timber;

/**
 * Created by Vladyslav Bondar on 26.02.2019
 * Skype: diginital
 */

public class LocationStoreTimberTree extends Timber.DebugTree {

    @Override protected @Nullable String createStackElementTag(@NotNull StackTraceElement element) {

        return String.format(Locale.getDefault(),
                "[TIMBER] %s.%s() [#%d]",
                super.createStackElementTag(element),
                element.getMethodName(),
                element.getLineNumber());
    }
}
