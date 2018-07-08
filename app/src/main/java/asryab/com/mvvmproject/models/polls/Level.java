package asryab.com.mvvmproject.models.polls;

public enum Level {
    CITY        (3),
    STATE       (2),
    NATIONWIDE  (1),
    UNKNOWN     (-1);

    final int codeLevel;

    Level(int codeLevel) {
        this.codeLevel = codeLevel;
    }

    public static Level valueOf(final int codeLevel) {
        switch (codeLevel) {
            case 3:
                return CITY;
            case 2:
                return STATE;
            case 1:
                return NATIONWIDE;
        }
        return UNKNOWN;
    }

}
