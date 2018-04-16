package de.nsmolenskii.experiments.n26.utils.storage;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public interface MetricsStorage<T> {
    void update(long timestamp, UnaryOperator<T> updater);

    T reduce(BinaryOperator<T> reducer);
}
