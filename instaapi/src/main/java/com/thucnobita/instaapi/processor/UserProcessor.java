package com.thucnobita.instaapi.processor;

import com.thucnobita.instaapi.IGRequest;
import com.thucnobita.instaapi.app.Cookie;
import com.thucnobita.instaapi.model.login.IGLoggedUser;
import com.thucnobita.instaapi.request.IGFollowRequest;
import com.thucnobita.instaapi.request.IGUnFollowRequest;
import com.thucnobita.instaapi.response.IGFollowResponse;
import com.thucnobita.instaapi.response.IGPostsResponse;
import com.thucnobita.instaapi.response.IGUnfollowResponse;
import com.thucnobita.instaapi.response.IGUserInfoResponse;
import com.thucnobita.instaapi.utils.Utilities;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class UserProcessor {

    private final IGRequest igRequest;

    public UserProcessor(IGRequest igRequest){
        this.igRequest = igRequest;
    }


    public Observable<IGUnfollowResponse> unFollow(long userId){
        if(igRequest.getLoggedUser() == null){
            throw new RuntimeException("You must login first");
        }
        Cookie cookie = igRequest.getCookie();
        IGLoggedUser loggedUser = igRequest.getLoggedUser();
        IGUnFollowRequest unFollowRequest = new IGUnFollowRequest();
        unFollowRequest.setCsrfToken(cookie.getCsrftoken());
        unFollowRequest.setRadioType("wifi-none");
        unFollowRequest.setSurface("profile");
        unFollowRequest.setUid(loggedUser.getPk());
        unFollowRequest.setUuid(cookie.getAdid());
        unFollowRequest.setUserId(userId);
        return igRequest.getRemote().unfollow(igRequest.getHeaders(),userId,igRequest.getSignaturePayload(unFollowRequest))
                .subscribeOn(Schedulers.io());
    }

    public Observable<IGFollowResponse> follow(long userId){
        if(igRequest.getLoggedUser() == null){
            throw new RuntimeException("You must login first");
        }
        Cookie cookie = igRequest.getCookie();
        IGLoggedUser loggedUser = igRequest.getLoggedUser();
        IGFollowRequest followRequest = new IGFollowRequest();
        followRequest.setCsrfToken(cookie.getCsrftoken());
        followRequest.setRadioType("wifi-none");
        followRequest.setUid(loggedUser.getPk());
        followRequest.setUuid(cookie.getAdid());
        followRequest.setDeviceId(cookie.getDeviceID());
        followRequest.setUserId(userId);
        return igRequest.getRemote().follow(igRequest.getHeaders(),userId,igRequest.getSignaturePayload(followRequest))
                .subscribeOn(Schedulers.io());
    }

    public Observable<IGUserInfoResponse> getUserInfo(long userId) {
        if(igRequest.getLoggedUser() == null){
            throw new RuntimeException("You must login first");
        }
        return igRequest.getRemote().getUserInfo(igRequest.getHeaders(), userId)
                .subscribeOn(Schedulers.io());
    }
    public Observable<IGUserInfoResponse> getMe() {
        if(igRequest.getLoggedUser() == null){
            throw new RuntimeException("You must login first");
        }
        return igRequest.getRemote().getUserInfo(igRequest.getHeaders(), igRequest.getLoggedUser().getPk())
                .subscribeOn(Schedulers.io());
    }

    public Observable<IGPostsResponse> getPosts(long userId){
        return igRequest.getRemote().getUserPosts(igRequest.getHeaders(),userId,false,false)
                .subscribeOn(Schedulers.io());
    }
     public Observable<IGPostsResponse> getMorePosts(long userId,String periviosPostId){
        return igRequest.getRemote().getMorePosts(igRequest.getHeaders(),userId,false,false,periviosPostId)
                .subscribeOn(Schedulers.io());
     }

    public Observable<IGUserInfoResponse> getUserInfoByUsername(String username){
        if(igRequest.getLoggedUser() == null){
            throw new RuntimeException("You must login first");
        }
        return igRequest.getRemote().getUsernameInfo(igRequest.getHeaders(),username,"feed_timeline")
                .subscribeOn(Schedulers.io());
    }
    public Observable<ResponseBody> search(String query, int countOfResult){
        if(igRequest.getLoggedUser() == null){
            throw new RuntimeException("You must login first");
        }
        return igRequest.getRemote().searchUser(igRequest.getHeaders(),"user_search_page", Utilities.getTimeZoneOffset(),countOfResult,query)
                .subscribeOn(Schedulers.io());
    }

}
