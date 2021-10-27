package com.checkin.CheckIn.error;

import com.checkin.CheckIn.domain.ResultResponseDto;
import javassist.NotFoundException;
import javassist.bytecode.DuplicateMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResultResponseDto handlerNotFoundException(Exception e) {
        log.info(e.getMessage());
        return ResultResponseDto.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .data(null)
                .build();
    }

    @ExceptionHandler(DuplicateMemberException.class)
    public ResultResponseDto handlerDuplicateMemberException(Exception e) {
        log.info(e.getMessage());
        return ResultResponseDto.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message(e.getMessage())
                .data(null)
                .build();
    }

}
