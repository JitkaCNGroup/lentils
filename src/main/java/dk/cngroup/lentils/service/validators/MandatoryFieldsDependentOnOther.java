package dk.cngroup.lentils.service.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MandatoryFieldsDependentOnOtherValidator.class})
public @interface MandatoryFieldsDependentOnOther {

    String message() default "Je nutno vybrat obrázek";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String baseField();
    String matchField1();
    String matchField2();
    String matchField3();
}

