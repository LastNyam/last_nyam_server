package com.lastnyam.lastnyam_server.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ResponseBody<T> {
    protected String status;
}
