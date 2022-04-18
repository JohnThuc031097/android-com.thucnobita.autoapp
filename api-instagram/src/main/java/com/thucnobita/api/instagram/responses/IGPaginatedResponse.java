package com.thucnobita.api.instagram.responses;

public interface IGPaginatedResponse {
    String getNext_max_id();

    boolean isMore_available();
}
