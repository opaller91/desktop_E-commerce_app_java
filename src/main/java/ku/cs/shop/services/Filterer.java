package ku.cs.shop.services;

public interface Filterer<T> {
    T filter(T t);
}
