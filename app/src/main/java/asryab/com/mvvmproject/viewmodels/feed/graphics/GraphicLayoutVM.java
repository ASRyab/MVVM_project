package asryab.com.mvvmproject.viewmodels.feed.graphics;

import asryab.com.mvvmproject.models.polls.PollStatistics;
import asryab.com.mvvmproject.screens.feed.graphics.GraphicType;
import asryab.com.mvvmproject.viewmodels.ViewModel;

public final class GraphicLayoutVM extends ViewModel {

    public final GraphicType typeOfTheGraphic;
    public final PollStatistics pollStatistics;

    public GraphicLayoutVM(final GraphicType typeOfTheGraphic, final PollStatistics pollStatistics) {
        this.typeOfTheGraphic   = typeOfTheGraphic;
        this.pollStatistics     = pollStatistics;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
