package cn.net.health.tools.thread;


/**
 * 演示 ThreadLocal 的用法2：避免参数传递的麻烦
 */
public class ThreadLocalNormalUsage06 {
    public static void main(String[] args) {
        new Service1().process();
    }
}

class Service1 {

    public void process() {
        User user = new User("鲁毅");
        //将User对象存储到 holder 中
        UserContextHolder.holder.set(user);
        new Service2().process();
    }
}

class Service2 {

    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service2拿到用户名: " + user.name);
        new Service3().process();
    }
}

class Service3 {

    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service3拿到用户名: " + user.name);
        //手动释放内存，从而避免内存泄漏
        UserContextHolder.holder.remove();
    }
}


class UserContextHolder {

    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

class User {

    String name;

    public User(String name) {
        this.name = name;
    }
}

