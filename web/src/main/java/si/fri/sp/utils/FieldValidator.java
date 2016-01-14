package si.fri.sp.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * @Author Gasper Andrejc, created on 13/jan/2016
 */

@ApplicationScoped
@Named
public class FieldValidator implements Serializable {
    private static final String DATE_FORMAT                       = "yyyy-MM-dd";

    // Dovoljeni znaki so vse črke slovenske, srbo-hrvaške, angleške in nemške abecede kot velika ali mala črka, »presledek« ter znak ».«
    private static final Pattern NAME_OR_SURNAME_OR_CITY_PATTERN = Pattern.compile("([a-zA-ZŠšĐđČčĆćŽžÄäÖöÜüß](\\s?\\-\\s?|\\.\\s?|\\.|\\s)?)+");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");

    public static void validateText(final FacesContext ctx, final UIComponent component, final Object value) {
        String nameAndSurname = (String) value;
        if (nameAndSurname == null || nameAndSurname.isEmpty()) {
            // required field validator will handle this
            return;
        }

        nameAndSurname = nameAndSurname.trim();

        if (!NAME_OR_SURNAME_OR_CITY_PATTERN.matcher(nameAndSurname).matches()) {
            ((UIInput) component).setValid(false);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Neveljaven vnos", null));
        }
    }

    public static void validateNumberSlo(final FacesContext ctx, final UIComponent component, final Object value) {
        String postNumber = (String) value;
        if (postNumber == null || postNumber.isEmpty()) {
            // required field validator will handle this
            return;
        }

        postNumber = postNumber.trim();

        if (!NUMBER_PATTERN.matcher(postNumber).matches()) {
            ((UIInput) component).setValid(false);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Neveljaven vnos, dovoljene le številke", null));
        }
    }

    public static void validateDate(final FacesContext ctx, final UIComponent component, final Object value) {
        String date = (String) value;
        if (date == null || date.isEmpty()) {
            // required field validator will handle this
            return;
        }

        date = date.trim();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.parse(date);
        }catch (ParseException e){
            ((UIInput) component).setValid(false);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Neveljaven datum: yyyy-MM-dd", null));
        }
    }


    public boolean fieldHasError(final String componentId) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String root=ctx.getViewRoot().getId() + ':' + componentId;
        Iterator<FacesMessage> iter = ctx.getMessages(root);
        while (iter.hasNext()) {
            if (FacesMessage.SEVERITY_ERROR == iter.next().getSeverity()) {
                return true;
            }
        }
        return false;
    }


    public String getFieldErrorMessage(final String componentId) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Iterator<FacesMessage> iter = ctx.getMessages(ctx.getViewRoot().getId() + ':' + componentId);
        while (iter.hasNext()) {
            FacesMessage msg = iter.next();
            if (FacesMessage.SEVERITY_ERROR == msg.getSeverity()) {
                return msg.getSummary();
            }
        }
        return "Error";
    }
}
