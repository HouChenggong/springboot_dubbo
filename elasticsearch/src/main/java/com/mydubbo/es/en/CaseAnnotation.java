package com.mydubbo.es.en;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/***
 * 用于“方法操作逻辑实现类”之上
 */
public @interface CaseAnnotation {
    CaseEnum value();
}
