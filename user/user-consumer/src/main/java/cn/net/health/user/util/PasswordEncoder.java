package cn.net.health.user.util;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PasswordEncoder {

    public final static String SALT = "salt";
    public final static String PASSWORD = "passWord";

    /**
     * 创建盐值
     * @param length 盐值位数
     * @return
     */
    public static String createSalt(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 密码加密
     * @param userName 用户名
     * @param passWord 原密码
     * @return 盐值、加密密码
     */
    public static Map<String, String> enCodePassWord(String userName, String passWord){
        Map<String, String> map = new HashMap<>();
        //盐值
        String salt = createSalt(32);
        //散列次数
        int hashIterations = 2;
        //第1个参数：散列算法
        //第2个参数：明文，原始密码
        //第3个参数：盐，通过使用随机数
        //第4个参数：散列的次数，比如散列两次
        SimpleHash simpleHash = new SimpleHash("md5", passWord, userName + salt, hashIterations);
        map.put(SALT, salt);
        map.put(PASSWORD, simpleHash.toString());
        return  map;
    }

    public static void main(String[] args){
        //用户名
        String name = "test";

        //原始密码：
        String source = "123456";

        //盐值
        String salt = createSalt(32);

        System.out.println("salt:" + salt);

        //散列次数
        int hashIterations = 2;

        //第1个参数：散列算法
        //第2个参数：明文，原始密码
        //第3个参数：盐，通过使用随机数
        //第4个参数：散列的次数，比如散列两次
        SimpleHash simpleHash = new SimpleHash("md5", source, name + salt, hashIterations);

        System.out.println("加密密码:" + simpleHash.toString());
    }

}
