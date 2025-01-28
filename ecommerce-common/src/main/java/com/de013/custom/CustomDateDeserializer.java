package com.de013.custom;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.de013.utils.CustomFormat;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import io.micrometer.common.util.StringUtils;

public class CustomDateDeserializer extends JsonDeserializer<Date>{

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) 
        throws IOException, JacksonException {
        String dateStr = jsonParser.getText();

        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }

        for (String format : CustomFormat.DATE) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(dateStr);
            } catch (Exception e) {

            }
        }
        
        return null;
    }
}
