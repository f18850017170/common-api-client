package cn.vonfly.common.api.client.sign;

/**
 * signKey用来直接put到待签名数据Map中
 */
public interface SignKey2Put {
    /**
     * 获取signKey键值对的KEY
     * @return
     */
    String getSignKeyEntryKey2Put();

    /**
     * 获取signKey键值对的value
     * @return
     */
    String getSignKeyEntryValue2Put();
}
