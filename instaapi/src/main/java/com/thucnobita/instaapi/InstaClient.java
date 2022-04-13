package com.thucnobita.instaapi;

import android.content.Context;

import com.thucnobita.instaapi.app.Cookie;
import com.thucnobita.instaapi.model.login.IGLoggedUser;
import com.thucnobita.instaapi.model.user.CurrentUserCache;
import com.thucnobita.instaapi.processor.AccountProcessor;
import com.thucnobita.instaapi.processor.CommentProcessor;
import com.thucnobita.instaapi.processor.DirectProcessor;
import com.thucnobita.instaapi.processor.MediaProcessor;
import com.thucnobita.instaapi.processor.StoryProcessor;
import com.thucnobita.instaapi.processor.UserProcessor;
import com.thucnobita.instaapi.utils.StorageUtils;

public class InstaClient {

    private static InstaClient client;

    public final AccountProcessor accountProcessor;
    public final StoryProcessor storyProcessor;
    public final CommentProcessor commentProcessor;
    public final MediaProcessor mediaProcessor;
    public final UserProcessor userProcessor;
    public final DirectProcessor directProcessor;
    private IGRequest igRequest;


    public InstaClient(Context context, String username, String password) {
        this.igRequest = new IGRequest(context, username, password);
        this.accountProcessor = new AccountProcessor(igRequest);
        this.storyProcessor = new StoryProcessor(igRequest);
        this.commentProcessor = new CommentProcessor(igRequest);
        this.mediaProcessor = new MediaProcessor(igRequest);
        this.userProcessor = new UserProcessor(igRequest);
        this.directProcessor = new DirectProcessor(igRequest);
    }

    public static InstaClient getInstanceCurrentUser(Context context){
        CurrentUserCache userCache = currentUser(context);
        if(client == null || !client.getLoggedUser().getUsername().equals(userCache.getUsername())){
            client = new InstaClient(context,userCache.getUsername(),userCache.getPassword());
        }
        return client;
    }
    public static CurrentUserCache currentUser(Context context) {
        return StorageUtils.getCurrentUser(context);
    }

    public IGLoggedUser getLoggedUser() {
        return igRequest.getLoggedUser();
    }

    public Cookie getCookie() { return igRequest.getCookie(); }
}
