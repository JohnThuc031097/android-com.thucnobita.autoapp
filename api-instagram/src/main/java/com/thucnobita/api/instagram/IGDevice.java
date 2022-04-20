package com.thucnobita.api.instagram;

import java.io.Serializable;
import java.util.Map;
import lombok.Data;

@Data
public class IGDevice implements Serializable {
    private static final long serialVersionUID = -823447845648L;
    private final String userAgent;
    private final String capabilities;
    private final Map<String, Object> deviceMap;

}
