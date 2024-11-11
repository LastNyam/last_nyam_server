package com.lastnyam.lastnyam_server.global.response;

import lombok.Getter;

@Getter
public final class SuccessResponseBody<T> extends ResponseBody<T> {
    private final String SUCCESS = "success";
    private final T data;

    SuccessResponseBody() {
        this.status = SUCCESS;
        this.data = null;
    }

    SuccessResponseBody(T data) {
        this.status = SUCCESS;
        this.data = data;
    }
}
