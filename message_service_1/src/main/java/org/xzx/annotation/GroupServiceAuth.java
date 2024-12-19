package org.xzx.annotation;


import org.xzx.bean.enums.GroupServiceEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GroupServiceAuth {
    GroupServiceEnum service();
}
