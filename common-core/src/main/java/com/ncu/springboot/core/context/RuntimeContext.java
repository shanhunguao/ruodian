package com.ncu.springboot.core.context;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.PriorityOrdered;

import com.ncu.springboot.core.jdkextends.NcuOptional;
import com.ncu.springboot.core.util.CollectionUtil;

/**
 * 运行时环境,包含applicationContext , servletContext
 */
/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无BUG
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */
public class RuntimeContext {

    private static RuntimeContext runtimeContext = new RuntimeContext();

    private RuntimeContext() {
    }

    public static RuntimeContext getInstance() {
        return runtimeContext;
    }

    public static class SpringContext implements ApplicationContextAware, BeanFactoryPostProcessor, PriorityOrdered {
        private static SpringContext springContext = new SpringContext();
        private ApplicationContext applicationContext;
        private BeanFactory beanFactory;

        private SpringContext() {
        }

        public static SpringContext getInstance() {
            return springContext;
        }

        public ApplicationContext getSpringContext() {
            if (null == applicationContext) {
                throw new RuntimeException(
                        "Spring ApplicationContext is null. Please config this Class as a spring bean. e.g. <bean class='"
                                + SpringContext.class.getName() + "' factory-method='getInstance' />");
            }
            return applicationContext;
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }

        public BeanFactory getBeanFactory() {
            return beanFactory;
        }

        @Override
        public int getOrder() {
            return PriorityOrdered.HIGHEST_PRECEDENCE;
        }
    }

    @WebListener
    public static class NcuServletContext implements ServletContextListener {
        private static NcuServletContext instance = new NcuServletContext();

        private ServletContext servletContext;

        public static NcuServletContext getInstance() {
            return instance;
        }

        public ServletContext getServletContext() {
            if (null == servletContext) {
                throw new RuntimeException(
                        "Servlet Context is null. Please config this Class as a web-app ServletContextListener. e.g. <listener><listener-class>"
                                + ServletContext.class.getName() + "</listener-class></listener>");
            }
            return servletContext;
        }

        @Override
        public void contextInitialized(ServletContextEvent sce) {
        	NcuServletContext.getInstance().servletContext = sce.getServletContext();
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
        }

    }

    public static ApplicationContext getSpringContext() {
        return SpringContext.getInstance().getSpringContext();
    }

    /**
     * 根据bean的名称从IOC中查找bean类
     *
     * @param name 待查找的bean名称
     *
     * @return 该名称所对应的bean对象 ， 如果查不到则为null
     * */
    public static Object getBean(String name) {
        return getBean(name, true);
    }

    /**
     * 根据bean的名称从IOC中查找bean类
     *
     * @param name 待查找的bean名称
     * @param requiredType 被期望的此bean对象的类型
     *
     * @return 该名称且期望类型相符合的bean对象 ， 如果查不到则为null
     * */
    public static <T> NcuOptional<T> getBean(String name, Class<T> requiredType) {
        return getBean(name, requiredType, true);
    }

    /**
     * 根据bean的名称从IOC中查找bean类
     *
     * @param requiredType 被期望的此bean对象的类型
     *
     * @return 该名称所对应的bean对象 ， 如果查不到则为null
     * */
    public static <T> T getBean(Class<T> requiredType) {
        return getBean(requiredType, true);
    }

    /**
     * 判断指定名称的bean是否存在
     *
     * @param beanName 执行的bean名称
     *
     * @return 如果该名称所对应的bean存在则返回true，否则返回false
     * */
    public static boolean hasBean(String beanName) {
        try {
            return null == getBean(beanName);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断指定名称的bean是否存在
     *
     * @param beanName 执行的bean名称
     * @param beanType 被期望的此bean对象的类型
     *
     * @return 该名称且期望类型相符合的bean对象存在则返回true，否则返回false
     * */
    public static boolean hasBean(String beanName, Class<?> beanType) {
        try {
            return getBean(beanName, beanType).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 动态往IOC容器中注册bean ， 如果此bean名称已经注册到IOC容器中则不进行注册
     *
     * @param beanName 待注册的bean名称
     * @param bean 待注册的bean对象
     * @param propertyValueMap 该bean对象的属性值-value映射
     *
     * @return 如果添加成功则返回true
     * */
    public static boolean registerBean(String beanName, Object bean, Map<String, Object> propertyValueMap) {
        return !hasBean(beanName) && addBeanInternal(beanName, bean, propertyValueMap);
    }

    /**
     * 动态往IOC容器中注册bean ， 如果此bean名称已经注册到IOC容器中则不进行注册
     *
     * @param beanName 待注册的bean名称
     * @param bean 待注册的bean对象
     *
     * @return 如果添加成功则返回true
     * */
    public static boolean registerBean(String beanName, Object bean) {
        return registerBean(beanName , bean , new HashMap<>());
    }

    /**
     * 根据bean名称与指定期望bean的类型从IOC中将该bean移除
     *
     * @param beanName 待移除的beanName
     * @param beanType 期望该bean对象的类型
     *
     * @return 如果添加成功则返回true
     * */
    public static boolean removeBean(String beanName, Class<?> beanType) {
        return hasBean(beanName, beanType) && removeBeanInternal(beanName);
    }

    /**
     * 根据指定的bean名称从IOC中移除bean
     *
     * @param beanName 待移除的beanName
     *
     * @return 如果移除成功则返回true
     * */
    private static boolean removeBeanInternal(String beanName) {
        BeanFactory beanFactory = SpringContext.getInstance().getBeanFactory();
        if (beanFactory instanceof DefaultListableBeanFactory) {
            ((DefaultListableBeanFactory) beanFactory).removeBeanDefinition(beanName);
            return true;
        }
        return false;
    }

    /**
     * 动态往IOC容器中注册bean
     *
     * @param beanName 待注册的bean名称
     * @param bean 待注册的bean对象
     * @param propertyValueMap 该bean对象的属性值-value映射
     *
     * @return 如果添加成功则返回true
     * */
    private static boolean addBeanInternal(String beanName, Object bean, Map<String, Object> propertyValueMap) {
        BeanFactory beanFactory = SpringContext.getInstance().getBeanFactory();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(bean.getClass());
        if ( !CollectionUtil.isEmpty(propertyValueMap) ) {
            propertyValueMap.forEach( (key , value) -> builder.addPropertyValue(key, value) );
        }
        if (beanFactory instanceof DefaultListableBeanFactory) {
            ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(beanName, builder.getBeanDefinition());
            return true;
        }
        return false;
    }

    /**
     * 从IOC中获取出所有指定类型的bean
     *
     * @param requiredType 指定bean的类型
     *
     * @return 指定bean类型的对象集合
     * */
    public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
        return getBeansOfType(requiredType, true);
    }

    /**
     * 从IOC中获取出所有拥有特定注解的Beans集合
     *
     * @param annotationType 指定注解类型
     *
     * @return 返回所有的拥有特定注解的bean对象集合
     * */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return getBeansWithAnnotation(annotationType, true);
    }

    /**
     * 根据bean的名称从IOC中查找bean类
     *
     * @param name 待查找的bean名称
     * @param searchInAncestor 在父IOC中查找 ， 如果在子级IOC中未找到指定的bean，则逐级往上查找
     *
     * @return 该名称所对应的bean对象 ， 如果查不到则为null
     * */
    public static Object getBean(String name, boolean searchInAncestor) {
        Object bean = getSpringContext().getBean(name);
        if (null == bean && searchInAncestor) {
            ApplicationContext parentContext;
            while (null == bean && null != (parentContext = getSpringContext().getParent())) {
                bean = parentContext.getBean(name);
            }
        }
        return bean;
    }

    /**
     * 根据bean的名称从IOC中查找bean类
     *
     * @param name 待查找的bean名称
     * @param requiredType 被期望的此bean对象的类型
     * @param searchInAncestor 在父IOC中查找 ， 如果在子级IOC中未找到指定的bean，则逐级往上查找
     *
     * @return 该名称且期望类型相符合的bean对象 ， 如果查不到则为null
     * */
    public static <T> NcuOptional<T> getBean(String name, Class<T> requiredType, boolean searchInAncestor) {
        try {
            T bean = getSpringContext().getBean(name, requiredType);
            if (null == bean && searchInAncestor) {
                ApplicationContext parentContext;
                while (null == bean && null != (parentContext = getSpringContext().getParent())) {
                    bean = parentContext.getBean(name, requiredType);
                }
            }
            return NcuOptional.of(bean);
        }catch (Exception e) {
            return NcuOptional.empty();
        }
    }

    /**
     * 根据bean的名称从IOC中查找bean类
     *
     * @param requiredType 被期望的此bean对象的类型
     * @param searchInAncestor 在父IOC中查找 ， 如果在子级IOC中未找到指定的bean，则逐级往上查找
     *
     * @return 该名称所对应的bean对象 ， 如果查不到则为null
     * */
    public static <T> T getBean(Class<T> requiredType, boolean searchInAncestor) {
        T bean = getSpringContext().getBean(requiredType);
        if (null == bean && searchInAncestor) {
            ApplicationContext parentContext;
            while (null == bean && null != (parentContext = getSpringContext().getParent())) {
                bean = parentContext.getBean(requiredType);
            }
        }
        return bean;
    }

    /**
     * 从IOC中获取出所有指定类型的beans
     *
     * @param requiredType 指定bean的类型
     * @param searchInAncestor 在父IOC中查找 ， 如果在子级IOC中未找到指定的bean，则逐级往上查找
     *
     * @return 指定bean类型的对象集合
     * */
    public static <T> Map<String, T> getBeansOfType(Class<T> requiredType, boolean searchInAncestor) {
        Map<String, T> beans = getSpringContext().getBeansOfType(requiredType);
        if (CollectionUtil.isEmpty(beans) && searchInAncestor) {
            ApplicationContext parentContext;
            while (beans.isEmpty() && null != (parentContext = getSpringContext().getParent())) {
                beans = parentContext.getBeansOfType(requiredType);
            }
        }
        return beans;
    }

    /**
     * 从IOC中获取出所有拥有特定注解的Beans集合
     *
     * @param annotationType 指定注解类型
     * @param searchInAncestor 在父IOC中查找 ， 如果在子级IOC中未找到指定的bean，则逐级往上查找
     *
     * @return 返回所有的拥有特定注解的bean对象集合
     * */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType,
                                                                 boolean searchInAncestor) {
        Map<String, Object> beans = getSpringContext().getBeansWithAnnotation(annotationType);
        if (CollectionUtil.isEmpty(beans) && searchInAncestor) {
            ApplicationContext parentContext;
            while (beans.isEmpty() && null != (parentContext = getSpringContext().getParent())) {
                beans = parentContext.getBeansWithAnnotation(annotationType);
            }
        }
        return beans;
    }

    public static void publishEvent(ApplicationEvent event) {
        getSpringContext().publishEvent(event);
    }

    public static ServletContext getServletContext() {
        return NcuServletContext.getInstance().getServletContext();
    }

}
