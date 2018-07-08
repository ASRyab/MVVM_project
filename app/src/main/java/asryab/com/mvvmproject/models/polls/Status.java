package asryab.com.mvvmproject.models.polls;

public enum Status {
    CLOSED(4),
    PUBLISHED(3),
    SCHEDULED(2),
    CREATED(1),
    DELETED(0),
    UNKNOWN(-1);

    final int codeStatus;

    Status(int codeStatus) {
        this.codeStatus = codeStatus;
    }

    public static Status valueOf(final int codeStatus) {
        switch (codeStatus) {
            case 4:
                return CLOSED;
            case 3:
                return PUBLISHED;
            case 2:
                return SCHEDULED;
            case 1:
                return CREATED;
            case 0:
                return DELETED;
        }
        return UNKNOWN;
    }

    public String toStringValue() {
        return name().toLowerCase();
    }

}
