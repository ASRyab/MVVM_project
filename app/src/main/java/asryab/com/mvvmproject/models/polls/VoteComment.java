package asryab.com.mvvmproject.models.polls;

import java.io.Serializable;

import asryab.com.mvvmproject.models.Profile;

public final class VoteComment implements Serializable {

    public int      id;
    public int      likes_count;
    public boolean  liked;
    public boolean  is_public;
    public String   comment;
    public Profile profile;
    public String   created_at;
    public String   updated_at;

}
