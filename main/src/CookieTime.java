/**
 * Class representing a tuple containing cookie id and the time it was seen
 * Notice this is only the time, not the date.
 * @param <S> cookie id
 * @param <T> timestamp
 */
public class CookieTime<S, T> {

    public final  S s;
    public final T t;

    public CookieTime(S s, T t) {
        this.s = s;
        this.t = t;
    }

    public S getCookie() {
        return s;
    }

    public T getTime() {
        return t;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof CookieTime)) {
            return false;
        }

        CookieTime<S, T> cookieTime = (CookieTime<S, T>) o;
        return cookieTime.getCookie().equals(this.getCookie()) && cookieTime.getTime().equals(this.getTime());
    }

}
