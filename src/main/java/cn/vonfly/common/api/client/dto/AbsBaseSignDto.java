package cn.vonfly.common.api.client.dto;


import cn.vonfly.common.api.client.convert.annotation.ReqIgnore;
import cn.vonfly.common.api.client.sign.SignDto;
import cn.vonfly.common.api.client.sign.SignKeyHidden;
import cn.vonfly.common.api.client.sign.annotation.SignIgnore;
import cn.vonfly.common.api.client.utils.SimpleDtoSignUtils;

import java.io.Serializable;

public abstract class AbsBaseSignDto implements SignKeyHidden,SignDto,Serializable {

    @SignIgnore
    @ReqIgnore
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    @Override
    public String dataSign() {
        return SimpleDtoSignUtils.dataSign(this);
    }
}
