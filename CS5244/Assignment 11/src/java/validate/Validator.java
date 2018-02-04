/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;

/**
 * Validates form data
 * 
 * @author jfoley
 */
public class Validator {

    // ensures that quantity input is number between 0 and 99
    // applies to quantity fields in cart page
    public boolean validateQuantity (String productId, String quantity) {

        boolean errorFlag = false;

        if (!productId.isEmpty() && !quantity.isEmpty()) {

            int i = -1;

            try {

                i = Integer.parseInt(quantity);
            } catch (NumberFormatException nfe) {

                System.out.println("User did not enter a number in the quantity field");
            }

            if (i < 0 || i > 99) {

                errorFlag = true;
            }
        }

        return errorFlag;
    }


    /**
     * Performs simple validation on the form on the checkout page
     * @param name parameter value
     * @param email parameter value
     * @param phone parameter value
     * @param address parameter value
     * @param ccNumber parameter value
     * @param expirationMonth parameter value
     * @param expirationYear parameter value
     * @param request the request object
     * @return true if a validation error occurs
     */
    public boolean validateForm(String name,
                                String email,
                                String phone,
                                String address,
                                String ccNumber,
                                String expirationMonth,
                                String expirationYear,
                                HttpServletRequest request) {

        boolean errorFlag = false;
        boolean nameError;
        boolean emailError;
        boolean phoneError;
        boolean addressError;
        boolean ccNumberError;
        boolean expirationMonthError;
        boolean expirationYearError;

        if (name == null
                || name.equals("")
                || name.length() > 45) {
            errorFlag = true;
            nameError = true;
            request.setAttribute("nameError", nameError);
        }
        if (email != null && !email.contains("@")) {
            errorFlag = true;
            emailError = true;
            request.setAttribute("emailError", emailError);
        }
        if (phone == null
                || phone.equals("")
                || phone.length() < 9) {
            errorFlag = true;
            phoneError = true;
            request.setAttribute("phoneError", phoneError);
        }
        if (address == null
                || address.equals("")
                || address.length() > 45) {
            errorFlag = true;
            addressError = true;
            request.setAttribute("addressError", addressError);
        }
        if (expirationMonth == null
                || expirationMonth.equals("")
                || isStringParseableAsInteger(expirationMonth) && (Integer.parseInt(expirationMonth) > 12 || Integer.parseInt(expirationMonth) < 1)) {
            errorFlag = true;
            expirationMonthError = true;
            request.setAttribute("expirationMonthError", expirationMonthError);
        }
        if (expirationYear == null
                || expirationYear.equals("")
                || isStringParseableAsInteger(expirationYear) && Integer.parseInt(expirationYear) < new GregorianCalendar().getTime().getYear()) {
            errorFlag = true;
            expirationYearError = true;
            request.setAttribute("expirationYearError", expirationYearError);
        }
        if (ccNumber == null
                || ccNumber.equals("")
                || ccNumber.length() > 19) {
            errorFlag = true;
            ccNumberError = true;
            request.setAttribute("ccNumberError", ccNumberError);
        }

        return errorFlag;
    }
    
    /**
     * Helper method to check if a string can be parsed as an integer by checking
     * for a NumberFormatException.
     * 
     * @param s the string to parse
     * @return true if the parse is successful, false if a NumberFormatException is thrown
     */
    private boolean isStringParseableAsInteger(String s){
        boolean parseable = false;
        try{
            Integer.parseInt(s);
            parseable = true;
        } catch (NumberFormatException e){
            parseable = false;
        }
        return parseable;
    }
}
