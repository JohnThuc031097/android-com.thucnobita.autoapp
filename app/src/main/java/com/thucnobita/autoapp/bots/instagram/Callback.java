package com.thucnobita.autoapp.bots.instagram;

import android.content.DialogInterface;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.responses.accounts.LoginResponse;

import java.util.concurrent.Callable;

public interface Callback {
    interface Login{
        LoginResponse inputCode(IGClient client,LoginResponse response);
        void successful(String message);
        void failed(String message);
    }
    interface Media{
        void successful(String linkVideo);
        void failed(String message);
    }
}
