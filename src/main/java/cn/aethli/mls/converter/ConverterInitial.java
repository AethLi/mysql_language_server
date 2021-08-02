package cn.aethli.mls.converter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ConverterInitial {
    private static final Map<String, Converter<?>> CONVERTER_MAP = new HashMap<>();
    static {
        CONVERTER_MAP.put(Boolean.class.getTypeName(), new BooleanConverter());
        CONVERTER_MAP.put(String.class.getTypeName(), new StringConverter());
        CONVERTER_MAP.put(LocalDateTime.class.getTypeName(), new LocalDateTimeConverter());
    }
}
