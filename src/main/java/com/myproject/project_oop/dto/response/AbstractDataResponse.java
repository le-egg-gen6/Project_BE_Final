package com.myproject.project_oop.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Setter
@Getter
public class AbstractDataResponse {

    @JsonIgnore
    private boolean success;

    @JsonIgnore
    private String message;
}
