package Contracts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Simple contract to store commonly used DateFormats
 *
 * @author carterdmorgan
 */

public interface DateFormatContract {
    DateFormat DAY_MONTH_ALPHA_YEAR = new SimpleDateFormat("dd MMM yyyy");
    DateFormat YEAR_MONTH_DAY_NUMERIC_DASH = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat TWELVE_HOUR_AM_PM = new SimpleDateFormat("h:mm aa");
    DateFormat TWENTY_FOUR_HOUR = new SimpleDateFormat("HH:mm");
    DateFormat TWENTY_FOUR_HOUR_SECONDS = new SimpleDateFormat("HH:mm:ss");
    DateFormat MONTH_FULL_DAY_YEAR_ALPHA = new SimpleDateFormat("MMMM d, yyyy");
}
