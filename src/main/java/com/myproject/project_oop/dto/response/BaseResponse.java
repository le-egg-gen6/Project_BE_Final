package com.myproject.project_oop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public static BaseResponse<?> buildDataResponse(Object data) {
        return BaseResponse.builder()
                .success(true)
                .data(data)
                .build();
    }

    public static BaseResponse<?> buildDataResponse(Object data, String msg) {
        return BaseResponse.builder()
                .success(true)
                .message(msg)
                .data(data)
                .build();
    }

    public static BaseResponse<?> buildMessageResponse(String msg) {
        return BaseResponse.builder()
                .success(true)
                .message(msg)
                .build();
    }

    public static BaseResponse<?> buildErrorResponse(String msg) {
        return BaseResponse.builder()
                .success(false)
                .message(msg)
                .build();
    }

}
