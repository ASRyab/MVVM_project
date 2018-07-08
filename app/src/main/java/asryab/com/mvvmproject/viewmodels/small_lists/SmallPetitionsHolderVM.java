package asryab.com.mvvmproject.viewmodels.small_lists;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import java.util.Locale;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.petitions.Petition;
import asryab.com.mvvmproject.models.polls.Level;
import asryab.com.mvvmproject.utils.DateTimeUtils;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public final class SmallPetitionsHolderVM extends ViewModel {

    public final ObservableBoolean isSelfSigned             = new ObservableBoolean();
    public final ObservableField<String> locationTimePosted = new ObservableField<>();
    public final ObservableField<String> title              = new ObservableField<>();
    public final ObservableField<String> supporters         = new ObservableField<>();

    private final Context context;

    public SmallPetitionsHolderVM(final Context context) {
        this.context = context;
    }

    public void updateItem(final Petition petition) {
        isSelfSigned        .set(petition.isSigned());
        locationTimePosted  .set(String.format("%s  \u2022  %s",
                                petition.getLevel().equals(Level.NATIONWIDE) ? context.getString(R.string.country_without_star) : petition.region,
                                DateTimeUtils.formatStringDayMonth(DateTimeUtils.parseDateStringToDate(petition.publishAt))));
        title               .set(petition.title);
        supporters          .set(String.format("%s %s", String.format(Locale.ENGLISH, "%,d", petition.signsCount) + "", context.getString(R.string.supporters)));
    }

}
