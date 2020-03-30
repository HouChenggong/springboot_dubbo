//package cn.net.health.util.file;
//
//import java.io.*;
//
///**
// * @author xiyou
// */
//public class SerializableTest {
//
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        serializeNY();
//        Xiyou ny = deserializeNY();
//        System.out.println(ny.toString());
//    }
//
//    private static void serializeNY() throws IOException {
//        Xiyou ny = new Xiyou();
//        ny.setName("xiyou");
//        ny.setBlogName("xiyou-blog");
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("D:\\serialable.txt")));
//        oos.writeObject(ny);
//        System.out.println("NY 对象序列化成功！");
//        oos.close();
//    }
//
//    private static Xiyou deserializeNY() throws IOException, ClassNotFoundException {
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("D:\\serialable.txt")));
//        Xiyou ny = (Xiyou) ois.readObject();
//        System.out.println("NY 对象反序列化成功");
//        return ny;
//    }
//}
