package bbejeck.guava.chapter3.function;

import com.google.common.base.Function;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Bill Bejeck
 * Date: 3/30/13
 * Time: 10:14 PM
 */
public class DateFormatFunction implements Function<Date,String> {

    private String dateFormat;

    public DateFormatFunction(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String apply(Date input) {
        return new SimpleDateFormat(dateFormat).format(input);
    }
}
