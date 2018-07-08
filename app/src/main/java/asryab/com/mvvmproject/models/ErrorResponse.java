package asryab.com.mvvmproject.models;

import android.content.Context;

import asryab.com.mvvmproject.R;

public class ErrorResponse extends Throwable implements Model {
    public static final int INTERNET_MISSING = 99;
    private Throwable throwable;
    private int code;
    private String error;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, Throwable throwable) {
        error = message;
        this.throwable = throwable;
    }

    public ErrorResponse(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public int getCode() {
        return code;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static String getErrorByCode(Context context, int code) {
        String message;
        switch (code){
            case INTERNET_MISSING:
                message = context.getString(R.string.internet_error);
                break;
            default:
                message = context.getString(R.string.unknown_error);
        }
        return message;
    }
}
