package chapter2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Mateusz on 13.03.2017.
 */
public class TweetMain {
    public static void main(String[] args) {
        TweetObserver tweetObserver1 = new TweetObserver();
        TweetObserver tweetObserver2 = new TweetObserver();

        Observable<Tweet> tweets = Observable.create(new ObservableOnSubscribe<Tweet>() {
            @Override
            public void subscribe(ObservableEmitter<Tweet> observableEmitter) throws Exception {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        observableEmitter.onNext(new Tweet("Tweet 1"));
                        observableEmitter.onNext(new Tweet("Tweet 2"));
                        observableEmitter.onNext(new Tweet("Tweet 3"));
                        observableEmitter.onNext(new Tweet("Tweet 4"));
                        observableEmitter.onNext(new Tweet("Tweet 5"));
                        try {
                            Thread.sleep(3000);
                        }catch(InterruptedException e){
                            observableEmitter.onError(e);
                        }
                        observableEmitter.onNext(new Tweet("Tweet 6"));
                        observableEmitter.onNext(new Tweet("Tweet 7"));
                    }
                }).start();


            }
        });


        tweets.subscribe(tweetObserver1);
        tweets.subscribe(tweetObserver2);

        System.out.println("Last line of code.");
    }
}
