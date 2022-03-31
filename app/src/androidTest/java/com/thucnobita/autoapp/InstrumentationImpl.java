package com.thucnobita.autoapp;

import android.app.Instrumentation;

import androidx.test.platform.app.InstrumentationRegistry;

public class InstrumentationImpl {
    private Instrumentation mInstrumentation = null;
    public InstrumentationImpl(){
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
    }

    public Instrumentation getmInstrumentation(){
        return mInstrumentation;
    }

}
