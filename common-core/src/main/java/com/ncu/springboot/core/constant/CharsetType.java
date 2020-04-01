package com.ncu.springboot.core.constant;

import java.nio.charset.Charset;

/**
 * Created by xuyn15828 on 2016/6/22.
 */
public interface CharsetType {
    Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
    Charset CHARSET_GBK = Charset.forName("GBK");

}
