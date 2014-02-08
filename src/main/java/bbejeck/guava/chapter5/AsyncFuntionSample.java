package bbejeck.guava.chapter5;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;

/**
 * User: Bill Bejeck
 * Date: 4/17/13
 * Time: 9:39 PM
 */
public class AsyncFuntionSample implements AsyncFunction<Long,String> {

    private ConcurrentMap<Long,String> map = Maps.newConcurrentMap();
    private ListeningExecutorService listeningExecutorService;

    public AsyncFuntionSample() {
        map.put(1L,"FOO");
        map.put(2L,"BAR");
        map.put(3L,"BAZ");
        ExecutorService executorService = MoreExecutors.getExitingExecutorService((ThreadPoolExecutor)Executors.newFixedThreadPool(3));
        listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
    }

    @Override
    public ListenableFuture<String> apply(final Long input) throws Exception {
        if(map.containsKey(input)) {
            SettableFuture<String> listenableFuture = SettableFuture.create();
            listenableFuture.set(map.get(input));
            return listenableFuture;
        }else{
            return listeningExecutorService.submit(new Callable<String>(){
                @Override
                public String call() throws Exception {
                    String retrieved = "AsyncValue";
                     map.putIfAbsent(input,retrieved);
                     return retrieved;
                }
            });
        }

    }
}
