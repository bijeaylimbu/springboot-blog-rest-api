package com.springboot.blog.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponseDto {
    private String message;
    private Object detail;
    private int statusCode;
    @JsonIgnore
    private int code;

    public static ResponseEntity successModel(Object o){
        RestResponseDto r=new RestResponseDto();
        r.setDetail(o);
        r.setMessage("SUCCESS");
        r.setStatusCode(200);
        return new ResponseEntity(r, HttpStatus.OK);
    }

    public ResponseEntity validationFailed(List<ObjectError> errorList){
        RestResponseDto r=new RestResponseDto();
        r.setDetail(errorList);
        return new ResponseEntity(r, HttpStatus.BAD_REQUEST);
    }
    public static ResponseEntity failureModel(String message){
        RestResponseDto r=new RestResponseDto();
        r.setDetail(message);
        r.setStatusCode(500);
        return new ResponseEntity(r, HttpStatus.BAD_REQUEST);
    }
}