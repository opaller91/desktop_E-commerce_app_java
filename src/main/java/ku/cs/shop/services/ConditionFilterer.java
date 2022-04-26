package ku.cs.shop.services;

public interface ConditionFilterer <T> {
    boolean check(T t);
}
