package com.xaaef.robin.config.xss;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author WangChenChen
 * @version 1.0
 * @date 2022/3/25 9:40
 */


public class XssStringJsonDeserializer extends JsonDeserializer<String> {

    @Override
    public Class<String> handledType() {
        return String.class;
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return HtmlUtils.htmlEscape(jsonParser.getValueAsString());
    }
}


