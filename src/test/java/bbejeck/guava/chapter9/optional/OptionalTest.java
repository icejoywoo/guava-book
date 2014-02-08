package bbejeck.guava.chapter9.optional;

import bbejeck.guava.common.model.TradeAccount;
import com.google.common.base.Optional;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 * User: Bill Bejeck
 * Date: 5/11/13
 * Time: 12:33 AM
 */
public class OptionalTest {

    @Test
    public void testOptionalNotNull(){
        TradeAccount tradeAccount = new TradeAccount.Builder().build();
        Optional<TradeAccount> tradeAccountOptional = Optional.fromNullable(tradeAccount);
        assertThat(tradeAccountOptional.isPresent(),is(true));
    }

    @Test
    public void testOptionalOfInstance(){
        TradeAccount tradeAccount = new TradeAccount.Builder().build();
        Optional<TradeAccount> tradeAccountOptional = Optional.of(tradeAccount);
        assertThat(tradeAccountOptional.isPresent(),is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void testOptionalNull(){
        Optional<TradeAccount> tradeAccountOptional = Optional.fromNullable(null);
        assertThat(tradeAccountOptional.isPresent(),is(false));
        tradeAccountOptional.get();
    }
}
