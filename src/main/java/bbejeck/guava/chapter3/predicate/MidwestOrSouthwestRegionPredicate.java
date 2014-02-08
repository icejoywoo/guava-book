package bbejeck.guava.chapter3.predicate;

import bbejeck.guava.common.model.Region;
import bbejeck.guava.common.model.State;
import com.google.common.base.Predicate;

/**
 * User: Bill Bejeck
 * Date: 4/3/13
 * Time: 11:39 PM
 */
public class MidwestOrSouthwestRegionPredicate implements Predicate<State> {

    @Override
    public boolean apply(State input) {
        return input.getRegion().equals(Region.MIDWEST) ||
               input.getRegion().equals(Region.SOUTHWEST);
    }
}
