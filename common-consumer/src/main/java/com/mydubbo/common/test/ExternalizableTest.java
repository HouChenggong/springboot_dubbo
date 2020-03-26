package com.mydubbo.common.test;

import java.io.*;

/**
 * @author xiyou
 */
public class ExternalizableTest implements Externalizable {

    private transient String content = "即使被transient修饰，我也会序列化";

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(content);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        content = (String)in.readObject();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ExternalizableTest et = new ExternalizableTest();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("D:\\externalizable.txt")));
        oos.writeObject(et);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("D:\\externalizable.txt")));
        et = (ExternalizableTest) ois.readObject();
        System.out.println(et.content);
        oos.close();
        ois.close();
    }
}
