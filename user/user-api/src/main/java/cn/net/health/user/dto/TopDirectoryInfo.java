package cn.net.health.user.dto;

public class TopDirectoryInfo {

    private String name;

    private String code;

    public TopDirectoryInfo() {

    }

    public TopDirectoryInfo(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
