package cn.net.health.tools.list;

import cn.hutool.json.JSONUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/9 17:43
 */
public class HongBao {
    // 发红包算法，金额参数以分为单位
    public static List<Integer> divideRedPackage(Integer totalAmount, Integer totalPeopleNum) {

        List<Integer> amountList = new ArrayList<Integer>();

        Integer restAmount = totalAmount;

        Integer restPeopleNum = totalPeopleNum;

        Random random = new Random();

        for (int i = 0; i < totalPeopleNum - 1; i++) {

            // 随机范围：[1，剩余人均金额的两倍)，左闭右开
            int one = restAmount / restPeopleNum * 3;
            int amount = random.nextInt(one);
            System.out.println(amount + ".............");
            restAmount -= amount;
            restPeopleNum--;
            amountList.add(amount);
        }
        amountList.add(restAmount);

        return amountList;
    }


    public static void main(String[] args) {

        List<Integer> amountList = divideRedPackage(100, 10);
        int total = 0;
        for (Integer amount : amountList) {

            System.out.println("抢到金额：" + new BigDecimal(amount));
            total += amount;
        }
        System.out.println(total);
    }

}
