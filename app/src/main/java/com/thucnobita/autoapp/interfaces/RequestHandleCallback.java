package com.thucnobita.autoapp.interfaces;

import java.io.Serializable;
import java.util.ArrayList;

public interface RequestHandleCallback extends Serializable {
    default void onSuccess(ArrayList<String> arrayList, String error) {
    }

    default void onFailure(ArrayList<String> arrayList, String error) {
    }

}
