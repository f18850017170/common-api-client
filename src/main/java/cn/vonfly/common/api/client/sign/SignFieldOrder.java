package cn.vonfly.common.api.client.sign;

import java.util.Comparator;

public interface SignFieldOrder {
    /**
     * 返回字段排序比较器
     * @return
     */
    Comparator<String> fieldOrderComparator();
}
