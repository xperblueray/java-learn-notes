package com.xperblueray.filter;

import com.xperblueray.util.ZkUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Activate(group = {CommonConstants.CONSUMER})
public class MethodMonitorFilter implements Filter {
    private static Logger log = LoggerFactory.getLogger(MethodMonitorFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long startTime = System.currentTimeMillis();
        String methodName = invocation.getMethodName();
        try {
            return invoker.invoke(invocation);
        } finally {
            long curTime = System.currentTimeMillis();
//            log.info("Invoke time: {} ms", curTime - startTime);
            ZkUtils.addLogTime(methodName, curTime + ":" + (curTime - startTime));
        }
    }
}
