package com.syp.pattern.prototype;

import java.lang.reflect.Field;

public class BeanUtil {
    public static  Object copy(Object prototype){
        Class clazz = prototype.getClass();
        Object instance = null;
        try {
            instance = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                field.set(instance,field.get(prototype));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
