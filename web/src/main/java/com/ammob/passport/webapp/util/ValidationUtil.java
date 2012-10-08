package com.ammob.passport.webapp.util;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.FieldChecks;

import com.ammob.passport.util.IdcardUtil;


/**
 * ValidationUtil Helper class for performing custom validations that
 * aren't already included in the core Commons Validator.
 *
 * <p>
 * <a href="ValidationUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class ValidationUtil {
    //~ Methods ================================================================

    /**
     * Validates that two fields match.
     * @param bean
     * @param va
     * @param field
     * @param errors
     */
    public static boolean validateTwoFields(Object bean, ValidatorAction va,
                                            Field field, Errors errors) {
        String value =
            ValidatorUtils.getValueAsString(bean, field.getProperty());
        String sProperty2 = field.getVarValue("secondProperty");
        String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);

        if (!GenericValidator.isBlankOrNull(value)) {
            try {
                if (!value.equals(value2)) {
                    FieldChecks.rejectValue(errors, field, va);
                    return false;
                }
            } catch (Exception e) {
                FieldChecks.rejectValue(errors, field, va);
                return false;
            }
        }

        return true;
    }
    
    /**
     * Validates china id card fields match.
     * @param bean
     * @param va
     * @param field
     * @param errors
     * @return
     */
    public static boolean validateIdCard(Object bean, ValidatorAction va,
    		Field field, Errors errors) {
    	String value = extractValue(bean, field);
        if (!GenericValidator.isBlankOrNull(value)) {
            try {
            	if(!IdcardUtil.isIdcard(value)){
                    FieldChecks.rejectValue(errors, field, va);
                    return false;
                }
            } catch (Exception e) {
                FieldChecks.rejectValue(errors, field, va);
                return false;
            }
        }
        return true;
    }
    
    /**
     * Extracts the value of the given bean. If the bean is <code>null</code>, the returned value is also <code>null</code>.
     * If the bean is a <code>String</code> then the bean itself is returned. In all other cases, the <code>ValidatorUtils</code>
     * class is used to extract the bean value using the <code>Field</code> object supplied.
     *
     * @see ValidatorUtils#getValueAsString(Object, String)
     */
    protected static String extractValue(Object bean, Field field) {
        String value = null;

        if (bean == null) {
            return null;
        } else if (bean instanceof String) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }

        return value;
    }
}
