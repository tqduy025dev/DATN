package com.server.datn.server.common.dto.base;

public class Response {
    private ResponseResult result;
    private ResponseData response;

    public ResponseResult getResult() {
        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }

    public ResponseData getResponse() {
        return response;
    }

    public void setResponse(ResponseData response) {
        this.response = response;
    }
}
