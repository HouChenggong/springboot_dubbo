package com.mydubbo.common.clone;

public class CopyTest {
    public static void main(String[] args) {
        YourDog dog1 =new YourDog();
        dog1.setDogName("dog1");
        Person p1 =new Person();
        p1.setName("p1");
        p1.setDog(dog1);

        Person p1Copyy =new Person(p1);
        System.out.println("被克隆对象: " + p1.hashCode() + "\n" +
                "被克隆对象内的dog属性: " + p1.getDog().hashCode() + "         " + p1.toString());

        System.out.println("克隆出来的对象: " + p1Copyy.hashCode() + "\n" +
                "克隆出来的对象内的dog属性: " + p1Copyy.getDog().hashCode() + "         " + p1Copyy.toString());

        System.out.println("------------------上面是直接进行深复制，不改变复制后的对象任何值----------------------");


        YourDog dog2 =new YourDog();
        dog2.setDogName("dog2");
        Person p2 =new Person();
        p2.setName("p2");
        p2.setDog(dog2);

        Person p2Copy =new Person(p2);
        p2Copy.setName("p2Copy");
        System.out.println("被克隆对象: " + p2.hashCode() + "\n" +
                "被克隆对象内的dog属性: " + p2.getDog().hashCode() + "         " + p2.toString());

        System.out.println("克隆出来的对象: " + p2Copy.hashCode() + "\n" +
                "克隆出来的对象内的dog属性: " + p2Copy.getDog().hashCode() + "         " + p2Copy.toString());

        System.out.println("--------------上面是改变复制后对象的普通属性，不改变引用类属性--------------------------");



        YourDog dog3 =new YourDog();
        dog3.setDogName("dog3");
        Person p3 =new Person();
        p3.setName("p3");
        p3.setDog(dog3);

        Person p3Copy =new Person(p3);

        YourDog dog3Copy =new YourDog();
        dog3Copy.setDogName("dog3Copy");
        p3Copy.setDog(dog3Copy);
        System.out.println("被克隆对象: " + p3.hashCode() + "\n" +
                "被克隆对象内的dog属性: " + p3.getDog().hashCode() + "         " + p3.toString());

        System.out.println("克隆出来的对象: " + p3Copy.hashCode() + "\n" +
                "克隆出来的对象内的dog属性: " + p3Copy.getDog().hashCode() + "         " + p3Copy.toString());
        System.out.println("--------------上面是改变复制后对象的引用类的属性，不改变普通属性--------------------------");

    }

    /**
     * 可见通过拷贝构造函数实现深克隆的方式其实和序列号的方式效果是一样的
     */

}
