package com.thucnobita.autoapp.utils;

import android.os.Environment;

public interface Constants {
    String PACKAGE_NAME_INSTAGRAM = "com.instagram.android";
    // Folder
    String FOLDER_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    String FOLDER_NAME_APP = "auto-app";
    String FOLDER_NAME_SESSION = "sessions";
    String FOLDER_NAME_ACCOUNT = "accounts";
    String FOLDER_NAME_UPLOAD = "Instagram";
    String FOLDER_NAME_IMAGE = "images";
//    String FOLDER_NAME_CREDENTIAL = "credentials";
}
