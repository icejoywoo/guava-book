package bbejeck.guava.chapter7.events;

import bbejeck.guava.common.model.TradeAccount;
import static com.google.common.base.Preconditions.*;

import java.util.Date;

/**
 * User: Bill Bejeck
 * Date: 4/26/13
 * Time: 10:20 AM
 */
public class TradeAccountEvent {

    private double amount;
    private Date tradeExecutionTime;
    private TradeType tradeType;
    private TradeAccount tradeAccount;

    public TradeAccountEvent(TradeAccount account, double amount, Date tradeExecutionTime, TradeType tradeType) {
        checkArgument(amount > 0.0, "Trade can't be less than zero");
        this.amount = amount;
        this.tradeExecutionTime = checkNotNull(tradeExecutionTime,"ExecutionTime can't be null");
        this.tradeAccount = checkNotNull(account,"Account can't be null");
        this.tradeType = checkNotNull(tradeType,"TradeType can't be null");
    }

    public double getAmount() {
        return amount;
    }

    public Date getTradeExecutionTime() {
        return tradeExecutionTime;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public TradeAccount getTradeAccount() {
        return tradeAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TradeAccountEvent)) return false;

        TradeAccountEvent that = (TradeAccountEvent) o;

        if (Double.compare(that.amount, amount) != 0) return false;
        if (tradeAccount != null ? !tradeAccount.equals(that.tradeAccount) : that.tradeAccount != null) return false;
        if (tradeExecutionTime != null ? !tradeExecutionTime.equals(that.tradeExecutionTime) : that.tradeExecutionTime != null)
            return false;
        if (tradeType != that.tradeType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (tradeExecutionTime != null ? tradeExecutionTime.hashCode() : 0);
        result = 31 * result + (tradeType != null ? tradeType.hashCode() : 0);
        result = 31 * result + (tradeAccount != null ? tradeAccount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TradeAccountEvent{" +
                "amount=" + amount +
                ", tradeExecutionTime=" + tradeExecutionTime +
                ", tradeType=" + tradeType +
                ", tradeAccount=" + tradeAccount +
                "} ";
    }


    public static class Builder {
        private double amount = 1000.43;
        private Date tradeExecutionTime = new Date();
        private TradeType tradeType = TradeType.BUY;
        private TradeAccount tradeAccount = new TradeAccount.Builder().build();

        public Builder(){}

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder tradeExecutionTime(Date tradeExecutionTime) {
            this.tradeExecutionTime = tradeExecutionTime;
            return this;
        }

        public Builder tradeType(TradeType tradeType) {
            this.tradeType = tradeType;
            return this;
        }

        public Builder tradeAccount(TradeAccount tradeAccount) {
            this.tradeAccount = tradeAccount;
            return this;
        }

        public TradeAccountEvent build(){
            return new TradeAccountEvent(this.tradeAccount,this.amount,this.tradeExecutionTime,this.tradeType);
        }

    }
}
