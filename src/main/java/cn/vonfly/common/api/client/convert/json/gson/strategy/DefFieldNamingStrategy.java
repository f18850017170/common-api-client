package cn.vonfly.common.api.client.convert.json.gson.strategy;

import cn.vonfly.common.api.client.convert.annotation.SpecificFieldName;
import com.google.gson.FieldNamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * 默认的名称转换策略
 * SpecificFieldName
 */
public class DefFieldNamingStrategy implements FieldNamingStrategy {
    private FieldNamingStrategy customFieldNamingStrategy;

    public DefFieldNamingStrategy(FieldNamingStrategy customFieldNamingStrategy) {
        this.customFieldNamingStrategy = customFieldNamingStrategy;
    }

    public DefFieldNamingStrategy() {
    }

    @Override
    public String translateName(Field f) {
        String fieldName = f.getName();
        if (null != customFieldNamingStrategy) {
            fieldName = customFieldNamingStrategy.translateName(f);
        }
        SpecificFieldName specificFieldNameAnnotation = f.getAnnotation(SpecificFieldName.class);
        if (null != specificFieldNameAnnotation) {
            if (specificFieldNameAnnotation.noTranslate()) {
                fieldName = f.getName();
            } else {
                String specificFieldName = specificFieldNameAnnotation.value();
                if (StringUtils.isNotBlank(specificFieldName)) {
                    fieldName = specificFieldName;
                }
            }
        }
        return fieldName;
    }
}
