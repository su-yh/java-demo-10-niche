package com.suyh.demo04.json.deserializer;

import com.suyh.demo04.bytecodes.BizClassPoll;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * @author suyh
 * @since 2024-09-14
 */
public class BizStringCollectionDeserializer {
    public static void rebuildStringCollectionDeserializer() {
        try {
            rebuildCustomStringCollectionDeserializer();
        } catch (NotFoundException | CannotCompileException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改默认字符串列表，当反序列化时，String 结果为null时，忽略值，不往List 中添加值。
     */
    private static void rebuildCustomStringCollectionDeserializer()
            throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        ClassPool classPool = BizClassPoll.getDefault();
        CtClass ctClass = classPool.get("com.fasterxml.jackson.databind.deser.std.StringCollectionDeserializer");

        CtClass jsonParserClass = classPool.get("com.fasterxml.jackson.core.JsonParser");
        CtClass deserializationContextClass = classPool.get("com.fasterxml.jackson.databind.DeserializationContext");
        CtClass stringCollectionCtClass = classPool.get("java.util.Collection");
        CtClass deserJsonDeserializerCtClass = classPool.get("com.fasterxml.jackson.databind.JsonDeserializer");
        CtClass[] params = new CtClass[]{jsonParserClass, deserializationContextClass, stringCollectionCtClass, deserJsonDeserializerCtClass};
        CtMethod ctMethod = ctClass.getDeclaredMethod("deserializeUsingCustom", params);

        String body = "\n" +
                "    {\n" +
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
                "                        if (_skipNullValues) {\n" +
                "                            continue;\n" +
                "                        }\n" +
                "                        value = (String) _nullProvider.getNullValue($2);\n" +
                "                    } else {\n" +
                "                        value = $4.deserialize($1, $2);\n" +
                "                    }\n" +
                "                } else {\n" +
                "                    value = $4.deserialize($1, $2);\n" +
                "                }\n" +
                "if (value != null) {\n" + // String结果 非null 时才往 List 中添加值。
                "                $3.add(value);\n" +
                "}\n" +
                "            }\n" +
                "        } catch (Exception e) {\n" +
                "            throw JsonMappingException.wrapWithPath(e, $3, $3.size());\n" +
                "        }\n" +
                "        return $3;\n" +
                "    }\n";

        ctMethod.setBody(body);

        ctClass.toClass();
    }
}
