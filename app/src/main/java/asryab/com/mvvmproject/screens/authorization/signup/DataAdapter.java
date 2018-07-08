package asryab.com.mvvmproject.screens.authorization.signup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.Country;
import asryab.com.mvvmproject.models.Pincode;

public class DataAdapter<T> extends ArrayAdapter {

    private List<T> mItems;
    private Context mContext;
    private AuthoCompleteWatcher.Type mType;

    public DataAdapter(Context context, int resource, List<T> list, AuthoCompleteWatcher.Type type) {
        super(context, resource, list);
        mContext    = context;
        mItems      = list;
        mType       = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView  == null){
            LayoutInflater inflater = ((SignUpActivity) mContext).getLayoutInflater();
            if(mType == AuthoCompleteWatcher.Type.GENDER) {
                convertView = inflater.inflate(R.layout.row_spinner, parent, false);
            } else {
                convertView = inflater.inflate(R.layout.row_dropdown, parent, false);
            }
        }
        TextView textViewItem;
        if(mType == AuthoCompleteWatcher.Type.GENDER) {
            textViewItem = (TextView) convertView.findViewById(R.id.tvSpinner);
        } else {
            textViewItem = (TextView) convertView.findViewById(R.id.tvDropdown);
        }
            switch (mType) {
                case COUNTRY:
                    Country country = (Country) mItems.get(position);
                    textViewItem.setText(country.name);
                    break;
                case PINCODE:
                    Pincode pincode = (Pincode) mItems.get(position);
                    textViewItem.setText(pincode.code + " - " + pincode.area);
                    break;
                case GENDER:
                    String gender = (String) mItems.get(position);
                    textViewItem.setText(gender);
                    break;
            }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView  == null){
            LayoutInflater inflater = ((SignUpActivity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.row_dropdown, parent, false);
        }

        TextView textViewItem = (TextView) convertView.findViewById(R.id.tvDropdown);
        switch (mType) {
            case COUNTRY:
                Country country = (Country) mItems.get(position);
                textViewItem.setText(country.name);
                break;
            case PINCODE:
                Pincode pincode = (Pincode) mItems.get(position);
                textViewItem.setText(pincode.code);
                break;
            case GENDER:
                String gender = (String) mItems.get(position);
                textViewItem.setText(gender);
                break;
        }

        return convertView;
    }
}
