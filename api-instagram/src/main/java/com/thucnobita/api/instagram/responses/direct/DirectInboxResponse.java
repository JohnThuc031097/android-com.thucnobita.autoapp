package com.thucnobita.api.instagram.responses.direct;

import com.thucnobita.api.instagram.models.direct.Inbox;
import com.thucnobita.api.instagram.models.user.User;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class DirectInboxResponse extends IGResponse {
    private User viewer;
    private Inbox inbox;
    private int seq_id;
    private int pending_requests_total;
    private User most_recent_inviter;
}
