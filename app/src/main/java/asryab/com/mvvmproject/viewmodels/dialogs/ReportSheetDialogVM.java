package asryab.com.mvvmproject.viewmodels.dialogs;

import android.view.View;

import asryab.com.mvvmproject.viewmodels.ViewModel;

public class ReportSheetDialogVM extends ViewModel {

    private final IReportNavigateListener reportNavigateListener;

    public ReportSheetDialogVM(IReportNavigateListener reportNavigateListener) {
        this.reportNavigateListener = reportNavigateListener;
    }

    public void report(final View view) {
        reportNavigateListener.report();
    }

    public void cancel(final View view) {
        reportNavigateListener.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface IReportNavigateListener {
        void report();
        void cancel();
    }

}
