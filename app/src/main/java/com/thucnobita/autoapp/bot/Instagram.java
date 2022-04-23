package com.thucnobita.autoapp.bot;

import com.thucnobita.autoapp.utils.Callback;

import java.util.ArrayList;
import java.util.Random;

public class Instagram {
    private com.thucnobita.bot.instagram.Instagram mInstagram;

    public Instagram(com.thucnobita.bot.instagram.Instagram instagram){
        this.mInstagram = instagram;
    }

    public void step1(Callback.Log callback){
        try{
            callback.begin();
            mInstagram.action(com.thucnobita.bot.instagram.Instagram.ACTION.click_profile);
            callback.write("=> Click profile => Ok");
            Thread.sleep(1000);
            mInstagram.action(com.thucnobita.bot.instagram.Instagram.ACTION.click_options);
            callback.write("=> Click options => Ok");
            Thread.sleep(1000);
            mInstagram.action(com.thucnobita.bot.instagram.Instagram.ACTION.click_saved);
            callback.write("=> Click saved => Ok");
            Thread.sleep(1000);
            ArrayList<String> idObjVideos = (ArrayList<String>) mInstagram.action(com.thucnobita.bot.instagram.Instagram.ACTION.get_videos_saved);
            if(idObjVideos.size() > 0){
                callback.write("=> Total videos saved:" + idObjVideos.size());
                int totalVideo = idObjVideos.size();
                int indexVideo = 0;
                if(totalVideo > 1){
                    indexVideo = new Random().nextInt(totalVideo);
                    indexVideo = indexVideo > 0 ? (indexVideo -1) : 0;
                }
                boolean clickVideo = (boolean) mInstagram.action(com.thucnobita.bot.instagram.Instagram.ACTION.click_video_saved, indexVideo, idObjVideos);
                callback.write("=> Click video " + indexVideo + " => " + clickVideo);
                Thread.sleep(1000);
                mInstagram.action(com.thucnobita.bot.instagram.Instagram.ACTION.click_get_link_video_saved);
                callback.write("=> Click get link video => Ok");
                callback.done();
            }
        }catch (Exception e){
            e.printStackTrace();
            callback.error(e.toString());
        }
    }

    public void step2(){

    }

    public void step3(){

    }
}
