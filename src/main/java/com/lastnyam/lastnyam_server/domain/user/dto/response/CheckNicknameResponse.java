package com.lastnyam.lastnyam_server.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CheckNicknameResponse {
    @JsonProperty("isDuplicated")
    private boolean duplicated;
}
