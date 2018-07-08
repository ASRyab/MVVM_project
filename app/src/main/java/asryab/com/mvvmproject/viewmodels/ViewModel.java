package asryab.com.mvvmproject.viewmodels;

import rx.subscriptions.CompositeSubscription;

public abstract class ViewModel {
    protected CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    public void onDestroy() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
