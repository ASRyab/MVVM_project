package asryab.com.mvvmproject.models.polls;

import android.content.Context;

import java.io.Serializable;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.Model;

public class PollAnswer implements Model, Serializable {

    public int id;
    public int poll_id;
    public int attachment_id;
    public int percents;
    public int value;
    public String text;
    public String created_at;
    public String updated_at;
    public String url;
    public boolean voted;

    public String getLabelText(final Context context, final int countAnswers) {
        if (countAnswers == 3) {
            switch (value) {
                case 0:
                    return context.getString(R.string.no);
                case 1:
                    return context.getString(R.string.not_sure);
                case 2:
                    return context.getString(R.string.yes);
            }
        }
        return text;
    }
}
