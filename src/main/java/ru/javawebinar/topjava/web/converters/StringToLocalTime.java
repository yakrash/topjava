package ru.javawebinar.topjava.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalTime;

public class StringToLocalTime implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(@Nullable String source) {
        return StringUtils.hasLength(source) ? LocalTime.parse(source) : null;
    }
}
