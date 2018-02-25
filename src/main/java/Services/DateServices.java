package Services;

import Contracts.DateFormatContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateServices {

    public static String formatDate(String hour, DateFormat readFormat, DateFormat writeFormat){
        try{
            Date toFormat = readFormat.parse(hour);
            hour = writeFormat.format(toFormat);
            return hour;
        }catch(ParseException e){
            System.out.println("Unable to parse");
            return "Unable to parse";
        }
    }

    public static boolean isToday(String date){
        DateFormat urlFormat = DateFormatContract.YEAR_MONTH_DAY_NUMERIC_DASH;
        Date today = Calendar.getInstance().getTime();
        String todayString = urlFormat.format(today);
        return todayString.equals(date);
    }
}
