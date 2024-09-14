package com.suyh.demo04.json.deserializer;

import com.suyh.demo04.bytecodes.BizClassPoll;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author suyh
 * @since 2024-09-13
 */
@Slf4j
public class BizStringArrayDeserializer {
    public static void rebuildStringArrayDeserializer() {
        try {
            rebuildCustomStringArrayDeserializer();
        } catch (NotFoundException | CannotCompileException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改默认字符串数组，当反序列化时，String 结果为null时，忽略值，不往数组中添加值。
     */
    private static void rebuildCustomStringArrayDeserializer()
            throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        ClassPool classPool = BizClassPoll.getDefault();
        CtClass ctClass = classPool.get("com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer");

        // 获取参数类型
        CtClass jsonParserClass = classPool.get("com.fasterxml.jackson.core.JsonParser");
        CtClass deserializationContextClass = classPool.get("com.fasterxml.jackson.databind.DeserializationContext");
        CtClass stringArrayCtClass = classPool.get("java.lang.String[]");
        CtClass[] params = new CtClass[]{jsonParserClass, deserializationContextClass, stringArrayCtClass};
        // 获取带有指定参数类型的重载方法
        CtMethod ctMethod = ctClass.getDeclaredMethod("_deserializeCustom", params);

        String body = "    {\n" +
                "        final ObjectBuffer buffer = $2.leaseObjectBuffer();\n" +
                "        int ix;\n" +
                "        Object[] chunk;\n" +
                "\n" +
                "        if ($3 == null) {\n" +
                "            ix = 0;\n" +
                "            chunk = buffer.resetAndStart();\n" +
                "        } else {\n" +
                "            ix = $3.length;\n" +
                "            chunk = buffer.resetAndStart($3, ix);\n" +
                "        }\n" +
                "\n" +
//                "        final JsonDeserializer<String> deser = _elementDeserializer;\n" +
                "\n" +
                "        try {\n" +
                "            while (true) {\n" +
                "                /* 30-Dec-2014, tatu: This may look odd, but let's actually call method\n" +
                "                 *   that suggest we are expecting a String; this helps with some formats,\n" +
                "                 *   notably XML. Note, however, that while we can get String, we can't\n" +
                "                 *   assume that's what we use due to custom deserializer\n" +
                "                 */\n" +
                "                String value;\n" +
                "                if ($1.nextTextValue() == null) {\n" +
                "                    JsonToken t = $1.currentToken();\n" +
                "                    if (t == JsonToken.END_ARRAY) {\n" +
                "                        break;\n" +
                "                    }\n" +
                "                    // Ok: no need to convert Strings, but must recognize nulls\n" +
                "                    if (t == JsonToken.VALUE_NULL) {\n" +
                "                        if (this._skipNullValues) {\n" +
                "                            continue;\n" +
                "                        }\n" +
                "                        value = (String) this._nullProvider.getNullValue($2);\n" +
                "                    } else {\n" +
                "                        value = _elementDeserializer.deserialize($1, $2);\n" +
                "                    }\n" +
                "                } else {\n" +
                "                    value = _elementDeserializer.deserialize($1, $2);\n" +
                "                }\n" +
                "                if (ix >= chunk.length) {\n" +
                "                    chunk = buffer.appendCompletedChunk(chunk);\n" +
                "                    ix = 0;\n" +
                "                }\n" +
                "                if (value != null) {\n" + // String 结果 非null时，才会往数组中添加值。
                "                    chunk[ix++] = value;\n" +
                "                }" +
                "            }\n" +
                "        } catch (Exception e) {\n" +
                "            // note: pass String.class, not String[].class, as we need element type for error info\n" +
                "            throw JsonMappingException.wrapWithPath(e, String.class, ix);\n" +
                "        }\n" +
                "        String[] result = buffer.completeAndClearBuffer(chunk, ix, String.class);\n" +
                "        $2.returnObjectBuffer(buffer);\n" +
                "        return result;\n" +
                "    }\n";
        ctMethod.setBody(body);

        ctClass.toClass();
    }
}
