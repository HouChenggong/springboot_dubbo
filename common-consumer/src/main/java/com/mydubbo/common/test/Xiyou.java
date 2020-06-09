package com.mydubbo.common.test;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xiyou
 */
@Getter
@Setter
public class Xiyou implements Serializable {


    private static final long serialVersionUID = 3979130160814338930L;

    private String name ;
    Object object;


    private String blogName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Xiyou xiyou = (Xiyou) o;
        return Objects.equal(name, xiyou.name) &&
                Objects.equal(blogName, xiyou.blogName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, blogName);
    }
}
