package com.example.web.model.bo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerBO {
    private long banner_id;
    private String link;
    private String image;
    private Boolean status = false;
}
