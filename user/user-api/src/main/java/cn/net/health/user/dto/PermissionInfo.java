package cn.net.health.user.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PermissionInfo implements Serializable {

    private static final long serialVersionUID = -1886827721229436347L;

    private String title;

    private String value;

    private boolean checked;

    private boolean disabled;

    private List<PermissionInfo> data = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<PermissionInfo> getData() {
        return data;
    }

    public void setData(List<PermissionInfo> data) {
        this.data = data;
    }

}
