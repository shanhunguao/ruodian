package com.ncu.springboot.core.springExt.resource;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.ncu.springboot.core.context.RuntimeContext;
import com.ncu.springboot.core.util.StringUtil;
import com.ncu.springboot.core.util.ValueUtil;

/**
 * Created by xuyn15828 on 2016/5/24.
 */
public class NcuResourceLoader extends DefaultResourceLoader {
    private Boolean enable_classpath;
    private static final String CLASSPATH_PREFIX = "classpath";

    @Override
    public Resource getResource(String location) {
        ApplicationContext ac = RuntimeContext.getSpringContext();
        if (ac instanceof ResourcePatternResolver) {
            ResourcePatternResolver resolver = ac;
            try {
                Resource[] resources = resolver.getResources(location);
                if (null != resources && 0 != resources.length) {
                    Resource resource = resources[0];
                    if (resource.exists()) {
                        return resource;
                    }
                }
            } catch (Exception e) {
            }
            if (enable_classpath && !StringUtil.startWithIgnoreCase(location, CLASSPATH_PREFIX)) {
                location = CLASSPATH_PREFIX + "*:" + location;
                return getResource(location);
            }
        }
        return super.getResource(location);
    }

    public boolean isEnable_classpath() {
        return enable_classpath;
    }

    public void setEnable_classpath(Boolean enable_classpath) {
        this.enable_classpath = ValueUtil.getBoolean(enable_classpath, true);
    }
}
