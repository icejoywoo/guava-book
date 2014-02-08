package bbejeck.guava.chapter3.predicate;

import bbejeck.guava.common.model.City;
import bbejeck.guava.common.model.Climate;
import com.google.common.base.Predicate;

/**
 * User: Bill Bejeck
 * Date: 4/3/13
 * Time: 10:04 PM
 */
public class TemperateClimatePredicate implements Predicate<City> {

    @Override
    public boolean apply(City input) {
        return input.getClimate().equals(Climate.TEMPERATE);
    }
}
