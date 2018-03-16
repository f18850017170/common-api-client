package cn.vonfly.common.api.client.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

public class SimpleReflectionUtils {
    /**
     * 遍历不为空对象的的可访问类元
     *
     * @param t
     * @param superClass
     * @param invoke
     * @param <T>
     */
    public static <T extends AccessibleObject> void iterateAccessibleItem(Object t, Class<?> superClass, ObjAccessibleOptionInvoke<T> invoke) {
        if (null == t) throw new IllegalArgumentException("传入对象不能为空");
        if (null == superClass) throw new IllegalArgumentException("传入对象限定超类不能为空");
        if (null == invoke) throw new IllegalArgumentException("迭代处理对象不能为空");
        Class<?> tempClass = t.getClass();
        if (!superClass.isAssignableFrom(tempClass))
            throw new IllegalArgumentException(String.format("对象类型[%s]与限定超类不一致[%s]", tempClass, superClass));
        final Object tempObj = t;
        do {
            T[] accessibleArray = invoke.getAccessible(tempClass);

            for (T accessible : accessibleArray) {
                invoke.invoke(accessible, tempObj, tempClass);
            }
            tempClass = tempClass.getSuperclass();
        } while (superClass.isAssignableFrom(tempClass));
    }

    /**
     * 获取字段value
     *
     * @param field
     * @param object
     * @return
     */
    public static Object getFieldValue(Field field, Object object) throws IllegalArgumentException {
        Object result;
        boolean accessible = false;
        try {
            accessible = field.isAccessible();
            field.setAccessible(true);
            result = field.get(object);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (!accessible) {
                field.setAccessible(accessible);
            }
        }
        return result;
    }

    /**
     * 类field操作处理
     */
    public static abstract class AbsFieldAccOpInvoke implements ObjAccessibleOptionInvoke<Field> {
        @Override
        public Field[] getAccessible(Class<?> tempClass) {
            return tempClass.getDeclaredFields();
        }
    }

    /**
     * 对象可访问类型处理
     *
     * @param <T>
     */
    public interface ObjAccessibleOptionInvoke<T extends AccessibleObject> {
        T[] getAccessible(Class<?> tempClass);

        void invoke(T t, Object tempObj, Class<?> tempClass);
    }
}
