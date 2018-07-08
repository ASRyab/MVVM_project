package asryab.com.mvvmproject.screens.authorization.signup;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.ArrayList;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.api.rx_tasks.auth.SearchPincodes;
import asryab.com.mvvmproject.models.GetListResponse;

public class AuthoCompleteWatcher implements TextWatcher {

    private Context mContext;
    private ArrayList<Object> mItems;
    private DataAdapter mArrayAdapter;
    private WatcherListener mListener;
    private Type mType;
    private ShowDrop mShowDrop;
    private String mOldPincode = "";

    public AuthoCompleteWatcher(Context context, WatcherListener listener, Type type) {
        mContext        = context;
        mListener       = listener;
        mItems          = new ArrayList<>();
        mType           = type;
        mArrayAdapter   = new DataAdapter(mContext, R.layout.row_dropdown, mItems, mType);
        mListener.setListAdapter(mArrayAdapter, mType);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mArrayAdapter.notifyDataSetChanged();
        if(isReadyToMakeRequest(s)) {
            makeRequest(s);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void makeRequest(CharSequence s) {
        mListener.setProgressVisibility(true, mType);
        switch (mType) {
            case PINCODE:
                makePincodeRequest(s);
                break;
        }
    }


    private void makePincodeRequest(CharSequence s) {
//todo need refactor
        new SearchPincodes(s.toString())
                .getTask(mContext)
                .subscribe(this::updateData, throwable -> {
                    mListener.setProgressVisibility(false, mType);
//                    mCompositeSubscription.add(ApiTask.errorResponseObservable(mContext, throwable,true));
                });
    }

    private void updateData(GetListResponse data) {
        mListener.setProgressVisibility(false, mType);
        mItems.clear();
        for(Object item : data.data) {
            mItems.add(item);
        }

        mArrayAdapter = new DataAdapter(mContext, R.layout.row_dropdown, mItems, mType);
        mListener.setListAdapter(mArrayAdapter, mType);
        mArrayAdapter.notifyDataSetChanged();
        if(mItems.size() > 0) {
            mShowDrop.show();
        }
    }

    private boolean isReadyToMakeRequest(CharSequence s) {
        boolean isReady = false;
        switch (mType) {
            case COUNTRY:
                if(s.length() >= 3) {
                    isReady = true;
                }
                break;
            case PINCODE:
                if(s.length() == 0) {
                    mListener.clearTextField();
                }
                if(s.length() == 6 && !mOldPincode.contains(s)) {
                    mOldPincode = s.toString();
                    isReady = true;
                }
                break;
        }
        return isReady;
    }

    public interface WatcherListener {
        void clearTextField();
        void setListAdapter(DataAdapter adapter, Type type);
        void setProgressVisibility(boolean visibility, Type type);
    }

    public interface ShowDrop{
        void show();
    }

    public void setShowDrop (ShowDrop drop) {
        mShowDrop = drop;
    }

    public enum Type {
        COUNTRY, PINCODE, GENDER
    }
}
