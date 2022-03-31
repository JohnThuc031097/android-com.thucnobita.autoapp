package com.thucnobita.autoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private TextView txtOutput;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtOutput = findViewById(R.id.txtOutput);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init(){
        runOnUiThread(()->{
            try{
                Class<?> clsInstrumentation = Class.forName("com.thucnobita.autoapp.InstrumentationImpl");
                Object obj = clsInstrumentation.newInstance();
                Method mGetInstrumentation = obj.getClass().getDeclaredMethod("getmInstrumentation");
                Instrumentation instrumentation = (Instrumentation) mGetInstrumentation.invoke(obj);
                txtOutput.setText(instrumentation.getProcessName());
            }catch (Exception e){
                e.printStackTrace();
                txtOutput.setText(e.toString());
            }
        });
    }
}