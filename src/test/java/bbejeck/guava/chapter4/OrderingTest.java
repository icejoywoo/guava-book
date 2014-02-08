package bbejeck.guava.chapter4;


import bbejeck.guava.chapter4.ordering.CityByPopulation;
import bbejeck.guava.chapter4.ordering.CityByRainfall;
import bbejeck.guava.common.model.City;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * User: Bill Bejeck
 * Date: 4/12/13
 * Time: 8:24 AM
 */
public class OrderingTest {

    private Ordering<Integer> numberOrdering = new Ordering<Integer>() {
        @Override
        public int compare(Integer left, Integer right) {
              return left.compareTo(right);
        }
    };
    private CityByPopulation cityByPopulation = new CityByPopulation();
    private CityByRainfall cityByRainfall = new CityByRainfall();
    private City.Builder cityBuilder = new City.Builder();

    @Test
    public void testGetGreatestOf(){
        List<Integer> numbers = Lists.newArrayList(1,2,3,500000000,19,5,100,3000);
        List<Integer> top3 = numberOrdering.greatestOf(numbers,3);
        List<Integer> expected = Lists.newArrayList(500000000,3000,100);
        assertThat(expected,is(top3));
    }

    @Test
    public void testGetLeastOf(){
        List<Integer> numbers = Lists.newArrayList(1,500000000,19,4,5,100,3000,2);
        List<Integer> bottom3 = numberOrdering.leastOf(numbers,3);
        List<Integer> expected = Lists.newArrayList(1,2,4);
        assertThat(expected,is(bottom3));
    }

    @Test
    public void testGetMax(){
        List<Integer> numbers = Lists.newArrayList(1,500000000,19,4,5,100,3000,2);
        assertThat(numberOrdering.max(numbers),is(500000000));
    }

    @Test
    public void testGetMin(){
        List<Integer> numbers = Lists.newArrayList(10,500000000,19,4,5,100,3000,2);
        assertThat(numberOrdering.min(numbers),is(2));
    }

    @Test
    public void testReverseSort(){
      City city1 = cityBuilder.population(100000).build();
      City city2 = cityBuilder.population(250000).build();
      City city3 = cityBuilder.population(350000).build();
      List<City> cities = Lists.newArrayList(city3,city2,city1);
      Ordering<City> cityOrdering = Ordering.from(cityByPopulation);
      Collections.sort(cities,cityOrdering);
      //In natural sorting order
      assertThat(cities.get(0),is(city1));
      Collections.sort(cities,cityOrdering.reverse());
      assertThat(cities.get(0),is(city3));
    }

    @Test
    public void testSecondarySort(){
        City city1 = cityBuilder.population(100000).averageRainfall(55.0).build();
        City city2 = cityBuilder.population(100000).averageRainfall(45.0).build();
        City city3 = cityBuilder.population(100000).averageRainfall(33.8).build();
        List<City> cities = Lists.newArrayList(city1,city2,city3);
        Ordering<City> secondaryOrdering = Ordering.from(cityByPopulation).compound(cityByRainfall);
        Collections.sort(cities,secondaryOrdering);
        assertThat(cities.get(0),is(city3));
    }

    @Test
    public void testSortNullFirst(){
        City city1 = cityBuilder.population(100000).averageRainfall(55.0).build();
        City city2 = cityBuilder.population(100000).averageRainfall(45.0).build();
        City city3 = cityBuilder.population(100000).averageRainfall(33.8).build();
        List<City> cities = Lists.newArrayList(city1,city2,city3,null);
        Ordering<City> nullsFirstOrdering = Ordering.from(cityByPopulation).nullsFirst();
        Collections.sort(cities,nullsFirstOrdering);
        assertThat(cities.get(0),is(nullValue()));
    }

    @Test
    public void testSortNullLast(){
        City city1 = cityBuilder.population(100000).averageRainfall(55.0).build();
        City city2 = cityBuilder.population(100000).averageRainfall(45.0).build();
        City city3 = cityBuilder.population(100000).averageRainfall(33.8).build();
        List<City> cities = Lists.newArrayList(null,city1,city2,city3);
        Ordering<City> nullsLastOrdering = Ordering.from(cityByPopulation).nullsLast();
        Collections.sort(cities,nullsLastOrdering);
        assertThat(cities.get(3),is(nullValue()));
    }
}
