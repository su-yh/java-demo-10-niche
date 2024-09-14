package com.suyh.demo04.bytecodes;

import javassist.ClassPool;

/**
 * @author suyh
 * @since 2024-09-14
 */
public class BizClassPoll {
    static {
        init();
    }

    public static ClassPool getDefault() {
        return ClassPool.getDefault();
    }

    private static void init() {
        ClassPool classPool = ClassPool.getDefault();
        importPackages(classPool);
    }

    private static void importPackages(ClassPool classPool) {
        classPool.importPackage("java.io.IOException");
        classPool.importPackage("java.util.Objects");
        classPool.importPackage("com.fasterxml.jackson.core.JsonToken");
        classPool.importPackage("com.fasterxml.jackson.databind.JsonMappingException");
        classPool.importPackage("com.fasterxml.jackson.databind.annotation.JacksonStdImpl");
        classPool.importPackage("com.fasterxml.jackson.databind.deser.ContextualDeserializer");
        classPool.importPackage("com.fasterxml.jackson.databind.deser.NullValueProvider");
        classPool.importPackage("com.fasterxml.jackson.databind.deser.impl.NullsConstantProvider");
        classPool.importPackage("com.fasterxml.jackson.databind.jsontype.TypeDeserializer");
        classPool.importPackage("com.fasterxml.jackson.databind.type.LogicalType");
        classPool.importPackage("com.fasterxml.jackson.databind.util.AccessPattern");
        classPool.importPackage("com.fasterxml.jackson.databind.util.ObjectBuffer");
        classPool.importPackage("com.fasterxml.jackson.databind.deser.std.StringCollectionDeserializer");
    }
}
