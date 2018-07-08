package asryab.com.mvvmproject.api.rx_tasks.petition;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.petitions.Petition;
import rx.Observable;

public class KeepUpdatePetition extends ApiTask<Petition> {

    private final boolean isKeepUpdated;
    private final double petitionId;

    public KeepUpdatePetition(boolean isKeepUpdated, int petitionId) {
        super();
        this.isKeepUpdated  = isKeepUpdated;
        this.petitionId  = petitionId;
    }

    @Override
    protected Observable<Petition> getObservableTask(Context _context) {
        Map<String,Object> mapKeepUpdate = new HashMap<>();
        mapKeepUpdate.put(ApiTask.PETITION_ID, petitionId);
        mapKeepUpdate.put(ApiTask.IS_KEEP_UPDATED, isKeepUpdated);
        return Api.getInst().feed().keepUpdate(mapKeepUpdate);
    }
}
