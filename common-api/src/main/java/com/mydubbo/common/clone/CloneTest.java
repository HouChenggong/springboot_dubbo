package com.mydubbo.common.clone;

import org.apache.commons.lang3.SerializationUtils;

public class CloneTest {
    public static void main(String[] args) {
        YourDog dog1 =new YourDog();
        dog1.setDogName("dog1");
        Person source = new Person();
        source.setName("p1");
        source.setDog(dog1);
        Person target = (Person) SerializationUtils.clone(source);
        System.out.println("被克隆对象: " + source.hashCode() + "\n" +
                "被克隆对象内的dog属性: " + source.getDog().hashCode() + "         " + source.toString());

        System.out.println("克隆出来的对象: " + target.hashCode() + "\n" +
                "克隆出来的对象内的dog属性: " + target.getDog().hashCode() + "         " + target.toString());

        System.out.println("------------------上面是直接进行深复制，不改变复制后的对象任何值----------------------");
        YourDog dog2 =new YourDog();
        dog2.setDogName("dog2");
        Person source2 = new Person();
        source2.setName("p2");
        source2.setDog(dog2);
        Person target2 = (Person) SerializationUtils.clone(source2);
        target2.setName("copy2");

        System.out.println("被克隆对象: " + source2.hashCode() + "\n" +
                "被克隆对象内的dog属性: " + source2.getDog().hashCode() + "         " + source2.toString());

        System.out.println("克隆出来的对象: " + target2.hashCode() + "\n" +
                "克隆出来的对象内的dog属性: " + target2.getDog().hashCode() + "         " + target2.toString());




        System.out.println("--------------上面是改变复制后对象的普通属性，不改变引用类属性--------------------------");
        YourDog dog3 =new YourDog();
        dog3.setDogName("dog3");
        Person source3= new Person();
        source3.setName("p3");
        source3.setDog(dog3);

        Person target3 = (Person) SerializationUtils.clone(source3);
        YourDog dog =new YourDog();
        dog.setDogName("dogCopy3");
        target3.setDog(dog);
        System.out.println("被克隆对象: " + source3.hashCode() + "\n" +
                "被克隆对象内的dog属性: " + source3.getDog().hashCode() + "         " + source3.toString());

        System.out.println("克隆出来的对象: " + target3.hashCode() + "\n" +
                "克隆出来的对象内的dog属性: " + target3.getDog().hashCode() + "         " + target3.toString());

        System.out.println("--------------上面是改变复制后对象的引用类的属性，不改变普通属性--------------------------");
    }


    /**
     * 结果说明了：
     *   假设原来的对象 A 包含了一个字段 a1 ,和引用类型对象 B
     * 1. 即使是深度克隆，只要不改变被克隆对象的值，原来对象和克隆对象都是指向同一块内存区域，是一样的
     * 2. 如果只是改变了被克隆后对象的普通属性a1（基本类型），而不改变引用类型的变量B，那么原本的B不变，会克隆一个新的A对象
     * 3. 如果改变了引用类型的属性，不改变基本类型的，将会克隆出A和B两个新对象
     *
     */
}
