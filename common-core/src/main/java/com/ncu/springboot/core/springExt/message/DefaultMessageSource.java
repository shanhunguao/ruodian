package com.ncu.springboot.core.springExt.message;

import com.ncu.springboot.core.springExt.resource.NcuReloadableResourceBundleMessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class DefaultMessageSource extends NcuReloadableResourceBundleMessageSource implements ExtendMessageSource {

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage) {
        return getMessage(code, args, defaultMessage, null);
    }

    @Override
    public String getMessage(String code, Object[] args) throws NoSuchMessageException {
        return getMessage(code, args, Locale.CHINA);
    }

    @Override
    public String getMessage(String code, String defaultMessage) {
        return getMessage(code, null, defaultMessage);
    }

    @Override
    public String getMessage(String code) throws NoSuchMessageException {
        return getMessage(code, (Object[]) null);
    }

}
