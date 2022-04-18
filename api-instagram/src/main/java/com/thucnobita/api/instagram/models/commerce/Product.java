package com.thucnobita.api.instagram.models.commerce;

import com.thucnobita.api.instagram.models.IGBaseModel;
import com.thucnobita.api.instagram.models.user.Profile;

import lombok.Data;

@Data
public class Product extends IGBaseModel {
    private String name;
    private String price;
    private String current_price;
    private String full_price;
    private long product_id;
    private Profile merchant;
    private String compound_product_id;
    private String description;
    private String retailer_id;
    private String external_url;

}
