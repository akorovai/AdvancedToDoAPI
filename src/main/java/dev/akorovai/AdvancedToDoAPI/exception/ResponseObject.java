package dev.akorovai.AdvancedToDoAPI.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseObject {
    private int statusCode;
    private String message;
}