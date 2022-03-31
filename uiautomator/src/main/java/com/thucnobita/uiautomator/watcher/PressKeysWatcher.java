/*
 * The MIT License (MIT)
 * Copyright (c) 2015 xiaocong@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.thucnobita.uiautomator.watcher;

import android.app.Instrumentation;
import android.os.RemoteException;
import android.view.KeyEvent;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiSelector;

import com.thucnobita.uiautomator.Log;

/**
 * Created with IntelliJ IDEA.
 * User: xiaocong@gmail.com
 * Date: 8/21/13
 * Time: 4:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class PressKeysWatcher extends SelectorWatcher{
    private String[] keys = new String[]{};
    private UiDevice device = null;

    public PressKeysWatcher(Instrumentation instrumentation, UiSelector[] conditions, String[] keys) {
        super(instrumentation, conditions);
        this.keys = keys;
        this.device = UiDevice.getInstance(instrumentation);
    }

    @Override
    public void action() {
        Log.d("PressKeysWatcher triggered!");
        for (String key: keys) {
            key = key.toLowerCase();
            switch (key) {
                case "home":
                    this.device.pressHome();
                    break;
                case "back":
                    this.device.pressBack();
                    break;
                case "left":
                    this.device.pressDPadLeft();
                    break;
                case "right":
                    this.device.pressDPadRight();
                    break;
                case "up":
                    this.device.pressDPadUp();
                    break;
                case "down":
                    this.device.pressDPadDown();
                    break;
                case "center":
                    this.device.pressDPadCenter();
                    break;
                case "menu":
                    this.device.pressMenu();
                    break;
                case "search":
                    this.device.pressSearch();
                    break;
                case "enter":
                    this.device.pressEnter();
                    break;
                case "delete":
                case "del":
                    this.device.pressDelete();
                    break;
                case "recent":
                    try {
                        this.device.pressRecentApps();
                    } catch (RemoteException e) {
                        Log.d(e.getMessage());
                    }
                    break;
                case "volume_up":
                    this.device.pressKeyCode(KeyEvent.KEYCODE_VOLUME_UP);
                    break;
                case "volume_down":
                    this.device.pressKeyCode(KeyEvent.KEYCODE_VOLUME_DOWN);
                    break;
                case "volume_mute":
                    this.device.pressKeyCode(KeyEvent.KEYCODE_VOLUME_MUTE);
                    break;
                case "camera":
                    this.device.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
                    break;
                case "power":
                    this.device.pressKeyCode(KeyEvent.KEYCODE_POWER);
                    break;
            }
        }
    }
}
