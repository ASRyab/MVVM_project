package asryab.com.mvvmproject.models.petitions;

import java.io.Serializable;

public class Update implements Serializable{

    public int id;
    public int petition_id;
    public String title;
    public String description;
    public String created_at;
}
