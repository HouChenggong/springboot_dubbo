package com.mydubbo.common.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mydubbo.common.service.ITestTransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("事务传播行为")
@RestController
public class TranscationController {


    @Reference(version = "${demo.service.version}")
    private ITestTransactionService transactionService;

    @ApiOperation(value = "baoHan1", notes = "获取语言信息")
    @GetMapping("/baoHan1")
    public void baoHan1() {
        transactionService.test1();
    }

    @ApiOperation(value = "baoHan2", notes = "获取语言信息")
    @GetMapping("/baoHan2")
    public void baoHan2() {
        transactionService.test2();
    }

    @ApiOperation(value = "baoHan3", notes = "获取语言信息")
    @GetMapping("/baoHan3")
    public void baoHan3() {
        transactionService.test3();
    }

    @ApiOperation(value = "baoHan4", notes = "获取语言信息")
    @GetMapping("/baoHan4")
    public void baoHan4() {
        transactionService.test4();
    }


    @ApiOperation(value = "baoHan4", notes = "获取语言信息")
    @GetMapping("/baoHan5")
    public void baoHan5() {
        transactionService.test5();
    }
}
