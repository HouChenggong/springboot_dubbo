package cn.net.health.user.aop.staticaop;


/**
 * @author xiyou
 * 静态代理对象
 */
public class Proxy2StudentServiceImpl implements PersionService {

    private PersionService persionService;

    public Proxy2StudentServiceImpl(PersionService sa) {
        this.persionService = sa;
    }

    @Override
    public void sayHello(String name) {
        System.out.println("proxy2..... start" + name);
        persionService.sayHello(name);
        System.out.println("proxy2..... end" + name);

    }

    public static void main(String[] args) {


        Proxy2StudentServiceImpl proxy2 = new Proxy2StudentServiceImpl(new ProxyStudentServiceImpl(new StudentServiceImpl()));
        proxy2.sayHello("xiyou");
        System.out.println("-----------------------");
        ProxyStudentServiceImpl proxy3 = new ProxyStudentServiceImpl(new Proxy2StudentServiceImpl(new StudentServiceImpl()));
        proxy3.sayHello("xiyou");
    }
}
