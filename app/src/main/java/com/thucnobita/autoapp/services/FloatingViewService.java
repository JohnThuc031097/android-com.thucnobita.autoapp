package com.thucnobita.autoapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.EasyFloatInitializer;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnPermissionResult;
import com.lzf.easyfloat.interfaces.OnTouchRangeListener;
import com.lzf.easyfloat.permission.PermissionUtils;
import com.lzf.easyfloat.utils.DragUtils;
import com.lzf.easyfloat.widget.BaseSwitchView;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.activities.MainActivity;

import java.util.List;
import java.util.Objects;

public class FloatingViewService extends Service {

    private boolean canClick = true;

    public FloatingViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EasyFloat.with(this)
                .setTag("FloatingViewService")
                .setLayout(R.layout.floating_view)
                .setShowPattern(ShowPattern.BACKGROUND)
                .setGravity(Gravity.START, 0,100)
                .setSidePattern(SidePattern.RESULT_HORIZONTAL)
                .registerCallbacks(new OnFloatCallbacks() {

                    @Override
                    public void createdResult(boolean easyFloatResult, String s, View view) {
                    }

                    @Override
                    public void show(View view) {

                    }

                    @Override
                    public void hide(View view) {

                    }

                    @Override
                    public void dismiss() {

                    }

                    @Override
                    public void touchEvent(View view, MotionEvent motionEvent) {
                        if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                            if(canClick){
                                Intent intent = new Intent(FloatingViewService.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                stopSelf();
                            }
                        }
                    }

                    @Override
                    public void drag(View view, MotionEvent motionEvent) {
                        canClick = false;
                        DragUtils.INSTANCE.registerDragClose(motionEvent, new OnTouchRangeListener() {
                            @Override
                            public void touchInRange(boolean inRange, BaseSwitchView baseSwitchView) {
                                baseSwitchView.<TextView>findViewById(com.lzf.easyfloat.R.id.tv_delete).setText(
                                        inRange ? "Accept delete" : "Drag here"
                                );
                                baseSwitchView.<ImageView>findViewById(com.lzf.easyfloat.R.id.iv_delete).setImageResource(
                                        inRange ? com.lzf.easyfloat.R.drawable.icon_delete_selected : com.lzf.easyfloat.R.drawable.icon_delete_normal
                                );
                            }

                            @Override
                            public void touchUpInRange() {
                                EasyFloat.dismiss("FloatingViewService");
                            }
                        }, com.lzf.easyfloat.R.layout.default_close_layout, ShowPattern.BACKGROUND);
                    }

                    @Override
                    public void dragEnd(View view) {
                        canClick = true;
                    }
                }).show();
    }
}