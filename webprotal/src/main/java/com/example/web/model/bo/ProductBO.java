package com.example.web.model.bo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;


@Getter
@Setter
public class ProductBO {
    private String product_name;
    private String description;
    private String link;
    private String image;
    private long category_id;
    private long idol_id;
    private String status_video;
    private long view_counter;
}
