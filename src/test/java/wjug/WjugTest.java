package wjug;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;
import wjug.cache.CacheServer;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

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

    WeatherClient client = new WeatherClient();

    @Test
    public void wjug_59() throws Exception {
        System.out.println(client.fetch("Warsaw"));

    }


    @Test
    public void wjug_67() throws Exception {
        Observable<Weather> warsaw = client.rxFetch("Warsaw");

        warsaw.subscribe((Weather w)->print(w));
    }


    @Test
    public void wjug75() throws Exception {
        Observable<Weather> warsaw = client.rxFetch("Warsaw");

        final Observable<Weather> timeout = warsaw
                .timeout(800, MILLISECONDS);


        timeout.subscribe(this::print);


    }



    @Test
    public void wjug_98() throws Exception {
        Observable<Weather> weather1 = client.rxFetch("Warsaw");
        Observable<Weather> weather2 = client.rxFetch("Radom");

        //zwroci 2 obiekty:
        final Observable<Weather> weather1and2 = weather1.mergeWith(weather2);

    }


    @Test
    public void wjug_99() throws Exception {
        Observable<Weather> weather1 = client.rxFetch("Warsaw");
        Observable<Weather> weather2 = client.rxFetch("Radom");

        //zwroci 2 obiekty:
        final Observable<Weather> weather1and2 = weather1.mergeWith(weather2);

    }


    private final PersonDAO dao = new PersonDAO();

    @Test
    public void wjug_121() throws Exception {
        Observable<Weather> lodz = client
                .rxFetch("Lodz")
                .subscribeOn(Schedulers.io()); //ten Callable ma sie uruchomic w innym watku niz client
        Observable<Person> person = dao
                .rxFindById(42)
                .subscribeOn(Schedulers.io()); //Ale nie uzywaj io() !!!!!


        final Observable<String> str = lodz.zipWith(person, (Weather w, Person p) -> w + " : " + p.toString()); // lambda wywola sie dopieor gdy na obu strumieniach pojawi sie event

        str.subscribe(this::print);

        TimeUnit.SECONDS.sleep(2);//koniec na 35:10
    }


    @Test
    public void wjug_144() throws Exception {
        final Observable<String> strings = Observable.just("A", "B", "C");
        final Observable<Integer> numbers = Observable.range(1,10).map(x -> x*10);

        //strings.zipWith(numbers);
        final Observable<String> s2 = Observable.zip(
                strings,
                numbers,
                (s, n) -> s + n
        );

        s2.subscribe(this::print);

    }

    @Test
    public void wjug_167() throws Exception {
        Schedulers.io(); //tworzy tyle watkow ile potrzeba
        Schedulers.computation(); //tworzy tyle watkow ile rdzeni;
        Schedulers.from(new ScheduledThreadPoolExecutor(10)); //Mozna ustalic rozmiar ile bedzie dostepnych watkow
        //najlepsze rozwiazanie

        //A to juz w ogole best, zrobic wlasy executor, i tez nie nieskonczona kolejka
        new ThreadPoolExecutor(10, 10,
                0L, MILLISECONDS,
                new LinkedBlockingQueue<>(11));
    }


    //MondoDB, RxAndroid
    @Test
    public void wjug_175() throws Exception {
        CacheServer eu = new CacheServer();
        CacheServer us = new CacheServer();

        Observable<String> reu = eu.rxFindBy(42);
        Observable<String> rus = us.rxFindBy(45);

        //mergeWith
        //mozna ustawic timeout dla kazdego, i on bierze tylko pierwszy - first, to co szybciej nadejdzie
        Observable.merge(reu.timeout(2,TimeUnit.SECONDS),rus).first("Nothing")
        .subscribe(this::print);

        TimeUnit.SECONDS.sleep(2);



    }


    @Test
    public void wjug_194() throws Exception {
       Observable
               .interval(1,TimeUnit.SECONDS)
               .map(x->x* Math.PI)
               .subscribe(this::print);

       TimeUnit.SECONDS.sleep(5);



    }

    File dir = new File("C:\\Users\\Mateusz\\Documents\\Education\\SelfProgramming");

    @Test
    public void wjug_204() throws Exception {
        childrenOf(dir).subscribe(this::print);

    }

    //bedziemy periodycznie sprawdzac czy nie ma nowych plikow na dysku:
    @Test
    public void wjug_214() throws Exception {
        Observable
                .interval(1,TimeUnit.SECONDS)
                .map(x -> childrenOf2(dir))
                .subscribe(this::print);

        TimeUnit.SECONDS.sleep(20);
    }


    //bedziemy periodycznie sprawdzac czy nie ma nowych plikow na dysku:
    @Test
    public void wjug_250() throws Exception {
        Observable
                .interval(1,TimeUnit.SECONDS)
               // .concatMapIterable(x -> childrenOf2(dir))
                .flatMap(x->childrenOf(dir))
                .distinct()
                .blockingSubscribe(this::print);


        TimeUnit.SECONDS.sleep(20);
    }

    @Test
    public void wjug_215() throws Exception {
       Observable.empty(); //observable bez jednego eventu

        Observable
                .just(1,2) //tez zle
                .single(1); //jestem pewien, ze zwroci DOKLADNIE jeden event, a jest pusty
                //.subscribe();

        Observable
                .empty()
                .single(1) //jestem pewien, ze zwroci DOKLADNIE jeden event, a jest pusty
                .subscribe();

        TimeUnit.SECONDS.sleep(5);
    }


    @Test
    public void wjug_231() throws Exception {
       Observable.interval(100,TimeUnit.MILLISECONDS).single(new Long(1)).subscribe(this::print);
        TimeUnit.SECONDS.sleep(5);

    }



    List<String> childrenOf2(File dir){
       return childrenOf(dir)
               .toList() //robimy list z kolejnych eventow
               .blockingGet(); //blokujemy watek klienta, czekamy na zakonczenie strumienia
    }


    Observable<String> childrenOf(File dir){
        final File[] files = dir.listFiles();

        return Observable.fromArray(files).map(File::getName);
    }


    private void print(Object obj){
        log.info("Got {}" + " |" +Thread.currentThread()+"| " + obj);
    }



    //TESTOWANIE------------------------------------------------------------------

    //
    @Test
    public void wjug_312() throws Exception {
       verySlowSoapService()
            .timeout(1,TimeUnit.SECONDS) //czekaj 1 sekunde max
               .doOnError(ex -> this.print("Oooops" + ex)) //jesli sie nie powiedzie to zaloguj watning  z timeout
            //.retry()//w nieskonczonosc
            .retry(4) //porobuj 5 razy (4 razy jeszcze jak sie nie uda. Jak wystapi blad, sporobuj ponownie sie zasubskrybowac
               .onErrorReturn(x -> BigDecimal.ONE.negate())
       .blockingSubscribe(this::print); //jesli sie nie powiada to zastap wyjatek wartoscia -1

    }


    @Test
    public void wjug_329() throws Exception {
        TestScheduler testScheduler = new TestScheduler();
        verySlowSoapService()
                .timeout(1,TimeUnit.SECONDS, testScheduler) //czekaj 1 sekunde max
                .doOnError(ex -> this.print("Oooops" + ex)) //jesli sie nie powiedzie to zaloguj watning  z timeout
                //.retry()//w nieskonczonosc
                .retry(4) //porobuj 5 razy (4 razy jeszcze jak sie nie uda. Jak wystapi blad, sporobuj ponownie sie zasubskrybowac
                .onErrorReturn(x -> BigDecimal.ONE.negate());
              //  .subscribe(new TestSubscriber<BigDecimal>()); //jesli sie nie powiada to zastap wyjatek wartoscia -1



    }


    private Observable<BigDecimal> verySlowSoapService() {
        return Observable
                //.interval(1,TimeUnit.MINUTES).take(1)
                .timer(1,TimeUnit.MINUTES)
                .map(x->BigDecimal.ZERO);
    }
}
