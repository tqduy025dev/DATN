package com.example.web.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Summary {
    long total;
    int  count;
    int  index;
    long totalPage;
}
