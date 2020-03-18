package com.formacao.demo.controller.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
    private String fieldName, message;

    public FieldMessage() {

    }

    public FieldMessage(String fieldName, String message) {
        super();
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
