package cn.vonfly.common.api.client.dto;

import cn.vonfly.common.api.client.convert.annotation.ReqIgnore;
import cn.vonfly.common.api.client.http.HttpPostExecute;
import cn.vonfly.common.api.client.sign.SignDataBackFill;
import cn.vonfly.common.api.client.sign.SignResultBackFill;
import cn.vonfly.common.api.client.sign.annotation.SignIgnore;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbsReqSignDto<R extends Serializable> extends AbsBaseSignDto implements SignDataBackFill,SignResultBackFill,HttpPostExecute {
    @SignIgnore
    private String sign;
    @SignIgnore
    @ReqIgnore
    private String signData;

    /**
     * 构建请求地址
     * @return
     */
    public abstract String buildUrl();
    /**
     * 返回对象的class
     * @return
     */
    public  Class<R> responseClass(){
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return (Class<R>) actualTypeArguments[0];
    }

    /**
     * 构建失败请求
     * @param code
     * @param msg
     * @return
     */
    public abstract R fail(String code,String msg);
    @Override
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    public String getSignData() {
        return signData;
    }

    @Override
    public void setSignData(String signData) {
        this.signData = signData;
    }
}
