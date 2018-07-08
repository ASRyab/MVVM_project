package asryab.com.mvvmproject.screens.authorization.forgotpass;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import asryab.com.mvvmproject.databinding.FragmentCheckEmailBinding;
import asryab.com.mvvmproject.databinding.FragmentForgotPasswordBinding;
import asryab.com.mvvmproject.utils.Arguments;

public class FragmentUtils {

    public static ViewDataBinding getForgotPasswordScreen(final String action,
                                                          final LayoutInflater inflater,
                                                          final ViewGroup container) {
        switch (action) {
            case Arguments.Activity.FORGOT_TYPE:
                return FragmentForgotPasswordBinding.inflate(inflater, container, false);
            case Arguments.Activity.CHECK_TYPE:
                return FragmentCheckEmailBinding.inflate(inflater, container, false);
            default:
                throw new IllegalArgumentException("Fragment type is not defined");
        }
    }
}
