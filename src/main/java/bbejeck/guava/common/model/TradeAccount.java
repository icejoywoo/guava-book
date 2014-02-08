package bbejeck.guava.common.model;

import static com.google.common.base.Preconditions.*;
/**
 * User: Bill Bejeck
 * Date: 4/20/13
 * Time: 11:43 PM
 */
public class TradeAccount {
    private String id;
    private String owner;
    private double balance;

    public TradeAccount(String id, String owner, double balance) {
        this.id = checkNotNull(id,"ID can't be null");
        this.owner = checkNotNull(owner,"Owner can't be null");
        checkArgument(balance > 0.0,"Balance can't be less than 0");
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TradeAccount)) return false;

        TradeAccount that = (TradeAccount) o;

        if (Double.compare(that.balance, balance) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


    public static class Builder {
        private String id =" 0087666333";
        private String owner = "Smith, Jane";
        private double balance = 750000.87;

        public Builder(){

        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder balance(double balance) {
            this.balance = balance;
            return this;
        }

        public TradeAccount build(){
            return new TradeAccount(this.id,this.owner,this.balance);
        }
    }
}
