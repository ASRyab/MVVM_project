package asryab.com.mvvmproject.viewmodels.authorization.signup;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.ErrorDialog;
import asryab.com.mvvmproject.api.rx_tasks.auth.SignUp;
import asryab.com.mvvmproject.binding.BindableString;
import asryab.com.mvvmproject.data.DataStorage;
import asryab.com.mvvmproject.dialogs.GenderDialog;
import asryab.com.mvvmproject.dialogs.ProgressDialog;
import asryab.com.mvvmproject.models.Country;
import asryab.com.mvvmproject.models.ErrorResponse;
import asryab.com.mvvmproject.models.Pincode;
import asryab.com.mvvmproject.models.user.Gender;
import asryab.com.mvvmproject.models.user.User;
import asryab.com.mvvmproject.screens.authorization.login.LoginActivity;
import asryab.com.mvvmproject.screens.authorization.signup.AuthoCompleteWatcher;
import asryab.com.mvvmproject.screens.authorization.signup.CountryAdapter;
import asryab.com.mvvmproject.screens.authorization.signup.DataAdapter;
import asryab.com.mvvmproject.screens.authorization.signup.EdittextBackgroundListener;
import asryab.com.mvvmproject.screens.authorization.signup.SignUpActivity;
import asryab.com.mvvmproject.screens.home.HomeTabsActivity;
import asryab.com.mvvmproject.utils.KeyboardUtils;
import asryab.com.mvvmproject.utils.ValidationUtils;
import asryab.com.mvvmproject.viewmodels.ToolbarVM;
import asryab.com.mvvmproject.viewmodels.authorization.SocialVM;
import rx.Subscription;

public class SignUpVM extends ToolbarVM implements AuthoCompleteWatcher.WatcherListener, GenderDialog.GenderListener {

    private SignUpActivity mActivity;
    public SocialVM socialVM;

    public EdittextBackgroundListener mBackgroundListener;
    public TextWatcher mCountryWatcher;
    public AuthoCompleteWatcher mPinWatcher;
    public DataAdapter mSpinnerAdapter;
    public CountryAdapter mCountryAdapter;
    public AdapterView.OnItemSelectedListener mSpinnerClickListener;
    public AdapterView.OnItemClickListener mAutocompleteCountryListener;
    public AdapterView.OnItemClickListener mAutocompletePinListener;

    public final BindableString mName        = new BindableString();
    public final BindableString mEmail       = new BindableString();
    public final BindableString mPassword    = new BindableString();
    public final BindableString mConfirmPassword = new BindableString();
    public final BindableString mCountry     = new BindableString();
    public final BindableString mPhoneCode   = new BindableString();
    public final BindableString mPhone       = new BindableString();
    public final BindableString mPinCode     = new BindableString();
    public final BindableString mArea        = new BindableString();
    public final BindableString mYear        = new BindableString();
    public final BindableString mGender      = new BindableString();

    public final ObservableBoolean isCountryProgressVisible = new ObservableBoolean(false);
    public final ObservableBoolean isPincodeProgressVisible = new ObservableBoolean(false);

    public final ProgressDialog progressDialog;
    private GenderDialog dialog;

    private Subscription mSearchSub;
    private Subscription subClickSignUp;

    public SignUpVM(SignUpActivity activity) {
        mActivity = activity;
        mTitle.set(activity.getString(R.string.sign_up));
        initSocialButton();
        progressDialog          = new ProgressDialog(mActivity);
        dialog                  = new GenderDialog(mActivity, this);
        mBackgroundListener     = new EdittextBackgroundListener();
        mPinWatcher             = new AuthoCompleteWatcher(mActivity, this, AuthoCompleteWatcher.Type.PINCODE);
        mSpinnerAdapter         = new DataAdapter(mActivity, R.layout.row_dropdown, Gender.getGenders(), AuthoCompleteWatcher.Type.GENDER);
        mCountryAdapter         = new CountryAdapter(mActivity, R.layout.row_dropdown, getCountryList());
        initSpinnerListener();
        initAutocompleteListeners();
        initCountryWatcher();
    }

    public void initSpinnerListener() {
        mSpinnerClickListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> genders = Gender.getAllNames();
                mGender.set(genders.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void initAutocompleteListeners() {
        mAutocompleteCountryListener = (parent, view, position, id) ->
        {
            Country country = (Country) parent.getItemAtPosition(position);
            mPhoneCode.set(country.phoneCode);
        };
        mAutocompletePinListener = (parent, view, position, id) ->
        {
            Pincode pincode = (Pincode) parent.getItemAtPosition(position);
            mArea.set(pincode.area);
        };
    }


    public void onClickEye(View v) {
        switch (v.getId()) {
            case R.id.showPassword_SUA:
                showHidePassword(mActivity.mBinding.passwordSUA);
                break;
            case R.id.showConfirm_SUA:
                showHidePassword(mActivity.mBinding.confirmPasswordSUA);
                break;
        }
    }

    private void showHidePassword(EditText editText) {
        if (editText != null) {
            if ((editText.getInputType() & InputType.TYPE_TEXT_VARIATION_PASSWORD) > 0) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            } else {
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD
                        | InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            }
            int position = editText.getText().length();
            editText.setSelection(position);
        }
    }

    private void initSocialButton() {
    }

    private List<Country> getCountryList() {
        InputStream stream = null;
        Writer writer = new StringWriter();
        ArrayList<Country> countyes = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                line = reader.readLine();
            }
            String json = writer.toString();
            countyes.addAll(new Gson().fromJson(json, new TypeToken<ArrayList<Country>>() {}.getType()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return countyes;
    }

    @Override
    public void onBackArrowPressed(View view) {
        mActivity.onBackPressed();
    }

    public void showGeenderDialog(View v) {
        dialog.show();
    }

    public void clickButton (@NonNull View v) {
        switch (v.getId()) {
            case R.id.btnSignUp_SUA:
                if(isAllFieldsValid()) {
                    signUp();
                }
                break;
            case R.id.btnLogIn_SUA:
                LoginActivity.startIt(mActivity);
                mActivity.finish();
                break;
        }
    }

    private void initCountryWatcher() {
        mCountryWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    mPhoneCode.set("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private boolean isCountryListContainsCountry(String countryName) {
        List<Country> countries = getCountryList();
        boolean isContains = false;
        for (Country country : countries) {
            if (country.name.toLowerCase().equals(countryName.toString().toLowerCase())) {
                isContains = true;
            }
        }
        return isContains;
    }

    private boolean isAllFieldsValid() {
        if(!isAllFieldsFull()) {
            ErrorDialog.show(mActivity, mActivity.getString(R.string.all_fields_required_error));
            return false;
        }
        if(!ValidationUtils.isValidName(mName.get())) {
            mBackgroundListener.setErrorBackground(FieldType.NAME);
            ErrorDialog.show(mActivity, mActivity.getString(R.string.registration_error));
            return false;
        }
        if(!ValidationUtils.isValidEmail(mEmail.get())) {
            mBackgroundListener.setErrorBackground(FieldType.EMAIL);
            ErrorDialog.show(mActivity, mActivity.getString(R.string.registration_error));
            return false;
        }
        if(!ValidationUtils.isValidPassword(mPassword.get())) {
            mBackgroundListener.setErrorBackground(FieldType.PASSWORD);
            ErrorDialog.show(mActivity, mActivity.getString(R.string.registration_error));
            return false;
        }
        if(!ValidationUtils.isValidConfirmPassword(mPassword.get(), mConfirmPassword.get())) {
            mBackgroundListener.setErrorBackground(FieldType.CONFIRM_PASSWORD);
            ErrorDialog.show(mActivity, mActivity.getString(R.string.registration_error));
            return false;
        }
        if(!isCountryListContainsCountry(mCountry.get())) {
            mBackgroundListener.setErrorBackground(FieldType.COUNTRY);
            ErrorDialog.show(mActivity, mActivity.getString(R.string.country_error));
            return false;
        }
        if(TextUtils.isEmpty(mPhoneCode.get())) {
            mBackgroundListener.setErrorBackground(FieldType.PHONE_COD);
            ErrorDialog.show(mActivity, mActivity.getString(R.string.registration_error));
            return false;
        }
        if(TextUtils.isEmpty(mPhone.get())) {
            mBackgroundListener.setErrorBackground(FieldType.PHONE);
            ErrorDialog.show(mActivity, mActivity.getString(R.string.registration_error));
            return false;
        }
        if(TextUtils.isEmpty(mPinCode.get())) {
            mBackgroundListener.setErrorBackground(FieldType.PINCODE);
            mBackgroundListener.setErrorBackground(FieldType.AREA);
            ErrorDialog.show(mActivity, mActivity.getString(R.string.registration_error));
            return false;
        }
        if(!ValidationUtils.isValidYear(mYear.get(), mActivity)) {
            mBackgroundListener.setErrorBackground(FieldType.YEAR);
            ErrorDialog.show(mActivity, mActivity.getString(R.string.registration_error));
            return false;
        }
        if(mGender.get().equals(mActivity.getResources().getString(R.string.gender))) {
            mBackgroundListener.setErrorBackground(FieldType.GENDER);
            ErrorDialog.show(mActivity, mActivity.getString(R.string.registration_error));
            return false;
        }
        return true;
    }

    private boolean isAllFieldsFull() {
        boolean isFull = true;
        if(TextUtils.isEmpty(mName.get())) {
            mBackgroundListener.setErrorBackground(FieldType.NAME);
            isFull = false;
        }
        if(TextUtils.isEmpty(mEmail.get())) {
            mBackgroundListener.setErrorBackground(FieldType.EMAIL);
            isFull = false;
        }
        if(TextUtils.isEmpty(mPassword.get())) {
            mBackgroundListener.setErrorBackground(FieldType.PASSWORD);
            isFull = false;
        }
        if(TextUtils.isEmpty(mConfirmPassword.get())) {
            mBackgroundListener.setErrorBackground(FieldType.CONFIRM_PASSWORD);
            isFull = false;
        }
        if(TextUtils.isEmpty(mCountry.get())) {
            mBackgroundListener.setErrorBackground(FieldType.COUNTRY);
            isFull = false;
        }
        if(TextUtils.isEmpty(mPhoneCode.get())) {
            mBackgroundListener.setErrorBackground(FieldType.PHONE_COD);
            isFull = false;
        }
        if(!ValidationUtils.isValidPhone(mPhone.get())) {
            mBackgroundListener.setErrorBackground(FieldType.PHONE);
            isFull = false;
        }
        if(TextUtils.isEmpty(mPinCode.get())) {
            mBackgroundListener.setErrorBackground(FieldType.PINCODE);
            mBackgroundListener.setErrorBackground(FieldType.AREA);
            isFull = false;
        }
        if(TextUtils.isEmpty(mYear.get())) {
            mBackgroundListener.setErrorBackground(FieldType.YEAR);
            isFull = false;
        }
        if(TextUtils.isEmpty(mGender.get())) {
            mBackgroundListener.setErrorBackground(FieldType.GENDER);
            isFull = false;
        }
        return isFull;
    }

    private void signUp() {
        KeyboardUtils.hideSoftKeyboard(mActivity);
        progressDialog.show();
        //todo need refactor
        subClickSignUp = new SignUp(mEmail.get(), mPassword.get(), mName.get(), getPinCode(mPinCode.get()),
                mPhoneCode.get() + mPhone.get(), mYear.get(), String.valueOf(Gender.idOf(mGender.get()))).getTask(mActivity)
                .subscribe(this::successRegistration
                        , throwable -> {
//                            throwable -> mCompositeSubscription.add(ApiTask.errorResponseObservable(context, throwable,true))
                            ErrorResponse throwable1 = (ErrorResponse) throwable;
                            ErrorDialog.show(mActivity, throwable1.getMessage());
                            progressDialog.dismiss();
                        }
                        , progressDialog::dismiss
                );
    }

    private String getPinCode(String s) {
        return s.substring(0, 6);
    }

    private void successRegistration(User user) {
        DataStorage.saveUser(user);
        HomeTabsActivity.startWith(mActivity, null, null, true);
    }

    @Override
    public void clearTextField() {
        mArea.set("");
    }

    @Override
    public void setListAdapter(DataAdapter adapter, AuthoCompleteWatcher.Type type) {
        switch (type) {
            case COUNTRY:
                mActivity.mBinding.countrySUA.setAdapter(adapter);
                break;
            case PINCODE:
                mActivity.mBinding.pincodeSUA.setAdapter(adapter);
                break;
        }
    }

    @Override
    public void setProgressVisibility(boolean visibility, AuthoCompleteWatcher.Type type) {
        switch (type) {
            case COUNTRY:
                isCountryProgressVisible.set(visibility);
                break;
            case PINCODE:
                isPincodeProgressVisible.set(visibility);
                break;
        }
    }

    @Override
    public void selelctGender(Gender gender) {
        if(gender != null) {
            mGender.set(gender.getName());
        }
    }

    public enum FieldType {
        NAME, EMAIL, PASSWORD, CONFIRM_PASSWORD, COUNTRY, PHONE_COD, PHONE, PINCODE, AREA, YEAR, GENDER
    }
}
