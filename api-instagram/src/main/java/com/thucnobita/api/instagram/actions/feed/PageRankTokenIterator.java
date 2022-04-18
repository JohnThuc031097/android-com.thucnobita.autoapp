package com.thucnobita.api.instagram.actions.feed;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.requests.IGPageRankTokenRequest;
import com.thucnobita.api.instagram.requests.IGRequest;
import com.thucnobita.api.instagram.responses.IGPageRankTokenResponse;
import com.thucnobita.api.instagram.responses.IGResponse;

public class PageRankTokenIterator<T extends IGRequest<R> & IGPageRankTokenRequest, R extends IGResponse & IGPageRankTokenResponse>
        extends CursorIterator<IGRequest<R>, R> {
    
    public PageRankTokenIterator(IGClient client, T t) {
        super(client, t, (req, id) -> IGPageRankTokenResponse.setFromFormat((IGPageRankTokenRequest) req, id), (res) -> res.toNextId(), (res) -> res.isHas_more());
    }
    
}
