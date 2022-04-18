package com.thucnobita.api.instagram.actions.feed;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.requests.IGPaginatedRequest;
import com.thucnobita.api.instagram.requests.IGRequest;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;

public class FeedIterator<T extends IGRequest<R> & IGPaginatedRequest, R extends IGResponse & IGPaginatedResponse>
        extends CursorIterator<IGRequest<R>, R> {

    public FeedIterator(IGClient client, T t) {
        super(client, t, (request, id) -> t.setMax_id(id), (e) -> e.getNext_max_id(), (e) -> e.isMore_available());
    }

}
