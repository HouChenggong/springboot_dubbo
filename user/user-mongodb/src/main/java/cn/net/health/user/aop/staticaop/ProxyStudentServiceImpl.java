package cn.net.health.user.aop.staticaop;


/**
 * @author xiyou
 * 静态代理对象
 */
public class ProxyStudentServiceImpl implements PersionService {

    private PersionService persionService;

    public ProxyStudentServiceImpl(PersionService sa) {
        this.persionService = sa;
    }

    @Override
    public void sayHello(String name) {
        System.out.println("proxy start" + name);
        persionService.sayHello(name);
        System.out.println("proxy end" + name);

    }

    public static void main(String[] args) {

        ProxyStudentServiceImpl proxy = new ProxyStudentServiceImpl(new StudentServiceImpl());
        ProxyStudentServiceImpl proxy2 = new ProxyStudentServiceImpl(new Student2ServiceImpl());
        proxy.sayHello("xiyou");
        proxy2.sayHello("111111xiyou");
    }
}
