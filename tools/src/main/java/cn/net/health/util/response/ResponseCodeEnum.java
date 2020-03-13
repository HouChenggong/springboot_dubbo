package cn.net.health.util.response;

/**
 * @author qinc
 * @version V1.0
 * @ClassName: ResponseCode
 * @Description: TODO
 * @Date: 2019/12/30 14:10
 */
public enum ResponseCodeEnum {
    SUCCESS(0, "success"),
    ERROR(1, "error");

    private final int CODE;
    private final String DESC;

    ResponseCodeEnum(int code, String desc) {
        this.CODE = code;
        this.DESC = desc;
    }

    public int getCode() {
        return CODE;
    }

    public String getDesc() {
        return DESC;
    }


}
