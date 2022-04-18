package com.thucnobita.api.instagram.responses.commerce;

import java.util.List;
import com.thucnobita.api.instagram.models.discover.SectionalMediaGridItem;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class CommerceDestinationResponse extends IGResponse implements IGPaginatedResponse {
    private List<SectionalMediaGridItem> sectional_items;
    private String rank_token;
    private String next_max_id;
    private boolean more_available;
}
