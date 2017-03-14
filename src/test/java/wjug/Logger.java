package wjug;

/**
 * Created by Mateusz on 13.03.2017.
 */
public class Logger {

    static java.util.logging.Logger log = java.util.logging.Logger.getLogger("Logger");

    public static void print(Object obj){
        log.info("Got {}" + " |" +Thread.currentThread()+"| " + obj);
    }
}
