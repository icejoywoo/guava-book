package bbejeck.guava.chapter3.predicate;

import bbejeck.guava.common.model.City;
import com.google.common.base.Predicate;

/**
 * User: Bill Bejeck
 * Date: 4/3/13
 * Time: 9:59 PM
 */
public class LowRainfallPredicate implements Predicate<City> {

    @Override
    public boolean apply(City input) {
        return input.getAverageRainfall() < 45.7;
    }
}
