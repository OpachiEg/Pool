package com.pool.controller.exception;

import com.pool.dto.MessageRsDto;
import com.pool.exception.BadRequestException;
import com.pool.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<MessageRsDto> notFound(RuntimeException e) {
        return ResponseEntity
                .status(404)
                .body(new MessageRsDto(e.getMessage()));
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<MessageRsDto> badRequest(RuntimeException e) {
        return ResponseEntity
                .status(400)
                .body(new MessageRsDto(e.getMessage()));
    }

}
