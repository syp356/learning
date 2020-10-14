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
import java.io.File;
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
    private Map<CustomHandlerMapping, CustomHandlerAdapter> handlerAdapters = new HashMap<>();
    private List<CustomViewResolver> viewResolers = new ArrayList<>();

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
            processDispatchResult(req,resp,new CustomModelAndView("404"));
            return ;
        }


        //根据一个handlerMapping 获得一个handlerAdapter
        CustomHandlerAdapter ha = getHandlerAdapter(handler);

        CustomModelAndView mv = ha.handler(req,resp,handler);

        processDispatchResult(req,resp,mv);

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

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, CustomModelAndView mv) {
        if(null == mv || this.viewResolers.isEmpty()){
            return;
        }
        for(CustomViewResolver viewResolver : this.viewResolers){
            CustomView view = viewResolver.resolveViewName(mv.getViewName());
            view.render(mv.getModel(),req,resp);
            return;
        }
    }

    private CustomHandlerAdapter getHandlerAdapter(CustomHandlerMapping handler) {
        return null;
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
        //初始化spring核心ioc容器
        //加载配置文件 解析配置文件，封装成beanDefinition
        applicationContext = new CustomApplicationContext(config.getInitParameter("contextConfigLocation"));

        //完成了IOC DI 和mvc部分对接

        //初始化九大组件
        initStrategies(applicationContext);

//        doInitHandlerMapping();
    }

    private void initStrategies(CustomApplicationContext applicationContext) {
        //多文件上传组件
//        initMultipartResolver(applicationContext);
        //初始化本地语言环境
//        initLocalResolver(applicationContext);
        //初始化模板处理器
//        initThemeResolver(applicationContext);
        //初始化handlerMappings
        initHandlerMappings(applicationContext);
        //初始化参数适配器
        initHandlerAdapters(applicationContext);
        //初始化异常拦截器
//        initHandlerExceptionResolvers(applicationContext);
        //初始化视图预处理器
//        initRequestToViewNameTranslator(applicationContext);
        //初始化视图转换器
        initViewResolvers(applicationContext);
        //flash管理器
//        initFlashMapManger(applicationContext);
    }

    private void initViewResolvers(CustomApplicationContext applicationContext) {
        String templateRoot = applicationContext.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File tempplateRootDir = new File(templateRootPath);
        for(File file : tempplateRootDir.listFiles()){
            this.viewResolers.add(new CustomViewResolver(templateRoot));
        }
    }

    private void initHandlerAdapters(CustomApplicationContext applicationContext) {
        for(CustomHandlerMapping handlerMapping : handlerMappings){
            this.handlerAdapters.put(handlerMapping,new CustomHandlerAdapter());
        }
    }

    private void initHandlerMappings(CustomApplicationContext applicationContext) {
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
