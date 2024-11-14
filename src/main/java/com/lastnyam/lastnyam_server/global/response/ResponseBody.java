package com.lastnyam.lastnyam_server.global.response;

import lombok.Getter;

@Getter
public abstract class ResponseBody<T> {
    protected String status;
}
