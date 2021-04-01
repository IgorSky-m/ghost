package com.skachko.receiver.converters;

import com.skachko.receiver.converters.api.IValueConverter;

import java.math.BigDecimal;
import java.util.UUID;

public class DefaultValueConverter implements IValueConverter {

    public String toString(String value) {
        return value;
    }

    public Character toChar(String value) {
        if (validate(value)) {
            return value.charAt(0);
        }
        return null;
    }

    public Integer toInteger(String value) {
        return Integer.valueOf(value);
    }

    public Long toLong(String value) {
        return Long.valueOf(value);
    }

    public Double toDouble(String value){
        return Double.valueOf(value);
    }

    public BigDecimal toDecimal(String value){
        return new BigDecimal(value);
    }

    public UUID tiUuid(String value){
        return UUID.fromString(value);
    }




    private boolean validate(String value) {
        return !"".equals(value) && value != null && !value.isEmpty();
    }
}
