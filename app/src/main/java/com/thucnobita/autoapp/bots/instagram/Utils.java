package com.thucnobita.autoapp.bots.instagram;

import android.content.Intent;
import android.net.Credentials;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.thucnobita.autoapp.interfaces.RequestHandleCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.InstagramFactory;
import me.postaddict.instagram.scraper.cookie.CookieHashSet;
import me.postaddict.instagram.scraper.cookie.DefaultCookieJar;
import me.postaddict.instagram.scraper.interceptor.ErrorInterceptor;
import me.postaddict.instagram.scraper.interceptor.UserAgentInterceptor;
import me.postaddict.instagram.scraper.interceptor.UserAgents;
import me.postaddict.instagram.scraper.model.Media;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class Utils {
    private RequestHandleCallback requestHandleCallback;

    public Utils(){
    }

    public Intent shareVideo(String type, String videoPath){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File videoFile = new File(Environment.getExternalStorageDirectory().getPath() + videoPath);
        Uri uri = Uri.fromFile(videoFile);

        Intent intentVideo = new Intent();
        intentVideo.setAction(Intent.ACTION_SEND);
        intentVideo.setType(type); // image/* or video/* or text/plain
        intentVideo.putExtra(Intent.EXTRA_STREAM, uri);
        intentVideo.setPackage(Configs.INSTAGRAM_PACKAGE_NAME);
        return intentVideo;
    }

    public static String getLinkVideo(String code, String[] account) throws IOException {
//        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36";
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new UserAgentInterceptor(UserAgents.OSX_CHROME))
//                .addInterceptor(new UserAgentInterceptor(userAgent))
                .addInterceptor(new ErrorInterceptor())
                .cookieJar(new DefaultCookieJar(new CookieHashSet()))
                .build();
        Instagram client = new Instagram(httpClient);
        client.basePage();
        client.login(account[0], account[1]);
        client.basePage();
        Media media = client.getMediaByCode(code);
        return media.getVideoUrl();
    }

    public String object2String(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
