package wjug;

import io.reactivex.Observable;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Created by Mateusz on 13.03.2017.
 */

//https://www.youtube.com/watch?v=aXBq1LQSrks
public class WjugTest {

    Logger log = Logger.getLogger(getClass().getName());
    @Test
    public void wjug_9() throws Exception{

        //RxJava - just
        final CompletableFuture<String> fut = CompletableFuture.completedFuture("42");

        final String s42 = fut.get();

        System.out.println(42);


        //skladanie transformacji thenApply zwraca future od initgera, ze tutaj bedzei jakis future od stringa i zrob z nim to:
        final CompletableFuture<Integer> futInt = fut.thenApply((String s) -> s.length() + 1);

        final CompletableFuture<Double> doubleCompletableFuture = futInt.thenApply((Integer x) -> x * 2.0);


        CompletableFuture<Double> doubleCompletableFuture1 = fut.thenApply((String s) -> s.length() + 1)
                .thenApply((Integer x) -> x * 2.0);


        System.out.println(doubleCompletableFuture.get());
    }

    @Test
    public void wjug_36() throws Exception {
        final Observable<String> obs = Observable.just("42");

        obs.subscribe(s -> print(s));

    }



    @Test
    public void wjug_37() throws Exception {
        final Observable<String> obs = Observable.just("42", "43", "44");

        obs.subscribe(this::print);

    }


    private void print(Object obj){
        log.info("Got {}" + " |" +Thread.currentThread()+"| " + obj);
    }
}
