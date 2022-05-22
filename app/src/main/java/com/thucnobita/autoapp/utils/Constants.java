package com.thucnobita.autoapp.utils;

import android.os.Environment;

public interface Constants {
    String PACKAGE_NAME_INSTAGRAM = "com.instagram.android";
    // Folder
    String FOLDER_ROOT = Environment.getExternalStorageDirectory().getPath();
    String FOLDER_NAME_APP = "auto-app";
    String FOLDER_NAME_SESSION = "sessions";
    String FOLDER_NAME_ACCOUNT = "accounts";
    String FOLDER_NAME_GOOGLE_API = "google-api";
}
