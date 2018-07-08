package asryab.com.mvvmproject.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.Calendar;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.ErrorDialog;

public class ValidationUtils {

    public static boolean isValidEmail(final String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidName(final String name) {
        return !TextUtils.isEmpty(name) && name.length() > 1;
    }

    public static boolean isValidPassword(final String password) {
//        PasswordValidator validator = new PasswordValidator();
//        return !TextUtils.isEmpty(password) && validator.validate(password);
        return password != null && password.length() >= 6;
    }

    public static boolean isValidConfirmPassword(final String password, final String passwordConfirm) {
        if(password != null && passwordConfirm != null) {
            return password.equals(passwordConfirm);
        }
        return false;
    }

    public static boolean isValidPhone(final String phone) {
        return phone != null && phone.length() == 10;
    }

    public static boolean isValidYear(final String year, final Context context) {
        if(year == null || year.length() != 4) {
            ErrorDialog.show(context, context.getString(R.string.registration_error));
            return false;
        }
        int currentYear     = Calendar.getInstance().get(Calendar.YEAR);
        int yearOfBith  = Integer.parseInt(year);
        if(yearOfBith > currentYear) {
            ErrorDialog.show(context, context.getString(R.string.registration_error));
            return false;
        }
        if((currentYear - yearOfBith) > 150) {
            ErrorDialog.show(context, context.getString(R.string.registration_error));
            return false;
        }
        if((currentYear - yearOfBith) < 18) {
            ErrorDialog.show(context, context.getString(R.string.registration_error));
            return false;
        }
        return true;
    }
}
