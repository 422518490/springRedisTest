package com.springRedis.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author liaoyubo
 * @version 1.0 2017/8/11
 * @description
 */
public class LoadBean implements BeanFactoryPostProcessor {

    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        this.defaultListableBeanFactory = (DefaultListableBeanFactory)configurableListableBeanFactory;

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition("com.springRedis.pipeline.DynamicCreateBean");
        this.defaultListableBeanFactory.registerBeanDefinition("dynamicCreateBean",beanDefinitionBuilder.getBeanDefinition());
    }
}
