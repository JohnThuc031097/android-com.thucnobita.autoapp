package com.thucnobita.api.instagram.responses.usertags;

import java.util.List;

import com.thucnobita.api.instagram.models.media.timeline.TimelineMedia;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class UserTagsFeedResponse extends IGResponse implements IGPaginatedResponse{

	private List<TimelineMedia> items;
	
	private int num_results;
	private String next_max_id;
	private boolean more_available;
	
	
}
