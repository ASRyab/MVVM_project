package asryab.com.mvvmproject.models.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Language {
    ENGLISH (1, "en", "English"),
    HINDI   (2, "hi", "Hindi"),
    KANNADA (3, "kn", "Kannada"),
    BENGALI (4, "bn", "Bengali"),
    TELUGU  (5, "te", "Telugu"),
    MARATHI (6, "mr", "Marathi");

    Language(int id, String locale, String name) {
        this.id     = id;
        this.locale = locale;
        this.name   = name;
    }

    int id;
    String locale;
    String name;

    public static Language valueOf(int id) {
        for(Language language : values()) {
            if(language.id == id) return language;
        }
        return ENGLISH;
    }

    public static List<Language> getAllLanguages() {
        List<Language> languages = new ArrayList<>(Arrays.asList(values()));
        return languages;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocale() {
        return locale;
    }
}
