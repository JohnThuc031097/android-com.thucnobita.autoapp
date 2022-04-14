package com.thucnobita.autoapp.bots.instagram;

import android.content.DialogInterface;

public interface Callback {
    interface Login{
        void successful(String message);
        void failed(String message);
    }
    interface Media{
        void successful(String linkVideo);
        void failed(String message);
    }
}
