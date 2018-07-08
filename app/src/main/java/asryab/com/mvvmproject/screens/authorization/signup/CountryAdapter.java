package asryab.com.mvvmproject.screens.authorization.signup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.Country;

public class CountryAdapter extends ArrayAdapter<Country> {

    private List<Country> mCountryes = new ArrayList<>();
    private LayoutInflater inflater;

    public CountryAdapter(Context context, int resource, List<Country> list) {
        super(context, resource, list);
        mCountryes.addAll(list);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            view = inflater.inflate(R.layout.row_dropdown, parent, false);
        }
        Country country = getItem(position);

        TextView countryTv = (TextView) view.findViewById(R.id.tvDropdown);
        countryTv.setText(country.name);

        return view;
    }

    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    Filter countryFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return  ((Country) resultValue).name;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<Country> suggestions = new ArrayList<Country>();
                for (Country country : mCountryes) {
                    if (country.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(country);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                addAll((ArrayList<Country>) results.values);
                notifyDataSetChanged();
            }
        }
    };
}
