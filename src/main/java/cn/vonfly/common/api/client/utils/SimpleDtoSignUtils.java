package cn.vonfly.common.api.client.utils;


import cn.vonfly.common.api.client.sign.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;

import java.security.MessageDigest;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class SimpleDtoSignUtils {
    /**
     * 默认的key隐藏处理
     */
    private static final SignKeyHiddenHandle defSignKeyHiddenHandle = new SignKeyHiddenHandle() {

        @Override
        public String signKeyHidden(String macKey) {
            if (null != macKey && macKey.length() > 0) {
                String temp = "";
                int repeatTimes = macKey.length();
                if (repeatTimes > 4) {
                    temp = macKey.substring(0, 4);
                    repeatTimes = macKey.substring(4).length();
                }
                temp += StringUtils.repeat('*', repeatTimes);
                return temp;
            }
            return macKey;
        }
    };
    /**
     * 默认加签字段默认构建方式key=value
     */
    private static final FieldSignBuild defFieldSignBuild = new FieldSignBuild() {
        @Override
        public void fieldSignBuild(StringBuilder stringBuilder, Map.Entry<String, String> tempEntry) {
            stringBuilder.append(tempEntry.getKey()).append("=").append(tempEntry.getValue()).append("&");
        }

        @Override
        public void lastCharHandle(StringBuilder stringBuilder) {
            if (stringBuilder.length() > 0) {
                stringBuilder.setLength(stringBuilder.length() - 1);
            }
        }
    };

    /**
     * 构建未添加key的加密数据
     *
     * @param t
     * @param fieldSignBuild
     * @param <T>
     * @return
     */
    public static <T extends SignDto> String buildSignDataWithKey(T t, FieldSignBuild fieldSignBuild) {
        Asserts.notNull(t, "进行签名数据构建的对象不能为空");
        Map<String, String> fieldMap = SignFieldMapBuildUtils.signFieldMapBuild(t);
        Asserts.check(null != fieldMap && !fieldMap.isEmpty(), "进行签名数据构建的字段不能为空");
        if (t instanceof SignKey2Put){
            SignKey2Put signKey2Put = (SignKey2Put) t;
            fieldMap.put(signKey2Put.getSignKeyEntryKey2Put(),signKey2Put.getSignKeyEntryKey2Put());
        }
        Map<String, String> tempMap = fieldMap;
        if (t instanceof SignFieldOrder) {
            SignFieldOrder signFieldOrder = (SignFieldOrder) t;
            Comparator<String> fieldOrderComparator = signFieldOrder.fieldOrderComparator();
            TreeMap<String, String> treeMap = new TreeMap<>(fieldOrderComparator);
            treeMap.putAll(fieldMap);
            tempMap = treeMap;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> tempEntry : tempMap.entrySet()) {
            fieldSignBuild.fieldSignBuild(stringBuilder, tempEntry);
        }
        fieldSignBuild.lastCharHandle(stringBuilder);
        if (t instanceof SignKey2Append){
            SignKey2Append signKey2Append = (SignKey2Append) t;
            stringBuilder.append(signKey2Append.buildSignKey2Append());
        }
        return stringBuilder.toString();
    }

    /**
     * 构建签名数据
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T extends SignDto> String dataSign(T t) {
        return dataSign(t, defFieldSignBuild, defSignKeyHiddenHandle);
    }

    /**
     * 构建签名数据
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T extends SignDto> String dataSign(T t, FieldSignBuild fieldSignBuild) {
        return dataSign(t, fieldSignBuild, defSignKeyHiddenHandle);
    }

    /**
     * 构建签名数据
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T extends SignDto> String dataSign(T t, SignKeyHiddenHandle signKeyHidden) {
        return dataSign(t, defFieldSignBuild, signKeyHidden);
    }

    /**
     * 构建签名数据
     *
     * @param t
     * @param fieldSignBuild
     * @param <T>
     * @return
     */
    public static <T extends SignDto> String dataSign(T t, FieldSignBuild fieldSignBuild, SignKeyHiddenHandle signKeyHidden) {
        String signDataWithoutKey = buildSignDataWithKey(t, fieldSignBuild);
        if (t instanceof SignDataBackFill) {
            SignDataBackFill signDataBackFill = (SignDataBackFill) t;
            signDataBackFill.setSignData(signDataWithoutKey);
        }
        MessageDigest messageDigest = t.buildMessageDigest();
        String signResult = Hex.encodeHexString(messageDigest.digest(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(signDataWithoutKey)));
        if (t instanceof SignResultBackFill) {
            SignResultBackFill signResultBackFill = (SignResultBackFill) t;
            signResultBackFill.setSign(signResult);
        }
        return signResult;
    }

    /**
     * key隐藏处理
     */
    public interface SignKeyHiddenHandle {
        /**
         * 进行key的隐藏
         *
         * @param key
         * @return
         */
        String signKeyHidden(String key);
    }

    /**
     * 参与加签字段构建（单独使用value拼接，或者同时使用key=value格式）
     */
    public interface FieldSignBuild {
        /**
         * 字段签名构建
         *
         * @param stringBuilder
         * @param tempEntry
         */
        void fieldSignBuild(StringBuilder stringBuilder, Map.Entry<String, String> tempEntry);

        /**
         * 最后一个字节处理
         *
         * @param stringBuilder
         */
        void lastCharHandle(StringBuilder stringBuilder);
    }
}
