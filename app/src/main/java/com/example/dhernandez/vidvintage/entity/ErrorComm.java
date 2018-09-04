package com.example.dhernandez.vidvintage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dhernandez on 03/09/2018.
 */

@Data
@NoArgsConstructor
public class ErrorComm {

    public enum STATUS {ERROR, INVALID_SESSION, NO_ERROR}

    String errorMsg;
    int errorCode;
    STATUS status;

    public ErrorComm(String errorMsg, int errorCode, STATUS status){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.status = status;
    }
}