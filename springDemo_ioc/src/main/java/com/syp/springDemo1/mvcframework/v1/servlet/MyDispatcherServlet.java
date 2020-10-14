package com.syp.springDemo1.mvcframework.v1.servlet;
import com.syp.springDemo1.mvcframework.annotation.MyAutowired;
import com.syp.springDemo1.mvcframework.annotation.MyControllor;
import com.syp.springDemo1.mvcframework.annotation.MyRequestMapping;
import com.syp.springDemo1.mvcframework.annotation.MyService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;


/**
 * @Author: SYP
 * @Date: 2020/4/12
 * @Description:
 * 1.spring实现的基本思路
 * 1.配置阶段
 *      配置web.xml
 *      设定init-aram
 *      设定url-pattern
 *      配置Annotation
 * 2.初始化阶段
 *      调用init()方法
 *      IOC容器初始化
 *      扫描相关的类
 *      创建实例化并保存至相关的类  通过反射机制将类实例化放入IOC容器中
 *      进行DI操作 扫描ioc容器中的实例，给没有赋值的属性自动赋值
 *      初始化HandlerMapping
 * 3.运行阶段
 *      调用doPost()/ooGet() web容器调用者两个方法，获得request和response对象
 *      匹配HandlerMapping 从request对象汇总获取用户输入的url,找到其对应的method
 *      反射调用method.invoke()
 *      response.getWrite().write()
 *
 *
 *
 */
public class MyDispatcherServlet extends HttpServlet{
    private Properties contextConfig = new Properties();
    private List<String> classNames = new ArrayList<String>();
    //ioc容器，key默认首字母小写
    Map<String,Object> ioc = new HashMap<String, Object>();
    private Map<String,Method> handlerMapping = new HashMap<String, Method>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6.委派 根据url去找到一个对应的Method 并通过response返回
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 internal error");
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");
        if(!this.handlerMapping.containsKey(url)){
            resp.getWriter().write("404 not found");
            return;
        }

        Map<String,String[]> params = req.getParameterMap();
        Method method = handlerMapping.get(url);
        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(ioc.get(beanName),new Object[]{req,resp,params.get("name")[0]});

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        //2.扫描相关的类
        doScanner(contextConfig.getProperty("scanPackage"));
        //3.初始化IOC容器，将扫描到的类实例化，保存到IOC容器
        try {
            doInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //4.完成依赖注入
        doAutowried();
        //5.初始化HandlerMapping
        doHandlerMapping();
    }

    private void doHandlerMapping() {
        if(ioc.isEmpty()){
            return;
        }
        for(Map.Entry<String,Object> entry : ioc.entrySet()){
            Class<?> clazz  = entry.getValue().getClass();

            if(!clazz.isAnnotationPresent(MyControllor.class)){
                continue;
            }

            String baseUrl = "";
            if(clazz.isAnnotationPresent(MyRequestMapping.class)){
                MyRequestMapping requestMapping = clazz.getAnnotation(MyRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            for(Method method : clazz.getMethods()){
               if(!method.isAnnotationPresent(MyRequestMapping.class)){
                   continue;
               }
                MyRequestMapping mapping = method.getAnnotation(MyRequestMapping.class);
                String url = ("/"+baseUrl +"/"+ mapping.value()).replaceAll("/+","/");
                handlerMapping.put(url,method);
                System.out.println("Mapped "+url + "Method "+method.getName());
            }
        }

    }

    private void doAutowried() {
        if(ioc.isEmpty()){
            return;
        }
        for(Map.Entry<String,Object> entry : ioc.entrySet()){
            for(Field field :entry.getValue().getClass().getDeclaredFields()){
                if(field.isAnnotationPresent(MyAutowired.class)){
                    MyAutowired autowired = field.getAnnotation(MyAutowired.class);
                    String beanName = autowired.value().trim();

                    if("".equals(beanName)){
                        //获取字段的类型
                        beanName = field.getType().getName();
                    }

                    field.setAccessible(true);
                    try {
                        //相当于通过接口的全名拿到接口的实现的实例
                        field.set(entry.getValue(),ioc.get(beanName));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void doInstance() throws Exception {
        if(classNames.isEmpty()){
            return;
        }
        for(String className :classNames){
            try {
                Class<?> clazz = Class.forName(className);
                if(clazz.isAnnotationPresent(MyControllor.class)){
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    Object instance = clazz.newInstance();
                    ioc.put(beanName,instance);
                }else if(clazz.isAnnotationPresent(MyService.class)){
                    //1.默认的类名首字母小写
                    String beanName = clazz.getAnnotation(MyService.class).value();
                    if("".equals(beanName.trim())){
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName,instance);
                    //2.在多个包下的同名类，自定义命名
                    //3.接口 每个实现类
                    for(Class<?> i : clazz.getInterfaces()){
                        if(ioc.containsKey(i.getName())){
                            throw new Exception("The "+i.getName()+"is exists");
                        }
                        ioc.put(i.getName(),instance);
                    }

                }else{
                    continue;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private void doScanner(String scanPackage) {
        URL url =  this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for(File file :classPath.listFiles()){
            if(file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else{
                if(file.getName().endsWith(".class")){
                    String className =scanPackage + "."+ file.getName().replace(".class", "");
                    classNames.add(className);
                }
            }
        }
    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
