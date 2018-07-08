package asryab.com.mvvmproject.models.user;

import java.util.ArrayList;
import java.util.List;

public enum Gender {
    MALE    (0, "Male"),
    FEMALE  (1, "Female"),
    OTHER   (2, "Other");

    Gender(int id, String name) {
        this.id     = id;
        this.name   = name;
    }

    int id;
    String name;

    public static Gender valueOf(int id) {
        for(Gender gender : values()) {
            if(gender.id == id) return gender;
        }
        return MALE;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static List<String> getAllNames() {
        List<String> names = new ArrayList<>();
        for(Gender gender : values()) {
            names.add(gender.getName());
        }
        return names;
    }

    public static List<Gender> getGenders() {
        List<Gender> genders = new ArrayList<>();
        for(Gender gender : values()) {
            genders.add(gender);
        }
        return genders;
    }

    public static int idOf(String name) {
        for(Gender gender : values()) {
            if(gender.name.equals(name)) {
                return gender.id;
            }
        }
        return 0;
    }
}
