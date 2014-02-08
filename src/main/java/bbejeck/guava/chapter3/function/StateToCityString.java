package bbejeck.guava.chapter3.function;


import bbejeck.guava.common.model.State;
import com.google.common.base.Function;
import com.google.common.base.Joiner;

/**
 * User: Bill Bejeck
 * Date: 4/2/13
 * Time: 10:06 PM
 */
public class StateToCityString implements Function<State,String> {

    @Override
    public String apply(State input) {
        return Joiner.on(",").join(input.getMainCities());
    }
}
