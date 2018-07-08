package asryab.com.mvvmproject.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseDialog;
import asryab.com.mvvmproject.models.user.Gender;

public class GenderDialog extends BaseDialog {

    private Button          accept;
    private Button          cancel;
    private RadioGroup      radioGroup;

    private Gender selectedGender;
    private GenderListener  genderListener;

    public GenderDialog(Context context, GenderListener listener) {
        super(context);
        genderListener = listener;

        init();
        setButtonClickListener();
        setRadioClickListener();
    }

    private void init() {
        setContentView(R.layout.dialog_gender);
        accept      = (Button) findViewById(R.id.btnAccept_GD);
        cancel      = (Button) findViewById(R.id.btnCancel_GD);
        radioGroup  = (RadioGroup) findViewById(R.id.radioGroup_GD);
    }

    public void setButtonClickListener() {
        accept.setOnClickListener(listener);
        cancel.setOnClickListener(listener);
    }

    public void setRadioClickListener() {
        radioGroup.setOnCheckedChangeListener(radioListener);
    }

    public interface GenderListener {
        void selelctGender(Gender gender);
    }

    private View.OnClickListener listener = v ->  {
        switch (v.getId()) {
            case R.id.btnAccept_GD:
                genderListener.selelctGender(selectedGender);
                this.dismiss();
                break;
            case R.id.btnCancel_GD:
                this.dismiss();
                break;
        }
    };

    private RadioGroup.OnCheckedChangeListener radioListener = (group, checkedId) ->  {
        switch (checkedId) {
            case R.id.rb_1:
                selectedGender = Gender.MALE;
                break;
            case R.id.rb_2:
                selectedGender = Gender.FEMALE;
                break;
            case R.id.rb_3:
                selectedGender = Gender.OTHER;
                break;
        }
    };
}
