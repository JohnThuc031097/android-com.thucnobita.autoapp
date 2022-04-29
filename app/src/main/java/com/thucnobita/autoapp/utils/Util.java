package com.thucnobita.autoapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnTouchRangeListener;
import com.lzf.easyfloat.utils.DragUtils;
import com.lzf.easyfloat.widget.BaseSwitchView;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.activities.MainActivity;

import java.io.File;
import java.io.IOException;

public class Util {
    private Util(){

    }
    public static String object2String(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public static JsonNode string2Json(String data) throws JsonProcessingException {
        return new ObjectMapper().readTree(data);
    }

    public static JsonNode string2Json(File file) throws IOException {
        return new ObjectMapper().readTree(file);
    }

    public static void createFloatingView(Context context, String tagName, OnFloatCallbacks onFloatCallbacks){
        EasyFloat.with(context)
                .setTag(tagName)
                .setLayout(R.layout.floating_view)
                .setShowPattern(ShowPattern.BACKGROUND)
                .setGravity(Gravity.START, 0,100)
                .setSidePattern(SidePattern.RESULT_HORIZONTAL)
                .registerCallbacks(onFloatCallbacks)
                .show();
    }

}
