package asryab.com.mvvmproject.models;

import java.util.List;

public class Countryes implements Model {
    public List<Country> mCountries;

    public Countryes(List<Country> list) {
        mCountries = list;
    }
}
