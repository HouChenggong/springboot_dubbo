package cn.net.health.user.dto;

import java.io.Serializable;
import java.util.List;

public class MenuInfo implements Serializable {

    private static final long serialVersionUID = 9215684297502215772L;

    private String onlyId; // 权限ID

    private String title; //菜单标题

    private String href; //菜单链接

    private boolean spread = false; //是否展开

    private List<MenuInfo> children; //子菜单列表

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean getSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public boolean isSpread() {
        return spread;
    }

    public List<MenuInfo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuInfo> children) {
        this.children = children;
    }

    public String getOnlyId() {
        return onlyId;
    }

    public void setOnlyId(String onlyId) {
        this.onlyId = onlyId;
    }
}
