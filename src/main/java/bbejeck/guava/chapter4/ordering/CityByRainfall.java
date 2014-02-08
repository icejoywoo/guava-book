package bbejeck.guava.chapter4.ordering;

import bbejeck.guava.common.model.City;
import com.google.common.primitives.Doubles;

import java.util.Comparator;

/**
 * User: Bill Bejeck
 * Date: 4/12/13
 * Time: 9:59 PM
 */
public class CityByRainfall implements Comparator<City> {

    @Override
    public int compare(City city1, City city2) {
           return Doubles.compare(city1.getAverageRainfall(), city2.getAverageRainfall());
    }
}
