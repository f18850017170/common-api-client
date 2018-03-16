package cn.vonfly.common.api.client.sign;

import java.security.MessageDigest;

/**
 *
 */
public interface SignDto {
    /**
     * 获取加密类型
     *
     * @return 算法类型
     */
    MessageDigest buildMessageDigest();

    /**
     * 加签数据
     *
     * @return 签名结果
     */
    String dataSign();

}
