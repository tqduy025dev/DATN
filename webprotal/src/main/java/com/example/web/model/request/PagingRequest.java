package com.example.web.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PagingRequest {
    private short pageNumber;
    private byte pageSize = 30;
}
