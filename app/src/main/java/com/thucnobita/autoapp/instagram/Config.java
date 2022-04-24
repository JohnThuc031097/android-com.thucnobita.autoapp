package com.thucnobita.autoapp.instagram;

import android.os.Environment;

public interface Config {
    String PACKAGE_NAME = "com.instagram.android";
    String DOMAIN = "www.instagram.com";

    String FOLDER_ROOT = Environment.getExternalStorageDirectory().getPath();
    String FOLDER_NAME_APP = "auto-app";
    String FOLDER_NAME_SESSION = "sessions";
    String FOLDER_NAME_VIDEOS = "videos";
}
