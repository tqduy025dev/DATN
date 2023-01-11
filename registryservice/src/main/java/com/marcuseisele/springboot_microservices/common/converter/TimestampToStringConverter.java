package com.server.datn.server.common.converter;

import com.server.datn.server.common.utils.TimeUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.sql.Timestamp;

public class TimestampToStringConverter implements Converter<Timestamp, String> {

    @Override
    public String convert(MappingContext<Timestamp, String> context) {
        try {
            Timestamp timestamp = context.getSource();
            return TimeUtils.parseString(timestamp);
        }catch (Exception e){
            return null;
        }
    }
}
