package dk.cngroup.lentils.service.validators;

import dk.cngroup.lentils.entity.ImageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class MandatoryFieldsDependentOnOtherValidator implements
        ConstraintValidator<MandatoryFieldsDependentOnOther, Object> {

    private static Logger logger = LoggerFactory.getLogger(MandatoryFieldsDependentOnOtherValidator.class);

    private String baseField;
    private String matchField1;
    private String matchField2;
    private String matchField3;

    @Override
    public void initialize(final MandatoryFieldsDependentOnOther constraint) {
        baseField = constraint.baseField();
        matchField1 = constraint.matchField1();
        matchField2 = constraint.matchField2();
        matchField3 = constraint.matchField3();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext context) {
        try {
            Object baseFieldValue = getFieldValue(object, baseField);
            Object matchFieldValue1 = getFieldValue(object, matchField1);
            Object matchFieldValue2 = getFieldValue(object, matchField2);
            Object matchFieldValue3 = getFieldValue(object, matchField3);
            MultipartFile file = (MultipartFile) matchFieldValue1;
            return baseFieldValue == ImageSource.FILE && !file.isEmpty()
                    || baseFieldValue == ImageSource.FILE && !matchFieldValue3.equals("")
                    || baseFieldValue == ImageSource.WEB && !matchFieldValue2.equals("")
                    || baseFieldValue == ImageSource.NONE;
        } catch (Exception e) {
            logger.info("Image validation problem");
            return false;
        }
    }

    private Object getFieldValue(final Object object, final String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field passwordField = clazz.getDeclaredField(fieldName);
        passwordField.setAccessible(true);
        return passwordField.get(object);
    }
}
