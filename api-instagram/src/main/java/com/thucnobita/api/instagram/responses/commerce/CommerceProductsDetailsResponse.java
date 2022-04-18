package com.thucnobita.api.instagram.responses.commerce;

import com.thucnobita.api.instagram.models.commerce.Product;
import com.thucnobita.api.instagram.models.user.Profile;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class CommerceProductsDetailsResponse extends IGResponse {
    private Profile merchant;
    private Product product_item;
}
