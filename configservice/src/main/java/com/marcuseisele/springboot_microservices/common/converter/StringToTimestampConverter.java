package com.server.datn.server.common.converter;

import com.server.datn.server.common.utils.TimeUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.sql.Timestamp;
import java.text.ParseException;

public class StringToTimestampConverter implements Converter<String, Timestamp> {
    @Override
    public Timestamp convert(MappingContext<String, Timestamp> mappingContext) {
        try {
            String source = mappingContext.getSource();
            return TimeUtils.parseTimestamp(source);
        } catch (ParseException e) {
            String source = mappingContext.getSource() + " 00:00:00";
            try {
                return TimeUtils.parseTimestamp(source);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
