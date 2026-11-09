import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    // 属性：没有默认值，使用时必须指定
    String value();

    // 属性：有默认值，可选
    int level() default 1;
}