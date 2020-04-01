package com.ncu.springboot.core.springExt.resource;

import com.ncu.springboot.core.util.ReflectionUtil;
import com.ncu.springboot.core.util.StringUtil;
import com.ncu.springboot.core.util.ValueUtil;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

/**
 * 为了防止因为ResourceLoaderAware导致spring的resourceLoader被注入,进行控制
 */
public class NcuReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {
    /**
     * Spring framework的包名头
     * */
    private static final String SPRING_NAMESPACE = "spring";
    /**
     * 由配置文件注入 ， 判断使用的是否自身的resourceLoader
     * */
    protected Boolean useSelfResource;

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        if ( ValueUtil.getBoolean(useSelfResource) ) {
            if (shouldBuildDefaultResourceLoader(resourceLoader)) {
                resourceLoader = new DefaultResourceLoader();
            }
            setSuperResourceLoader(resourceLoader);
        } else {
            setSuperResourceLoader(resourceLoader);
        }
    }

    private boolean shouldBuildDefaultResourceLoader(ResourceLoader resourceLoader) {
        boolean currentUseSelfResourceLoader = null != this.resourceLoader &&
                !StringUtil.containsIgnoreCase(this.resourceLoader.getClass().getName(), SPRING_NAMESPACE);
        if (resourceLoader != null) {
            String clazzName = resourceLoader.getClass().getName();
            //如果新resourceLoader不是spring产生的
            //并且当前使用的resourceLoader也是自己的,则进行打印替换日志并替换
            return !StringUtil.containsIgnoreCase(clazzName, SPRING_NAMESPACE) && currentUseSelfResourceLoader;
        }
        return !currentUseSelfResourceLoader;
    }

    private void setSuperResourceLoader(ResourceLoader resourceLoader) {
        boolean isSelfResourceLoader = resourceLoader != null &&
                !StringUtil.containsIgnoreCase(ReflectionUtil.getClassName(resourceLoader), SPRING_NAMESPACE);
        if (isSelfResourceLoader || null == this.resourceLoader) {
            this.resourceLoader = resourceLoader;
            super.setResourceLoader(resourceLoader);
        }
    }

    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    public void setUseSelfResource(Boolean useSelfResource) {
        this.useSelfResource = ValueUtil.getBoolean(useSelfResource, false);
    }
}
