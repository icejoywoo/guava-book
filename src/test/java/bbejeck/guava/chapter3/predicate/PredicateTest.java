package bbejeck.guava.chapter3.predicate;

import bbejeck.guava.common.model.City;
import bbejeck.guava.common.model.Climate;
import bbejeck.guava.common.model.Region;
import bbejeck.guava.common.model.State;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 4/4/13
 * Time: 9:27 PM
 */
public class PredicateTest {

    @Test
    public void testPredicateCompose(){
        City city = new City("Austin,TX","12345",250000, Climate.SUB_TROPICAL,45.3);
        State state = new State("Texas","TX", Sets.newHashSet(city), Region.SOUTHWEST);
        Map<String,State> stateMap = new HashMap<String,State>();
        stateMap.put(state.getCode(),state);
        Function<String,State> stateObjectToStateCdMap = Functions.forMap(stateMap);
        Predicate<String> composed = Predicates.compose(new MidwestOrSouthwestRegionPredicate(),stateObjectToStateCdMap);
        assertThat(composed.apply("TX"),is(true));
    }

    @Test
    public void testPredicateAnd() throws Exception {
        LowRainfallPredicate dryPredicate = new LowRainfallPredicate();
        SmallPopulationPredicate smallPopulationPredicate = new SmallPopulationPredicate();
        Predicate<City> smallAndDry = Predicates.and(dryPredicate, smallPopulationPredicate);
        City smallWetCity = new City.Builder().averageRainfall(56.87).population(400000).build();
        assertThat(smallAndDry.apply(smallWetCity),is(false));
        City smallDryCity = new City.Builder().averageRainfall(25.63).population(450000).build();
        assertThat(smallAndDry.apply(smallDryCity),is(true));
    }

    @Test
    public void testPredicateAndWithList() throws Exception {
        LowRainfallPredicate dryPredicate = new LowRainfallPredicate();
        SmallPopulationPredicate smallPopulationPredicate = new SmallPopulationPredicate();
        List<Predicate<City>> predicateCityList = Lists.newArrayList(dryPredicate,smallPopulationPredicate);
        Predicate<City> smallAndDry = Predicates.and(predicateCityList);
        City smallWetCity = new City.Builder().averageRainfall(56.87).population(400000).build();
        assertThat(smallAndDry.apply(smallWetCity),is(false));
        City smallDryCity = new City.Builder().averageRainfall(25.63).population(450000).build();
        assertThat(smallAndDry.apply(smallDryCity),is(true));
    }

    @Test
    public void testPredicateOr() throws Exception {
        TemperateClimatePredicate climatePredicate = new TemperateClimatePredicate();
        SmallPopulationPredicate smallPopulationPredicate = new SmallPopulationPredicate();
        Predicate<City> smallOrTemperate = Predicates.or(climatePredicate, smallPopulationPredicate);
        City smallHumidCity = new City.Builder().climate(Climate.HUMID).population(400000).build();
        assertThat(smallOrTemperate.apply(smallHumidCity),is(true));
        City largeTemperateCity = new City.Builder().climate(Climate.TEMPERATE).population(2000000).build();
        assertThat(smallOrTemperate.apply(largeTemperateCity),is(true));
    }

    @Test  //Assume person want to live in a small city with lots of rainfall
    public void testPredicateNot()throws Exception {
        LowRainfallPredicate dryPredicate = new LowRainfallPredicate();
        SmallPopulationPredicate smallPopulationPredicate = new SmallPopulationPredicate();
        Predicate<City> smallAndWet = Predicates.and(Predicates.not(dryPredicate), smallPopulationPredicate);
        City smallWetCity = new City.Builder().averageRainfall(56.87).population(400000).build();
        assertThat(smallAndWet.apply(smallWetCity),is(true));
    }
}
