/**
 * Copyright 2015 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ncu.springboot.core.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.ncu.springboot.core.jdkextends.NcuOptional;

public final class SerializeUtil {

    public static NcuOptional<byte[]> serializeByHessian(Object obj) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            HessianOutput output = new HessianOutput(os);
            output.writeObject(obj);
            return NcuOptional.of(os.toByteArray());
        } catch (IOException e) {
        }
        return NcuOptional.empty();
    }

    public static NcuOptional<Object> unSerializeByHessian(byte[] bytes) {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        HessianInput input = new HessianInput(is);
        try {
            return NcuOptional.of(input.readObject());
        } catch (IOException e) {
        }
        return NcuOptional.empty();
    }

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtil.close(oos, baos);
        }
    }

    public static NcuOptional<Object> unserialize(byte[] bytes) {
        if (bytes == null) {
            return NcuOptional.empty();
        }
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return NcuOptional.of(ois.readObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtil.close(bais);
        }
    }

}
