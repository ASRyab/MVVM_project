package asryab.com.mvvmproject.viewmodels.feed.petitions.updates;

import android.databinding.ObservableField;

import asryab.com.mvvmproject.models.petitions.Update;
import asryab.com.mvvmproject.utils.DateTimeUtils;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public class UpdateItemVM extends ViewModel {

    public Update currentUpdateModel;
    public ObservableField<String> completeText = new ObservableField<>();

    public void updateViewModel(final Update updateModel) {
        this.currentUpdateModel = updateModel;
        completeText.set(String.format("%s  %s", currentUpdateModel.title, DateTimeUtils.formatStringDayMonthAndYear(DateTimeUtils.parseDateStringToDate(currentUpdateModel.created_at))));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
