package wjug.cache;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import wjug.Logger;

import java.time.Duration;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by Mateusz on 14.03.2017.
 */
public class CacheServer {


    public String findBy(long key) {
        Logger.print("Loading from Memcached: {} " +  key);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "<data>" + key + "</data>";
    }

    public Observable<String> rxFindBy(long key) {
        return Observable
                .fromCallable(() -> findBy(key))
                .subscribeOn(Schedulers.io());
    }
}
