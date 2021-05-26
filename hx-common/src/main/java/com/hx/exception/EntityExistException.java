package com.hx.exception;

import org.springframework.util.StringUtils;

public class EntityExistException extends RuntimeException {

    public EntityExistException(Object o, String field, String val) {
        super(EntityExistException.generateMessage(o.getClass().getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity)
                + " with " + field + " "+ val + " existed";
    }
}
