package de.nsmolenskii.experiments.n26.utils.validation;

public interface CastsUtils {

    @SuppressWarnings("unchecked")
    static <T> Class<T> cast(Class<?> target) {
        return (Class<T>) target;
    }
}
