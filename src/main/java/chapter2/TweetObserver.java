package chapter2;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Mateusz on 13.03.2017.
 */
public class TweetObserver implements Observer<Tweet> {

    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(Tweet tweet) {
        System.out.println("Observer print Tweet:" + tweet.toString());
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("There is no more tweets.");
    }
}
