package com.thucnobita.autoapp.bots.instagram;

import android.content.Intent;
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
import java.util.ArrayList;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

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

    public void getLinks(String stringData, @NonNull final RequestHandleCallback requestHandleCallback) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (stringData.matches("https://www.instagram.com/(.*)")) {
            String[] data = stringData.split(Pattern.quote("?"));
            String string = data[0];
            AsyncHttpClient client = new AsyncHttpClient();
            RequestHandle result = client.get(string + "?__a=1", null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String res = new String(responseBody);
                    try {
                        String link = null;
                        JSONObject jsonObject = new JSONObject(res);
                        JSONObject objectGraphql = jsonObject.getJSONObject("graphql");
                        JSONObject objectMedia = objectGraphql.getJSONObject("shortcode_media");
                        boolean isVideo = objectMedia.getBoolean("is_video");
                        if (isVideo) {
                            link = objectMedia.getString("video_url");
                        } else {
                            link = objectMedia.getString("display_url");
                        }
                        arrayList.add(link);
                        try {
                            arrayList.clear();
                            JSONObject objectSidecar = objectMedia.getJSONObject("edge_sidecar_to_children");
                            JSONArray jsonArray = objectSidecar.getJSONArray("edges");
                            String edgeSidecar = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                JSONObject node = object.getJSONObject("node");
                                boolean is_video_group = node.getBoolean("is_video");
                                if (is_video_group) {
                                    edgeSidecar = node.getString("video_url");
                                } else {
                                    edgeSidecar = node.getString("display_url");
                                }
                                arrayList.add(edgeSidecar);
                            }
                            requestHandleCallback.onSuccess(arrayList, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            requestHandleCallback.onSuccess(arrayList, e.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        requestHandleCallback.onSuccess(arrayList, e.toString());
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    requestHandleCallback.onFailure(arrayList, error.getMessage());
                }
            });
        } else {
        }
    }

    public String object2String(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
