package com.rancard.enums;

import java.util.List;


public enum TargetDefinition {

    IN(List.class),
    GREATER_THAN(Integer.class),
    LESS_THAN(Integer.class),
    EQUALS(String.class),
    BETWEEN(List.class),
    NOT_IN(List.class),
    CONTAINS(String.class),
    NOT_CONTAINS(String.class),
    IS_NULL(Void.class),
    IS_NOT_NULL(Void.class);


    private final Class<?> valueType;

    TargetDefinition(Class<?> valueType) {
        this.valueType = valueType;
    }

    //Get the value of the enum
    public Class<?> getValueType() {
        return valueType;
    }

}
