package com.paradigmcreatives.rajiworld.validations;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class PasswordValidator implements Validator{
	// Actions ------------------------------------------------------------------------------------

    public void validate(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        // Cast the value of the entered password to String.
        String password = (String) value;

        // Obtain the component and submitted value of the confirm password component.
        UIInput confirmComponent = (UIInput) component.getAttributes().get("confirm");
        String confirm = (String) confirmComponent.getSubmittedValue();

        // Check if they both are filled in.
        if (password == null || password.isEmpty() || confirm == null || confirm.isEmpty()) {
            return; // Let required="true" do its job.
        }

        // Compare the password with the confirm password.
        if (!password.equals(confirm)) {
            confirmComponent.setValid(false); // So that it's marked invalid.
            throw new ValidatorException(new FacesMessage("Passwords are not equal."));
        }

        // You can even validate the minimum password length here and throw accordingly.
        // Or, if you're smart, calculate the password strength and throw accordingly ;)

}
}
