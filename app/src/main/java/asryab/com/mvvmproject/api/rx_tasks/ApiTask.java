package asryab.com.mvvmproject.api.rx_tasks;

import android.content.Context;
import android.widget.Toast;

import java.net.UnknownHostException;

import asryab.com.mvvmproject.api.Api;
import asryab.com.mvvmproject.data.DataStorage;
import asryab.com.mvvmproject.models.ErrorResponse;
import asryab.com.mvvmproject.screens.authorization.login.LoginActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public abstract class ApiTask<R> extends ObservableTask<R> {

    private static final String LOG_TAG = ApiTask.class.getSimpleName();

    public ApiTask() {
        super(true);
    }

    public ApiTask(boolean _isBackground) {
        super(_isBackground);
    }

    @Override
    public Observable<R> getErrorHandler(Throwable _throwable) {
        return Observable.create(subscriber -> {
            Observable.just(_throwable)
                    .map(this::transformError)
                    .subscribe(HttpException -> parseError(subscriber,HttpException)
                    ,Throwable::printStackTrace);
                }

        );
    }

    private ErrorResponse transformError(Throwable _throwable) {
        ErrorResponse errorResponse = new ErrorResponse(_throwable.getMessage(), null);
        try {
            if (_throwable instanceof UnknownHostException) {
                errorResponse = new ErrorResponse(ErrorResponse.INTERNET_MISSING);
                errorResponse.setError(_throwable.getMessage());
            } else {
                HttpException httpException = (HttpException) _throwable;
                String errorBody = httpException.response().errorBody().string();
                errorResponse = Api.getInst().getGson().fromJson(errorBody, ErrorResponse.class);
                errorResponse.setThrowable(_throwable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorResponse;
    }

    public static Subscription errorResponseObservable(Context _context, Throwable _throwable, boolean isShow) {
        return Observable.just(_throwable)
                .map(throwable ->
           (ErrorResponse) throwable)
                .subscribe(errorResponse -> {
                    showError(_context, isShow, errorResponse);
                }, Throwable::printStackTrace);
    }

    public static Subscription errorResponseObservable(Context _context, Throwable _throwable
            , Action0 internetError, Action1<ErrorResponse> serverError) {
        return Observable.just(_throwable)
                .map(throwable ->
                        (ErrorResponse) throwable)
                .subscribe(errorResponse -> {
                    reacteOnError(_context, internetError, serverError, errorResponse);
                }, Throwable::printStackTrace);
    }

    private static void reacteOnError(Context _context
            , Action0 internetError, Action1<ErrorResponse> serverError, ErrorResponse errorResponse) {
        if (errorResponse.getCode() == ErrorResponse.INTERNET_MISSING) {
            if (internetError!=null)
                internetError.call();
        }
        //todo develop version
        Toast.makeText(_context, errorResponse.getError(), Toast.LENGTH_SHORT).show();

    }


    //deprecated
    private static void showError(Context _context, boolean isShow, ErrorResponse errorResponse) {
        if (isShow)
            Toast.makeText(_context, errorResponse.getError(), Toast.LENGTH_SHORT).show();
    }

    private void parseError(Subscriber<? super R> subscriber, ErrorResponse errorResponse) {
        if (errorResponse.getCode() == 101) {
            DataStorage.setLogin(false);
            //todo not sure
            subscriber.onCompleted();
            LoginActivity.startItAlone(mContext);
        } else {
            //todo on the development
//            if (errorResponse.getThrowable() == null)
//                errorResponse.setError(ErrorResponse.getErrorByCode(mContext, errorResponse.getCode()));
            subscriber.onError(errorResponse);
        }
    }

    public static final String EMAIL            = "email";
    public static final String PASSWORD         = "password";
    public static final String DEVICE_OS        = "device_os";
    public static final String DEVICE_TOKEN     = "device_token";
    public static final String PUSH_TOKEN       = "push_token";
    public static final String USERNAME         = "username";
    public static final String PIN_CODE         = "pin_code";
    public static final String PHONE            = "mobile_phone";
    public static final String YEAR             = "birth_year";
    public static final String GENDER           = "gender";
    public static final String SEARCH           = "search";
    public static final String PETITION_ID      = "petition_id";
    public static final String IS_KEEP_UPDATED  = "is_keep_updated";
    public static final String VOTE_ID          = "vote_id";

}
