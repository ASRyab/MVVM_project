package asryab.com.mvvmproject.models.polls;

import asryab.com.mvvmproject.R;

public enum PollCategory {
    DAY,
    WEEK,
    MONTH,
    SPORT,
    BOLLYWOOD,
    FUN,
    MARKET_SURVEY,
    OTHER,
    PROMOTED,
    SPONSORED;

    public static PollCategory valueFrom(final String indent) {
        switch (indent) {
            case "Daily poll":
                return DAY;
            case "Weekly poll":
                return WEEK;
            case "Monthly poll":
                return MONTH;
            case "Sports poll":
                return SPORT;
            case "Bollywood poll":
                return BOLLYWOOD;
            case "Fun poll":
                return FUN;
            case "Market survey":
                return MARKET_SURVEY;
            case "Other poll":
                return OTHER;
            case "Promoted poll":
                return PROMOTED;
            case "Sponsored poll":
                return SPONSORED;
        }

        return null;
    }

    public int getStringResByCategory() {
        switch (this) {
            case DAY:
                return R.string.poll_day;
            case WEEK:
                return R.string.poll_week;
            case MONTH:
                return R.string.poll_month;
            case SPORT:
                return R.string.poll_sport;
            case BOLLYWOOD:
                return R.string.poll_bollywood;
            case FUN:
                return R.string.poll_fun;
            case MARKET_SURVEY:
                return R.string.poll_market_survey;
            case OTHER:
                return R.string.poll_other;
            case PROMOTED:
                return R.string.poll_promoted;
            case SPONSORED:
                return R.string.poll_sponsored;
        }
        return 0;
    }
}
