package com.syp.springDemo.framework.webmvc.servlet;

import com.syp.springDemo.framework.annotatin.CustomController;
import com.syp.springDemo.framework.annotatin.CustomRequestMapping;
import com.syp.springDemo.framework.annotatin.CustomRequestParam;
import com.syp.springDemo.framework.context.CustomApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: SYP
 * @Date: 2020/4/6
 * @Description:
 *职责：
 * 任务调度
 */
public class CustomDispatchServlet extends HttpServlet {

    private CustomApplicationContext applicationContext;

    private List<CustomHandlerMapping> handlerMappings =  new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception,Detail : " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        CustomHandlerMapping handler = getHandler(req);
        if(handler == null){
            resp.getWriter().write("404 Not Found");
            return ;
        }


        Method method = handler.getMethod();
        Map<String,String[]> params = req.getParameterMap();

        //获取形参列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] paramValues = new Object[parameterTypes.length];
        for(int i=0; i<parameterTypes.length; i++){
            Class parameterType = parameterTypes[i];
            if(parameterType == HttpServletRequest.class){
                paramValues[i] = req;
            }else if(parameterType == HttpServletResponse.class){
                paramValues[i] = resp;
            }else if(parameterType == String.class){
                Annotation[][] pa = method.getParameterAnnotations();
                for(int j=0; j<pa.length; j++){
                    for(Annotation a : pa[j]){
                        if(a instanceof CustomRequestParam){
                            String paramName = ((CustomRequestParam) a).value();
                            if(!"".equals(paramName.trim())){
                                String value = Arrays.toString(params.get(paramName))
                                        .replaceAll("\\[|\\]]","")
                                        .replaceAll("\\s+",",");
                                paramValues[i] = value;
                            }
                        }
                    }
                }
            }
        }

        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(applicationContext.getBean(beanName),paramValues);
    }

    //自己写，自己用
    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
//        if(chars[0] > )
        chars[0] += 32;
        return String.valueOf(chars);
    }


    private CustomHandlerMapping getHandler(HttpServletRequest req) {
        if(this.handlerMappings.isEmpty()){
            return null;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"".replaceAll("/+","/"));
        for(CustomHandlerMapping mapping : handlerMappings){
            Matcher matcher = mapping.getPattern().matcher(url);
            if(!matcher.matches()){continue;}
            return mapping;
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        applicationContext = new CustomApplicationContext(config.getInitParameter("contextConfigLocation"));

        //初始化ApplicationContext
        doInitHandlerMapping();
        //加载配置文件 解析配置文件，封装成beanDefinition
    }
    private void doInitHandlerMapping() {
        if(this.applicationContext.getBeanDefinitionCount() == 0 ){
            return;
        }

        for(String  beanName : this.applicationContext.getBeanDefinitionNames()){
            Object instance = applicationContext.getBean(beanName);
            Class<?> clazz = instance.getClass();
            if(!clazz.isAnnotationPresent(CustomController.class)){
                continue;
            }
            String baseUrl = "";
            if(clazz.isAnnotationPresent(CustomRequestMapping.class)){
                CustomRequestMapping requestMapping = clazz.getAnnotation(CustomRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            for(Method method : clazz.getMethods()){
                if(method.isAnnotationPresent(CustomRequestMapping.class)){
                    CustomRequestMapping requestMapping = method.getAnnotation(CustomRequestMapping.class);

                    String url = ("/"+baseUrl + "/"+ requestMapping.value().replaceAll("\\*",".*")).replaceAll("/+","/");
                    Pattern pattern = Pattern.compile(url);
                    handlerMappings.add(new CustomHandlerMapping(pattern,method,instance));
                    System.out.println("Mapped : " + url + "," + method);
                }
            }

        }

    }
}
