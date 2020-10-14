package com.syp.netty.rpc.registry;

import com.syp.netty.rpc.protocol.InvokerProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: SYP
 * @Date: 2020/8/17
 * @Description:
 */
public class RegistryHandler extends ChannelInboundHandlerAdapter {
    //根据一个包名将所有符合条件的class全部扫描出来，放到一个容器中 如果是分布式则读取配置文件
    //给每一个对应的class起一个唯一的名字，作为服务名称，保存到容器中
    //当有客户端连接过来后，就会获取协议内容 InvokerProtocol的对象
    //要去注册好的容器中找到符合条件的服务
    //通过远程调用provider得到返回结果，并回复给客户端

    private List<String> classNames = new ArrayList<String>();

    private Map<String, Object> registerMap = new HashMap<String, Object>();
    public RegistryHandler() {
        scannerClass("com.syp.netty.rpc.provider");
        doRegistry();
    }

    private void doRegistry() {
        if(classNames.isEmpty()){
            return;
        }

        for(String className : classNames){
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> aClass = clazz.getInterfaces()[0];
                String serviceName = aClass.getName();
                registerMap.put(serviceName,clazz.newInstance());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private void scannerClass(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for(File file : classPath.listFiles()){
            if(file.isDirectory()){
                scannerClass(packageName+"."+file.getName());
            }else{
                classNames.add(packageName+"."+file.getName().replace(".class",""));
            }
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();
        InvokerProtocol request = (InvokerProtocol) msg;
        if(registerMap.containsKey(request.getClassName())){
            Object service = registerMap.get(request.getClassName());
            Method method = service.getClass().getMethod(request.getMethodName(),request.getParams());
            result = method.invoke(service,request.getVlaues());
        }

        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
