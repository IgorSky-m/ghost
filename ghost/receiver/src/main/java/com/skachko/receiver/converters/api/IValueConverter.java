package com.skachko.receiver.converters.api;


import java.math.BigDecimal;
import java.util.UUID;

public interface IValueConverter {

    String toString(String value);

    Character toChar(String value);

    Integer toInteger(String value);

    Long toLong(String value);

    Double toDouble(String value);

    BigDecimal toDecimal(String value);

    UUID tiUuid(String value);

}
