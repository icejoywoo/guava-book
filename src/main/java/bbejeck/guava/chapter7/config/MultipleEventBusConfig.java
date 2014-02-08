package bbejeck.guava.chapter7.config;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.*;

/**
 * User: Bill Bejeck
 * Date: 4/26/13
 * Time: 3:48 PM
 */
@Configuration
@ComponentScan(basePackages = {"bbejeck.guava.chapter7.publisher.complex",
                              "bbejeck.guava.chapter7.subscriber.complex"})
public class MultipleEventBusConfig {

    @Bean(autowire = Autowire.BY_NAME,name = "salesEventBus")
    public EventBus salesEventBus() {
        return new EventBus();
    }
    @Bean(autowire = Autowire.BY_NAME,name = "buysEventBus")
    public EventBus buysEventBus() {
        return new EventBus();
    }


}
