package com.thucnobita.autoapp.instagram;

import java.util.concurrent.Callable;

public interface Callback {
    interface Login{
        Callable<String> getCode();
        void success(String message);
        void fail(String message);
    }
    interface Media{
        void success(String linkMedia);
        void fail(String message);
    }
}
