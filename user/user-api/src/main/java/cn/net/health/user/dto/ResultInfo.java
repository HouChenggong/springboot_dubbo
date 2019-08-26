package cn.net.health.user.dto;

import cn.net.health.user.util.Constant;

import java.io.Serializable;

public class ResultInfo<T> implements Serializable {

    private static final long serialVersionUID = -2042618546543630713L;

    public ResultInfo() {
    }

    public ResultInfo(T data) {
        this.data = data;
    }

    public ResultInfo(T data, int count) {
        this.data = data;
        this.count = count;
    }

    public ResultInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultInfo(String msg) {
        this.code = Constant.YES_ERROR;
        this.msg = msg;
    }

    public ResultInfo(String code, String msg,T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 错误信息
     */
    private String msg;
    /**
     * 错误号
     */
    private String code = Constant.NOT_ERROR;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 总记录数
     */
    private int count;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
