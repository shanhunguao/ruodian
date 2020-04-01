package com.ncu.springboot.core.util;

import com.ncu.springboot.core.constant.CharsetType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlUtil {
    public static final String DEFAULT_HEADER = "<?xml version=\"1.0\" encoding=\" UTF-8\"?>";

    public static String toXMLWithHeader(Object obj) {
        return DEFAULT_HEADER + toXml(obj);
    }

    public static String toXml(Object obj) {
        XStream xstream = new XStream(new DomDriver(CharsetType.CHARSET_UTF_8.name()));
        xstream.processAnnotations(obj.getClass());
        return xstream.toXML(obj).replace("__", "_");
    }

    public static <T> T toBean(String xmlStr, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver(CharsetType.CHARSET_UTF_8.name()));
        xstream.processAnnotations(cls);
        T obj = (T) xstream.fromXML(xmlStr);
        return obj;
    }


}
