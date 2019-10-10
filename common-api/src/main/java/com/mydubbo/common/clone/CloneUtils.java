package com.mydubbo.common.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CloneUtils {
    /**
     * 要实现深度克隆，必须要求，被克隆对象和被克隆对象里面被引用的对象都实现序列号接口
     * @param obj
     * @return
     */
    public static Object clone(Object obj) {
        Object cloneObj = null;
        try {
            //写入字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(obj);
            obs.close();

            //分配内存，写入原始对象，生成新对象
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);

            //返回生成的新对象
            cloneObj = ois.readObject();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
    }
}
