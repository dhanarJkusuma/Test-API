package id.dhanarjkusuma.app.loket.helper;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {EnumValueValidator.class})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateEnum {

    public abstract String message() default "Invalid value. This is not permitted.";

    public abstract Class<?>[] groups() default {};

    public abstract Class<? extends Payload>[] payload() default {};

    public abstract Class<? extends java.lang.Enum<?>> enumClass();

    public abstract boolean ignoreCase() default false;
}
