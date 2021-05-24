package ru.javawebinar.topjava.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class StringToLocalDate implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(@Nullable String source) {
        return StringUtils.hasLength(source) ? LocalDate.parse(source) : null;
    }
}
