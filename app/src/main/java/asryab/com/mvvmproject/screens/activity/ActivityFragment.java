package asryab.com.mvvmproject.screens.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.abstracts.BaseFragment;
import asryab.com.mvvmproject.api.rx_tasks.auth.SignOut;
import asryab.com.mvvmproject.data.DataStorage;
import asryab.com.mvvmproject.screens.intro.IntroActivity;

public class ActivityFragment extends BaseFragment {
    private static final String LOG_TAG = ActivityFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity,container,false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.main_container);
        Button button = new Button(getContext());
        button.setText("LOGOUT");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG,"onClick");
                new SignOut().getTask(getContext()).subscribe(
                        successResponse -> signOut()
                        ,Throwable::printStackTrace
                );
            }
        });
        linearLayout.addView(button,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }

    private void signOut() {
        DataStorage.setLogin(false);
        Log.d(LOG_TAG,"signOut");
        IntroActivity.startIt(getContext());
    }
}