package com.xperblueray.rpc.common;

import lombok.Data;

/**
 * 封装的请求对象
 */
@Data
public class RpcResponse {
    /**
     * 请求对象的Id
     */
    private String requestId;
    /**
     * 错误信息
     */
    private String error;
    /**
     * 返回结果
     */
    private Object result;
}
