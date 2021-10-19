package com.checkin.CheckIn.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "Result")
public class ResultResponseDto<T> {

    @ApiModelProperty(value = "상태코드")
    private Integer statusCode;
    @ApiModelProperty(value = "상태메세지")
    private String message;
    private T data;

    @Builder
    public ResultResponseDto(Integer statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}