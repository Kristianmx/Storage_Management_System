package com.JFSEI.Storage_Management_System.api.error_handler;

import com.JFSEI.Storage_Management_System.api.dtos.error.ListResponseErrors;
import com.JFSEI.Storage_Management_System.api.dtos.error.ResponseError;
import com.JFSEI.Storage_Management_System.utils.exceptions.BadRequestException;
import com.JFSEI.Storage_Management_System.utils.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ListResponseErrors handlerBadRequest
            (MethodArgumentNotValidException exception){
        List<Map<String ,String >> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(error->{
            Map<String , String > errorResponse = new HashMap<>();
            errorResponse.put("error", error.getDefaultMessage());
            errorResponse.put("Field",error.getField());
            errors.add(errorResponse);
        });
        return ListResponseErrors.builder().code(HttpStatus.BAD_REQUEST.value()).status(HttpStatus.BAD_REQUEST.name()).errors(errors).build();
    }
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseError handlerIdError(IdNotFoundException exception) {

        Map<String, String> error = new HashMap<>();

        error.put("Error", "ID not found");
        error.put("Entity", exception.getMessage());

        return ResponseError.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.name())
                .error(error)
                .build();
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseError handlerRequestError(BadRequestException exception) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("Error", exception.getMessage());

        return ResponseError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .error(errorResponse)
                .build();
    }

}
