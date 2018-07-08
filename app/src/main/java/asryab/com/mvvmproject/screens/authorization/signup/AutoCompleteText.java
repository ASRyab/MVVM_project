package asryab.com.mvvmproject.screens.authorization.signup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class AutoCompleteText extends AutoCompleteTextView implements AuthoCompleteWatcher.ShowDrop{

    public AutoCompleteText(Context context) {
        super(context);
    }

    public AutoCompleteText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoCompleteText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        String filterText = "";
        super.performFiltering(filterText, keyCode);
    }

    @Override
    public void show() {
       showDropDown();
    }
}
