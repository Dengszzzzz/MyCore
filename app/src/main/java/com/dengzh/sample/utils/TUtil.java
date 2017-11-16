package com.dengzh.sample.utils;

import java.lang.reflect.ParameterizedType;

/**
 * 类转换初始化
 * 在java中T.getClass() 或 T.class都是不合法的,因为T是泛型变量;
 由于一个类的类型在编译期已确定,故不能在运行期得到T的实际类型;
 // 获取当前运行类泛型父类类型，即为参数化类型，有所有类型公用的高级接口Type接收!
 Type type = this.getClass().getGenericSuperclass();
 // 强转为“参数化类型”
 //ParameterizedType参数化类型，即泛型
 ParameterizedType pt = (ParameterizedType) type; // BaseDao
 // 获取参数化类型中，实际类型的定义
 Type[] ts = pt.getActualTypeArguments();
 // 转换
 this.clazz = (Class) ts[0];

 简而言之就是获得超类(父类)的泛型参数的实际类型
 */
public class TUtil {
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
