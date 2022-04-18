package com.thucnobita.api.instagram.responses;

import com.thucnobita.api.instagram.requests.IGPageRankTokenRequest;

public interface IGPageRankTokenResponse {
    String getRank_token();
    
    String getPage_token();
    
    boolean isHas_more();
    
    public static void setFromFormat(IGPageRankTokenRequest request, String id) {
        String[] tokens = id.split(":", 2);
        
        request.setRank_token(tokens[0]);
        request.setPage_token(tokens[1]);
    }
    
    default String toNextId() {
        return String.join(":", getRank_token(), getPage_token());
    }
    
}
