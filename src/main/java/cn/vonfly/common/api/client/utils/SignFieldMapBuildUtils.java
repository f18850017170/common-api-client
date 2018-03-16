package cn.vonfly.common.api.client.utils;

import cn.vonfly.common.api.client.sign.SignDto;
import cn.vonfly.common.api.client.sign.annotation.SignIgnore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class SignFieldMapBuildUtils {

    /**
     * 构建参与加签的字段map
     * @param t
     * @param <T>
     * @return
     */
    public static <T extends SignDto> Map<String, String> signFieldMapBuild(T t) {
        return signFieldsRetrieve(t, SignDto.class);
    }

    /**
     * 构建参与加签字段的Map
     *
     * @param t
     * @param superClass
     * @return
     */
    protected static Map<String, String> signFieldsRetrieve(Object t, Class<?> superClass) {
        final Map<String, String> result = new LinkedHashMap<>();
        final Object tempObj = t;
        SimpleReflectionUtils.iterateAccessibleItem(tempObj, superClass, new SimpleReflectionUtils.AbsFieldAccOpInvoke() {

            @Override
            public void invoke(Field field, Object tempObj, Class<?> tempClass) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    Object fieldValue = SimpleReflectionUtils.getFieldValue(field, tempObj);
                    if (null != fieldValue) {
                        SignIgnore annotation = field.getAnnotation(SignIgnore.class);
                        if (null == annotation || Arrays.asList(annotation.exclusions()).contains(tempObj.getClass())) {
                            result.put(field.getName(), String.valueOf(fieldValue));
                        }
                    }
                }
            }
        });
        return result;
    }
}
