package com.de013.custom;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.de013.utils.CustomFormat;

import io.micrometer.common.util.StringUtils;

@Component
public class CustomDateConverter implements Converter<String, Date> {
    
    public Date convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        for (String format : CustomFormat.DATE) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(source);
            } catch (Exception e) {
                // Exception
            }
        }

        return null;
    }
}
