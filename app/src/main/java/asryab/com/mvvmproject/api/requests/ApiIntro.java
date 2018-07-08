package asryab.com.mvvmproject.api.requests;

import java.util.ArrayList;

import asryab.com.mvvmproject.api.ApiConst;
import asryab.com.mvvmproject.models.intro.IntroPageModel;
import retrofit2.http.GET;
import rx.Observable;

public interface ApiIntro {

    @GET(ApiConst.GET_INTRO_IMAGES)
    Observable<ArrayList<IntroPageModel>> getIntros();

}
