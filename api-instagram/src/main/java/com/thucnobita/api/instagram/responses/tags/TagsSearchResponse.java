package com.thucnobita.api.instagram.responses.tags;

import com.thucnobita.api.instagram.responses.IGPageRankTokenResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

import java.util.List;

@Data
public class TagsSearchResponse extends IGResponse implements IGPageRankTokenResponse {
    private String rank_token;
    private String page_token;
    private String status;
    private boolean has_more;
    private List<SearchTagTag> results;

    @Data
    public static class SearchTagTag {
        private long id;
        private String name;
        private String formatted_media_count;
        private String search_result_subtitle;
        private String profile_pic_url;
        private int media_count;
        private boolean use_default_avatar;
    }
}
