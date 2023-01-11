package com.server.datn.server.common.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData {
    @JsonProperty(index = 1)
    private Integer pageNo;
    @JsonProperty(index = 2)
    private Integer pageSize;
    @JsonProperty(index = 3)
    private Integer totalPages;
    @JsonProperty(index = 4)
    private Integer totalItems;
    @JsonProperty(index = 5)
    private Long totalElements;
    @JsonProperty(index = 6, value = "next")
    private Boolean isNext;
    @JsonProperty(index = 7, value = "prev")
    private Boolean isPrev;
    @JsonProperty(index = 8)
    private Object data;

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Boolean getNext() {
        return isNext;
    }

    public void setNext(Boolean next) {
        isNext = next;
    }

    public Boolean getPrev() {
        return isPrev;
    }

    public void setPrev(Boolean prev) {
        isPrev = prev;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
