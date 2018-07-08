package asryab.com.mvvmproject.api.requests;

import java.util.Map;

import asryab.com.mvvmproject.api.ApiConst;
import asryab.com.mvvmproject.api.rx_tasks.ApiTask;
import asryab.com.mvvmproject.models.CheckAuthResponse;
import asryab.com.mvvmproject.models.GetListResponse;
import asryab.com.mvvmproject.models.Pincode;
import asryab.com.mvvmproject.models.SuccessResponse;
import asryab.com.mvvmproject.models.user.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiAuth {
    @POST(ApiConst.LOGIN)
    Observable<User> userLogin(@Body final Map<String, String> authorizationPartMap);

    @POST(ApiConst.SIGN_UP)
    Observable<User> userSignup(@Body final Map<String, String> authorizationPartMap);

    @GET(ApiConst.GET_PINCODES)
    Observable<GetListResponse<Pincode>> getPincodes(@Query(ApiTask.SEARCH) final String query);

    @GET(ApiConst.CHECK_AUTH)
    Observable<CheckAuthResponse> checkAuth();

    @POST(ApiConst.SIGN_OUT)
    Observable<SuccessResponse> userSignOut();
}
