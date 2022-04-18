package com.thucnobita.api.instagram.responses.discover;

import java.util.List;
import com.thucnobita.api.instagram.models.discover.Cluster;
import com.thucnobita.api.instagram.models.discover.SectionalItem;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class DiscoverTopicalExploreResponse extends IGResponse implements IGPaginatedResponse {
    private List<SectionalItem> sectional_items;
    private String rank_token;
    private List<Cluster> clusters;
    private String next_max_id;
    private boolean more_available;
}
