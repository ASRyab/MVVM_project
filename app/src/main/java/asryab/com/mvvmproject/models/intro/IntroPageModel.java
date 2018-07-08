package asryab.com.mvvmproject.models.intro;

import java.io.Serializable;

import asryab.com.mvvmproject.models.Model;

public class IntroPageModel implements Model, Serializable {

    public int      id;
    public String   title;
    public String   subTitle;
    public String   url;
    public String   updated_at;

    public IntroPageModel(String title, String subTitle, String url) {
        this.title          = title;
        this.subTitle       = subTitle;
        this.url = url;
    }
}
