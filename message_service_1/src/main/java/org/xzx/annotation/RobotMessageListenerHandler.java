package org.xzx.annotation;

import java.lang.annotation.*;

//TODO: 拆分多个注解，优化执行时间
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RobotMessageListenerHandler {

    /**
     * 手动配置当前监听器内Handler方法的优先级，默认情况下为0，按照扫描顺序排列
     * @return 优先级
     */
    int order() default 0;

    /**
     * 是否并发执行，无需等待其他Handler完成，当事件到来时直接并发执行
     * @return 是否并发执行
     */
    boolean concurrency() default false;

    /**
     * 判断是否以某个字符串开头
     * @return 事件开始的字符串
     */
    String startString() default "";

    /**
     * 判断是否以某个字符串结尾
     * @return 事件结束的字符串
     */
    String endString() default "";

    /**
     * 判断是否匹配某个正则表达式
     * @return 正则表达式
     */
    String regex() default "";

    /**
     * 判断是否继续
     * @return 是否继续
     */
    boolean shutdown() default false;
}
