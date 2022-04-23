package com.thucnobita.autoapp.utils;

public interface Callback {
    interface Log{
        void begin();
        void write(String message);
        void done();
        void error(String message);
    }
}
