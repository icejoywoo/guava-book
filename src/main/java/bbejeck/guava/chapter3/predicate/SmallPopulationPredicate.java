package bbejeck.guava.chapter3.predicate;

import bbejeck.guava.common.model.City;
import com.google.common.base.Predicate;

/**
 * User: Bill Bejeck
 * Date: 4/2/13
 * Time: 11:39 PM
 */
public class SmallPopulationPredicate implements Predicate<City> {

    @Override
    public boolean apply(City input) {
        return input.getPopulation() < 500000;
    }
}
