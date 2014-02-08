package bbejeck.guava.common.model;

import com.google.common.primitives.Doubles;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: Bill Bejeck
 * Date: 4/1/13
 * Time: 8:40 PM
 */
public class City {

    private String name;
    private String zipCode;
    private int population;
    private double averageRainfall;
    private Climate climate;

    public City(String name, String zipCode, int population, Climate climate, double averageRainfall) {
        this.name = checkNotNull(name, "Name can't be null");
        this.zipCode = checkNotNull(zipCode, "Zip code can't be null");
        checkArgument(population > 0, "Population can't be 0");
        this.population = population;
        this.climate = checkNotNull(climate);
        checkArgument(averageRainfall > 0, "Average rainfall can't be 0");
        this.averageRainfall = averageRainfall;
    }

    public String getName() {
        return name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public int getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return "City{" +
                "averageRainfall=" + averageRainfall +
                ", name='" + name + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", population=" + population +
                ", climate=" + climate +
                "} " + super.toString();
    }

    public Climate getClimate() {
        return climate;
    }

    public double getAverageRainfall() {
        return averageRainfall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;

        City city = (City) o;

        if (Doubles.compare(city.averageRainfall, averageRainfall) != 0) return false;
        if (population != city.population) return false;
        if (climate != city.climate) return false;
        if (name != null ? !name.equals(city.name) : city.name != null) return false;
        if (zipCode != null ? !zipCode.equals(city.zipCode) : city.zipCode != null) return false;

        return true;
    }



    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + population;
        temp = Double.doubleToLongBits(averageRainfall);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (climate != null ? climate.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private String name = "New York, NY";
        private String zipCode = "1234567";
        private int population = 100000;
        private double averageRainfall = 15.0;
        private Climate climate = Climate.TEMPERATE;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder population(int population) {
            this.population = population;
            return this;
        }

        public Builder averageRainfall(double averageRainfall) {
            this.averageRainfall = averageRainfall;
            return this;
        }

        public Builder climate(Climate climate) {
            this.climate = climate;
            return this;
        }

        public City build() {
            return new City(this.name, this.zipCode, this.population, this.climate, this.averageRainfall);
        }


    }
}
