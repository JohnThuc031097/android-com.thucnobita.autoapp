package com.thucnobita.autoapp.utils;

import android.content.Context;
import android.view.Gravity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.thucnobita.autoapp.R;

import java.io.File;
import java.io.IOException;

public class Util {
    private Util(){

    }
    public static String object2String(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public static <T> T file2Object(File src, Class<T> valueType) throws IOException {
        return new ObjectMapper().readValue(src, valueType);
    }

    public static void object2File(File resultFile, Object obj) throws IOException {
        new ObjectMapper().writeValue(resultFile, obj);
    }

    public static JsonNode string2Json(String src) throws JsonProcessingException {
        return new ObjectMapper().readTree(src);
    }

    public static <T> T string2Object(String data, Class<T> valueType) throws JsonProcessingException {
        return new ObjectMapper().readValue(data, valueType);
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
