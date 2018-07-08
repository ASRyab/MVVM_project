package asryab.com.mvvmproject.models;

import java.util.ArrayList;

public final class GetListResponse<T> implements Model {

    public int total;
    public ArrayList<T> data;

}
