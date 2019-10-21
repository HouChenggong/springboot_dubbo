package cn.net.health.tools.str;

public class Idea {

    public static void main(String[] args) {
        String json1 = "123";
        String json2 = "123";
        System.out.println(json1 == json2);//true
        String json3 = json2 + "";

        /**
         * json3底层走了strinBuilder，进行字符串拼接，然后调用StringBuffer的toString方法
         *
         * 通过变量和字符串拼接，java 是需要先到内存找变量对应的值，
         * 才能进行完成字符串拼接的工作，这种方式 java 编译器没法优化，
         * 只能走 StringBuilder 进行拼接字符串，然后调用 toString 方法，
         * 当然返回的结果和常量池中的 111 这个字符串的内存地址是不一样的，因此结果为 false。
         *
         */
        System.out.println(json1 == json3);//false
        String json4 = "123" + "";
        /**
         * json4没有走stringBuider进行字符串拼接
         * 直接在表达式里写值，java 不用根据变量去内存里找对应的值，
         * 可以在编译的时候直接对这个表达式进行优化，优化后的表达式从 "111" + ""直接变成了"111" ，
         * 两个 String 类型的变量都指向了常量池的 111 字符串，因此结果为 true;
         *
         */
        System.out.println(json1 == json4);//true


        System.out.println(json3 == json4);


    }
}
