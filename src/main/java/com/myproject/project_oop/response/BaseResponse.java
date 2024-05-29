package com.myproject.project_oop.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
public class BaseResponse {

    private HttpStatus status;
    private Integer isError;
    private String errorMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private BaseResponse() {
        this.timestamp = LocalDateTime.now();
    }


    public BaseResponse(HttpStatus status, String message, Integer error) {
        this();
        this.status = status;
        this.errorMessage = message;
        this.isError = error;
    }

}
