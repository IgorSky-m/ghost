package com.skachko.receiver.enums;

public enum FieldType {
    STRING("String"),
    CHARACTER("Character"),
    CHARACTER_PRIMITIVE("char"),
    DOUBLE("Double"),
    DOUBLE_PRIMITIVE("double"),
    FLOAT("Float"),
    FLOAT_PRIMITIVE("float"),
    BYTE("Byte"),
    BYTE_PRIMITIVE("byte"),
    SHORT("Short"),
    SHORT_PRIMITIVE("short"),
    INTEGER("Integer"),
    INTEGER_PRIMITIVE("int"),
    LONG("Long"),
    LONG_PRIMITIVE("long"),
    DECIMAL("BigDecimal"),
    UUID("UUID");

    public final String name;

    FieldType(String name) {
        this.name = name;
    }

    public static FieldType findByName(String name){
        for (FieldType value : FieldType.values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }



}
