package asryab.com.mvvmproject.api.rx_tasks.petition;

import android.content.Context;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.petitions.Petition;
import rx.Observable;

public class SignPetition extends ApiTask<Petition> {

    private final int idPetition;

    public SignPetition(int idPetition) {
        super();
        this.idPetition = idPetition;
    }

    @Override
    protected Observable<Petition> getObservableTask(Context _context) {
        return Api.getInst().feed().signPetition(idPetition);
    }
}
