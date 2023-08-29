package org.xzx.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface RobotListenerHandler {

    /**
     * 手动配置当前监听器内Handler方法的优先级，默认情况下为0，按照扫描顺序排列
     * @return 优先级
     */
    int order() default 0;

    /**
     * 对于一些特殊类型，可以配置只监听指定的Id发生的事件
     * @return 监听Id列表
     */
    long[] contactId() default {};

    /**
     * 是否并发执行，无需等待其他Handler完成，当事件到来时直接并发执行
     * @return 是否并发执行
     */
    boolean concurrency() default false;

}
