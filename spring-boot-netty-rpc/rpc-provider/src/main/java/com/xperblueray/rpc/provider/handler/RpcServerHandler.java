package com.xperblueray.rpc.provider.handler;

import com.alibaba.fastjson.JSON;
import com.xperblueray.rpc.api.IUserService;
import com.xperblueray.rpc.common.RpcRequest;
import com.xperblueray.rpc.common.RpcResponse;
import com.xperblueray.rpc.provider.annotation.RpcService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端业务处理类
 * 1. 将标注@RpcService的bean进行缓存
 *  1.1 得从容器中找到标有@RpcService的类进行缓存
 *  1.2 从容器中找到相应的注解
 *  1.3 需要获取容器的对象，让类实现ApplicationContextAware接口
 *
 * 2. 接收客户端请求
 * 3. 根据传递过来的beanname从缓存中查找到对应的bean
 * 4. 解析请求中的方法名称  参数类型  参数信息
 * 5. 反射调用bean的方法
 * 6. 给客户端进行响应
 */
@Component
@ChannelHandler.Sharable
public class RpcServerHandler extends SimpleChannelInboundHandler<String> implements ApplicationContextAware {

    private static final Map SERVICE_INSTANCE_MAP = new ConcurrentHashMap();

    /**
     * 通道读取就绪事件
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 1. 接收客户端请求  将msg转换成RpcRequest对象
        RpcRequest rpcRequest = JSON.parseObject(msg, RpcRequest.class);
        RpcResponse response = new RpcResponse();
        response.setRequestId(rpcRequest.getRequestId());
        // 业务处理
        try {
            response.setResult(handler(rpcRequest));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        // 给客户端响应
        ctx.writeAndFlush(JSON.toJSONString(response));
    }


    /**
     * 业务处理方法
     * @param request
     * @return
     */
    public Object handler(RpcRequest request) throws InvocationTargetException {
        // 3. 根据传递过来的beanname从缓存中查找到对应的bean
        Object serverBean = SERVICE_INSTANCE_MAP.get(request.getClassName());
        if (serverBean == null) {
            throw new RuntimeException("根据beanName找不到服务， beanName:" + request.getClassName());
        }
        // 4. 解析请求中的方法名称  参数类型  参数信息
        Class<?> serverBeanClass = serverBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterType = request.getParameterType();
        Object[] parameters = request.getParameters();

        // 5. 反射调用bean的方法 -- CGLIB反射调用
        FastClass fastClass = FastClass.create(serverBeanClass);
        FastMethod method = fastClass.getMethod(methodName, parameterType);
        return method.invoke(serverBean, parameters);

    }

    /**
     * 1. 将标注@RpcService的bean进行缓存
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (serviceMap != null && serviceMap.size() > 0) {
            for (Map.Entry<String, Object> entry : serviceMap.entrySet()) {
                Object serviceBean = entry.getValue();
                if (serviceBean.getClass().getInterfaces().length == 0) {
                    throw new RuntimeException("服务必须实现接口");
                }
                // 默认取第一个接口作为缓存bean的名称
                String name = serviceBean.getClass().getInterfaces()[0].getName();
                SERVICE_INSTANCE_MAP.put(name, serviceBean);
                if (serviceBean instanceof IUserService) {
                    System.out.println(serviceBean);
                }

            }

        }
    }
}
