package cn.vonfly.common.api.client.sign;

/**
 * 签名key添加到待签名数据
 */
public interface SignKey2Append {
    /**
     * 构建用来直接append到signData之后的数据
     * @return
     */
    String buildSignKey2Append();
}
