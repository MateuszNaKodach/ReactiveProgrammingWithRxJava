package wjug;

import io.reactivex.Observable;

import java.time.Duration;

/**
 * Created by Mateusz on 13.03.2017.
 */
public class WeatherClient {

    public Weather fetch(String city){
        Logger.print("Loading for..." + city);
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Weather();
    }


    public Observable<Weather> rxFetch(String city) {
        return Observable.fromCallable(()->
                fetch(city));
    }
}
