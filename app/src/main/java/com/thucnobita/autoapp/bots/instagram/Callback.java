package com.thucnobita.autoapp.bots.instagram;

import android.content.DialogInterface;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.responses.accounts.LoginResponse;

import java.util.concurrent.Callable;

public interface Callback {
    interface Login{
        Callable<String> getCode();
        void success(String message);
        void fail(String message);
    }
    interface Media{
        void success(String linkVideo);
        void fail(String message);
    }
}
