package chapter2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static java.util.concurrent.TimeUnit.MICROSECONDS;

/**
 * Created by Mateusz on 13.03.2017.
 */
public class CreateMain {
    public static void main(String[] args) {
        log("Before");
        Observable
                .range(5, 3)
                .subscribe(i -> {
                    log(i);
                });
        log("After");



        Observable<Integer> ints = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> subscriber) throws Exception {
                log("Create");
                subscriber.onNext(5);
                subscriber.onNext(6);
                subscriber.onNext(7);
                log("Completed");
            }
        })/*.publish().refCount()*/;


        log("Starting");
        ints.subscribe(i->log(i));
        ints.subscribe(i->log(i));
        ints.subscribe(i->log(i));
        log("Exit");


        Observable
                .interval(1_000_000 / 60, MICROSECONDS)
                .subscribe((Long i) -> log(i));


    }


    static <T> Observable<Integer> range(int from, int n){
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<java.lang.Integer> observableEmitter) throws Exception {
                for(int i=from;i<n;i++) {
                    observableEmitter.onNext(i);

                }
                observableEmitter.onComplete();
            }
        });

    }

    static <T> Observable<T> just(T x) {
        return Observable.create(subscriber -> {
            subscriber.onNext(x);
            subscriber.onComplete();
        });

    }

    private static void log(Object msg) {
        System.out.println(
                Thread.currentThread().getName() +
                        ": " + msg);
    }

}
