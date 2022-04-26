package com.thucnobita.autoapp.instagram;

import android.os.Environment;

public interface Constants {
    String PACKAGE_NAME_APP = "com.thucnobita.autoapp";
    String PACKAGE_NAME_INSTAGRAM = "com.instagram.android";
    String DOMAIN = "www.instagram.com";
    // Folder
    String FOLDER_ROOT = Environment.getExternalStorageDirectory().getPath();
    String FOLDER_NAME_APP = "auto-app";
    String FOLDER_NAME_SESSION = "sessions";
    String FOLDER_NAME_VIDEOS = "videos";
    // File
    String FILE_CONFIGS = "configs.json";
}
