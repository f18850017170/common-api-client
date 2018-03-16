package cn.vonfly.common.api.client.sign;

/**
 * 签名数据回填
 */
public interface SignDataBackFill {
    /**
     * 设置参与加密的源数据
     * @param signData
     */
    void setSignData(String signData);

    /**
     * 获取参与加密的源数据
     * @return
     */
    String getSignData();
}
