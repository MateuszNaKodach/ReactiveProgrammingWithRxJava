package wjug;

import io.reactivex.Observable;

/**
 * Created by Mateusz on 13.03.2017.
 */
public class PersonDAO {

    public Person findById(int id){
        Logger.print("Loading... " + id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Person();
    }

    public Observable<Person> rxFindById(int id){
        return Observable.fromCallable(()->findById(id));
    }
}
