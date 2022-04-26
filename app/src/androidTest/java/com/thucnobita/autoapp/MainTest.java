package com.thucnobita.autoapp;

import androidx.test.filters.LargeTest;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 23)
public class MainTest {
    @Test
    @LargeTest
    public void loadTest() throws InterruptedException {
        while (true){
            Thread.sleep(1000);
        }
    }
}