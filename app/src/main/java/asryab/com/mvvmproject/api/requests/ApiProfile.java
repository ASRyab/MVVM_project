package asryab.com.mvvmproject.api.requests;

import asryab.com.mvvmproject.api.ApiConst;
import asryab.com.mvvmproject.models.user.User;
import retrofit2.http.GET;
import rx.Observable;

public interface ApiProfile {
    @GET(ApiConst.PROFILE)
    Observable<User> profile();
}
