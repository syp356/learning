package com.syp.springDemo.framework.beans.support;

import com.syp.springDemo.framework.beans.config.CustomBeanDefinition;

import javax.print.DocFlavor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author: SYP
 * @Date: 2020/4/6
 * @Description:
 */
public class CustomBeanDefinitionReader {

    private List<String> registryBeanClasses = new ArrayList<String>();
    private Properties contextConfig = new Properties();

    public CustomBeanDefinitionReader(String[] contextConfigLocation) {
//        this.configLicaions = contextConfigLocation;
        doLoadConfig(contextConfigLocation[0]);

        doScanner(contextConfig.getProperty("scanPackage"));
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.","/"));
        File classPath  = new File(url.getFile());

        for(File file : classPath.listFiles()){
            if(file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else{
                if(!file.getName().endsWith(".class")){
                    continue;
                }
                String className = (scanPackage + "." + file.getName().replace(".class",""));
                registryBeanClasses.add(className);
            }

        }

    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation.replaceAll("classpath:",""));
        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public List<CustomBeanDefinition> loadBeanDefinitions() {
        List<CustomBeanDefinition> result = new ArrayList<>();
        try{
            for(String className : registryBeanClasses){
                Class<?> beanClass = Class.forName(className);
                if(beanClass.isInterface()){
                    continue;
                }
                result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()),beanClass.getName()));
                for(Class<?> i : beanClass.getInterfaces()){
                    result.add(doCreateBeanDefinition(i.getName(),beanClass.getName()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private CustomBeanDefinition doCreateBeanDefinition(String beanName, String beanClassName) {
        CustomBeanDefinition beanDefinition = new CustomBeanDefinition();
        beanDefinition.setFactoryBeanName(beanName);
        beanDefinition.setBeanClassName(beanClassName);
        return beanDefinition;
    }

    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
//        if(chars[0] > )
        chars[0] += 32;
        return String.valueOf(chars);

    }

}
