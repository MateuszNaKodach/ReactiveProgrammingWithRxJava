package chapter2;

import io.reactivex.Observable;

/**
 * Created by Mateusz on 13.03.2017.
 */
public class CacheMain {
    public static void main(String[] args) {
       /* Observable<Integer> ints1 =
                Observable.create(subscriber -> {
                            log("Create");
                            subscriber.onNext(42);
                            subscriber.onComplete();
                        }
                );
        log("Starting");
        ints1.subscribe(i -> log("Element A: " + i));
        ints1.subscribe(i -> log("Element B: " + i));
        log("Exit");*/

        Observable<Integer> ints2 =
                Observable.<Integer>create(subscriber -> {
                            log("Create");
                            subscriber.onNext(42);
                            subscriber.onComplete();
                        }
                ).cache();
        log("Starting");
        ints2.subscribe(i -> log("Element A: " + i));
        ints2.subscribe(i -> log("Element B: " + i));
        log("Exit");




    }


    private static void log(Object msg) {
        System.out.println(
                Thread.currentThread().getName() +
                        ": " + msg);
    }
}
