package com.suyh.demo04.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

import java.io.IOException;

/**
 * @author suyh
 * @since 2024-09-13
 */
public class BizStringDeserializer extends StringDeserializer {
    public final static BizStringDeserializer instance = new BizStringDeserializer();

    /**
     * 若反序列化后，结果是空白字符串，则将值置为null.
     */
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String v = super.deserialize(p, ctxt);
        String trim = v.trim();
        return trim.isEmpty() ? null : trim;
    }
}
