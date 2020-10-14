package com.syp.springDemo.framework.context;

import com.syp.springDemo.framework.annotatin.CustomAutowired;
import com.syp.springDemo.framework.annotatin.CustomController;
import com.syp.springDemo.framework.annotatin.CustomService;
import com.syp.springDemo.framework.beans.CustomBeanWrapper;
import com.syp.springDemo.framework.beans.config.CustomBeanDefinition;
import com.syp.springDemo.framework.beans.support.CustomBeanDefinitionReader;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: SYP
 * @Date: 2020/4/6
 * @Description:
 */
public class CustomApplicationContext {

//    private String[] configLocations;

    private CustomBeanDefinitionReader reader;

    private Map<String,CustomBeanDefinition> beanDefinitionMap = new HashMap<String, CustomBeanDefinition>();
    private Map<String, CustomBeanWrapper> factoryBeanInstanceCache = new HashMap<String, CustomBeanWrapper>();
    private Map<String, Object> factoryBeanObjectCache = new HashMap<String, Object>();

    public CustomApplicationContext(String... contextConfigLocation) {
        //this.configLocations = contextConfigLocation;
        //加载配置文件
        reader = new CustomBeanDefinitionReader(contextConfigLocation);
        try {
            //解析配置文件，封装成BeanDefinition
            List<CustomBeanDefinition> beanDefinitions =  reader.loadBeanDefinitions();
            //把BeanDefinition 缓存起来
            doRegistBeanDefinition(beanDefinitions);

            doAutowired();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doAutowired() {
        for(Map.Entry<String, CustomBeanDefinition> beanDefinitionEntry : this.beanDefinitionMap.entrySet()){
            String beanName = beanDefinitionEntry.getKey();
            getBean(beanName);
        }
    }

    private void doRegistBeanDefinition(List<CustomBeanDefinition> beanDefinitions) throws Exception {
        for(CustomBeanDefinition beanDefinition : beanDefinitions){
            if(this.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw  new Exception("The "+ beanDefinition.getFactoryBeanName()+ " is exist");
            }
            beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
            beanDefinitionMap.put(beanDefinition.getBeanClassName(),beanDefinition);
        }
    }

    public Object getBean(String beanName){
        //先拿到BeanDefiniton配置信息
        CustomBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        //反射实例化
        Object instance = instantiateBean(beanName,beanDefinition);
        //封装成一个beanWrapper
        CustomBeanWrapper beanWrapper = new CustomBeanWrapper(instance);
        //保存到ioc容器
        factoryBeanInstanceCache.put(beanName,beanWrapper);

        //依赖注入
        populateBean(beanName,beanDefinition,beanWrapper);
        return beanWrapper.getWrapperInstance();
    }

    private void populateBean(String beanName, CustomBeanDefinition beanDefinition, CustomBeanWrapper beanWrapper) {
        Object instance = beanWrapper.getWrapperInstance();
        Class<?> clazz = beanWrapper.getWrappedClass();
        if(!(clazz.isAnnotationPresent(CustomController.class) || clazz.isAnnotationPresent(CustomService.class))){
            return;
        }

        for(Field field : clazz.getDeclaredFields()){
            if(!field.isAnnotationPresent(CustomAutowired.class)){
                continue;
            }

            CustomAutowired autowired = field.getAnnotation(CustomAutowired.class);

            String autowiredNBeanName = autowired.value().trim();

            if("".equals(autowiredNBeanName)){
                autowiredNBeanName = field.getType().getName();
            }

            field.setAccessible(true);
            if(this.factoryBeanInstanceCache.get(autowiredNBeanName) == null){
                continue;
            }

            try {
                field.set(instance,this.factoryBeanInstanceCache.get(autowiredNBeanName).getWrapperInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }


        }
    }

    private Object instantiateBean(String beanName, CustomBeanDefinition beanDefinition) {
        String className = beanDefinition.getBeanClassName();
        Object instance = null;
        try {
            if(this.factoryBeanObjectCache.containsKey(beanName)){
                instance = this.factoryBeanObjectCache.get(beanName);
            }
            Class<?> clazz = Class.forName(className);
            instance = clazz.newInstance();
            this.factoryBeanObjectCache.put(beanName,instance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return instance;
    }

    public Object getBean(Class clazz){
        return getBean(clazz.getName());
    }

    public int getBeanDefinitionCount() {
        return  this.beanDefinitionMap.size();
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }
}
