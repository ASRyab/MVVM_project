package asryab.com.mvvmproject.models.polls;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

import asryab.com.mvvmproject.models.Model;

public final class Poll implements Model, Serializable {

    public int id;
    public int category_id;
    public int votes_count;
    public boolean voted;

    private int level;
    private int status;

    public int category_count;
    public String category_name;
    public String url;
    public PollAnswer[] answers;
    public ArrayList<VoteComment> comments;

    public String location;
    public String title;
    public String description;
    public String[] more_info;
    public String type;

    public String publish_at;
    public String close_at;
    public String created_at;
    public String updated_at;

    public Level getLevel() {
        return Level.valueOf(level);
    }

    public Status getStatus() {
        return Status.valueOf(status);
    }

    public PollCategory getCategory() {
        return PollCategory.valueFrom(category_name);
    }

    // Variables for dev's
    public boolean isShowMore       = false;
    public int statisticViewNowPos  = 0; // 0 - empty, 1 - map, 2 - diagram

    public boolean isClosedPoll() {
        return !TextUtils.isEmpty(type) && type.equals("closed");
    }
}
