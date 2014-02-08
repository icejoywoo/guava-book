package bbejeck.guava.common.model;

import com.google.common.base.Objects;
import com.google.common.primitives.Doubles;

/**
 * User: Bill Bejeck
 * Date: 4/26/13
 * Time: 11:21 AM
 */
public class Stock {

    private String symbol;
    private double price;


    public Stock(double price, String symbol) {
        this.price = price;
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;

        Stock stock = (Stock) o;

        if (Doubles.compare(stock.price, price) != 0) return false;
        if (symbol != null ? !symbol.equals(stock.symbol) : stock.symbol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(symbol,price);
    }

    public static class Builder {
        private String symbol ="IBM";
        private double price = 200.00;

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Stock build(){
            return new Stock(this.price,this.symbol);
        }

    }

}
