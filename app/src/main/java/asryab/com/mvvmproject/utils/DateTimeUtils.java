package asryab.com.mvvmproject.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public abstract class DateTimeUtils {

    private static final String DATE_DAY_OF_THE_MONTH_AND_YEAR_FORMAT           = "MMMM d, yyyy";
    private static final String DATE_DAY_OF_THE_MONTH_FORMAT                    = "MMMM d";

    public static final SimpleDateFormat showDayOfTheMonthAndYearPatternFormat  = new SimpleDateFormat(DATE_DAY_OF_THE_MONTH_AND_YEAR_FORMAT, Locale.getDefault());
    public static final SimpleDateFormat showDayOfTheMonthPatternFormat         = new SimpleDateFormat(DATE_DAY_OF_THE_MONTH_FORMAT, Locale.getDefault());
    public static final ISO8601DateFormat datePatternFormat                     = new ISO8601DateFormat();
    static {
        datePatternFormat.setTimeZone(TimeZone.getDefault());
    }

    public static Date parseDateStringToDate(final String dateString) {
        try {
            return datePatternFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long parseDateStringToLong(final String dateString) {
        final Date date = parseDateStringToDate(dateString);
        return date == null ? 0 : date.getTime();
    }

    public static String formatStringDayMonth(final Date date) {
        if (date == null) return "";
        return showDayOfTheMonthPatternFormat.format(date);
    }

    public static String formatStringDayMonthAndYear(final Date date) {
        if (date == null) return "";
        return showDayOfTheMonthAndYearPatternFormat.format(date);
    }

    public static String formatStringLeftTime(final long diffLongTime, final String labelDAYS, final String labelHRS, final String labelDAY, final String labelHR, final String labelMIN) {
        final long longTimeInMinutes    = diffLongTime / (60 * 1000);
        final int days                  = (int) (longTimeInMinutes / (24 * 60));
        final int hours                 = (int) (days == 0 ? longTimeInMinutes / 60 : ((longTimeInMinutes % (24 * 60)) / 60));
        final int min                   = (int) (hours == 0 ? longTimeInMinutes : (longTimeInMinutes % hours));

        final StringBuilder resultTime  = new StringBuilder();

        if (days > 0) {
            resultTime.append(String.format("%s %s", days, days == 1 ? labelDAY : labelDAYS));
            if (hours > 0) {
                resultTime.append(" ");
                resultTime.append(String.format("%s %s", hours, hours == 1 ? labelHR : labelHRS));
            }
        } else if (hours > 0) {
            resultTime.append(String.format("%s %s", hours, hours == 1 ? labelHR : labelHRS));
            if (min > 0) {
                resultTime.append(" ");
                resultTime.append(String.format("%s %s", min, labelMIN));
            }
        } else if (min >= 0) resultTime.append(String.format("%s %s", min, labelMIN));

        return resultTime.toString();
    }

}
