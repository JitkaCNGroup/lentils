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

    private String imgSource;
    private String imgFile;
    private String imgUrl;
    private String filename;

    @Override
    public void initialize(final MandatoryFieldsDependentOnOther constraint) {
        imgSource = constraint.imgSource();
        imgFile = constraint.imgFile();
        imgUrl = constraint.imgUrl();
        filename = constraint.filename();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext context) {
        try {
            Object imgSourceValue = getFieldValue(object, imgSource);
            Object imgFileValue = getFieldValue(object, imgFile);
            Object imgUrlValue = getFieldValue(object, imgUrl);
            Object filenameValue = getFieldValue(object, filename);
            MultipartFile file = (MultipartFile) imgFileValue;
            return imgSourceValue.equals(ImageSource.LOCAL) && !file.isEmpty()
                    || imgSourceValue.equals(ImageSource.LOCAL) && !filenameValue.equals("")
                    || imgSourceValue.equals(ImageSource.REMOTE) && !imgUrlValue.equals("")
                    || imgSourceValue.equals(ImageSource.NONE);
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
