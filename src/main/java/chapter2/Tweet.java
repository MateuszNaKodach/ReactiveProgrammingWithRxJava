package chapter2;

/**
 * Created by Mateusz on 13.03.2017.
 */
public class Tweet {
    String message = "";

    public Tweet(){

    }

    public Tweet(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
