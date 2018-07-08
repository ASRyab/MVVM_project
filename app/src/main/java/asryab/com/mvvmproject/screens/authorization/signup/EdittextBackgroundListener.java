package asryab.com.mvvmproject.screens.authorization.signup;

import android.view.View;

import java.util.HashMap;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.viewmodels.authorization.signup.SignUpVM;

public class EdittextBackgroundListener {

    private HashMap<SignUpVM.FieldType, View> fieldMap = new HashMap<>();

    public EdittextBackgroundListener () {
    }

    public void setEditField (SignUpVM.FieldType type, View field) {
        fieldMap.put(type, field);
    }

    public void setErrorBackground(SignUpVM.FieldType type) {
        View view = fieldMap.get(type);
        if(view != null) {
            view.setBackground(view.getContext().getResources().getDrawable(R.drawable.edit_error_background));
            view.setTag(R.string.background_key, true);
        }
    }
}
