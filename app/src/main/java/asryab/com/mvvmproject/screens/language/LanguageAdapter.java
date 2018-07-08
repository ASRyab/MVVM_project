package asryab.com.mvvmproject.screens.language;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.data.DataStorage;
import asryab.com.mvvmproject.databinding.RowLanguageBinding;
import asryab.com.mvvmproject.models.user.Language;
import asryab.com.mvvmproject.viewmodels.language.LanguageVM;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageHolder> {

    private List<Language> mLanguages;
    private LanguageVM mAppLanguageModel;
    private Language mAppLanguage;
    private Context mContext;


    public LanguageAdapter(Context context, List<Language> languages) {
        mContext = context;
        mLanguages = languages;
        mAppLanguage = DataStorage.getAppLanguage();
    }

    @Override
    public LanguageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowLanguageBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_language, parent, false);
        return new LanguageHolder(binding);
    }

    @Override
    public void onBindViewHolder(LanguageHolder holder, int position) {
        holder.setLanguage(mLanguages.get(position));
    }

    @Override
    public int getItemCount() {
        return mLanguages.size();
    }

    private void setSelected(LanguageVM nSelected) {
        if(mAppLanguageModel != null) {
            mAppLanguageModel.isCheckVisible.set(false);
        }
        mAppLanguageModel = nSelected;
        mAppLanguageModel.isCheckVisible.set(true);
    }

    public Language getSelectedLanguage() {
        return mAppLanguage;
    }


    public class LanguageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Language mLanguage;
        RowLanguageBinding mLanguageBinding;
        public LanguageVM mViewModel;

        public LanguageHolder(RowLanguageBinding vb) {
            super(vb.getRoot());
            mLanguageBinding = vb;
            mLanguageBinding.getRoot().setOnClickListener(this);
        }

        public void setLanguage(Language language) {
            mLanguage = language;
            mViewModel = new LanguageVM(mLanguage.getName());
            mLanguageBinding.setViewModel(mViewModel);
            if(mLanguage == mAppLanguage) {
                mViewModel.isCheckVisible.set(true);
                setSelected(mViewModel);
            }
        }

        @Override
        public void onClick(View v) {
            setSelected(mViewModel);
            mAppLanguage = mLanguage;
        }
    }
}
