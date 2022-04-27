package com.thucnobita.autoapp.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.services.FloatingViewService;


public class MainActivity extends AppCompatActivity   {
    private ConstraintLayout mainActivity;
    private Button btnFloatingView;

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private boolean isComfirmPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFloatingView = findViewById(R.id.btnbtnFloatingView);

    }

    public void handleBtnFloatingView(View v){
        if(!Settings.canDrawOverlays(this)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            intent.putExtra("REQUEST_CODE", CODE_DRAW_OVER_OTHER_APP_PERMISSION);
            openActivityResultLauncher(intent);
        }else{
            initializeView();
        }
    }

    private void initializeView() {
        startService(new Intent(MainActivity.this, FloatingViewService.class));
        finish();
    }

    private void openActivityResultLauncher(Intent intent){
        registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent intentResult = result.getData();
                    if (intentResult != null) {
                        Bundle bundle = intentResult.getExtras();
                        if (bundle != null) {
                            if (bundle.getInt("REQUEST_CODE") == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
                                if (result.getResultCode() == RESULT_OK) {
                                    initializeView();
                                } else { //Permission is not available
                                    Toast.makeText(this,
                                            "Draw over other app permission not available. Closing the application",
                                            Toast.LENGTH_SHORT).show();

                                    finish();
                                }
                            }
                        }
                    }

                }).launch(intent);
    }

}