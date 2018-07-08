package asryab.com.mvvmproject.api.rx_tasks;

import android.content.Context;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public abstract class ObservableTask<R> {

    private boolean isBackgroundTask = true;
    protected Context mContext;

    public ObservableTask() {}

    public ObservableTask(final boolean _isBackground) {
        isBackgroundTask = _isBackground;
    }

    protected abstract Observable<R> getObservableTask(final Context _context);

    public Observable<R> getTask(final Context _context) {
        mContext = _context;
        Observable<R> observable;
        if (isBackgroundTask) {
            observable = Observable.defer(() -> getObservableTask(_context));
        } else {
            observable = getObservableTask(_context);
        }
        if (getErrorHandlerFunc() != null) {
            observable = observable.onErrorResumeNext(getErrorHandlerFunc());
        }
        observable = observable.observeOn(getObserveScheduler())
                .subscribeOn(getSubscribeScheduler())
                .unsubscribeOn(getSubscribeScheduler());
        return observable;
    }

    public Observable<R> getErrorHandler(final Throwable _throwable) {
        return Observable.<R>create(subscriber -> {
            subscriber.onNext(null);
            subscriber.onCompleted();
        });
    }

    public Func1 getErrorHandlerFunc() {
        return new Func1<Throwable, Observable<? extends R>>() {
            @Override
            public Observable<? extends R> call(Throwable throwable) {
                throwable.printStackTrace();
                return getErrorHandler(throwable);
            }
        };
    }

    public Scheduler getObserveScheduler() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler getSubscribeScheduler() {
        return isBackgroundTask ? Schedulers.newThread() :
                Schedulers.io();
    }
}

