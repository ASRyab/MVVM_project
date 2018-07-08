package asryab.com.mvvmproject.models.polls;

import asryab.com.mvvmproject.models.Model;

public final class PollVote implements Model {

    public int poll_answer_id;
    public Boolean is_public;
    public String comment;

    public PollVote() {
    }

    public PollVote(int poll_answer_id) {
        this.poll_answer_id = poll_answer_id;
    }

    public PollVote(int poll_answer_id, Boolean is_public, String comment) {
        this.poll_answer_id = poll_answer_id;
        this.is_public = is_public;
        this.comment = comment;
    }
}
