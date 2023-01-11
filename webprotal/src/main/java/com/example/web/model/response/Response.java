package com.example.web.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response {
    private short statusCode;
    private String message;
    private Object data;
}
