package de.nsmolenskii.experiments.n26.domains;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.nsmolenskii.experiments.n26.utils.json.JsonSnakeCase;
import org.immutables.value.Value.Immutable;

@Immutable
@JsonSnakeCase
@JsonSerialize(as = ImmutableTransactionStatistic.class)
@JsonDeserialize(as = ImmutableTransactionStatistic.class)
public interface TransactionStatistic {

    long getCount();

    double getSum();

    double getMax();

    double getMin();

    default double getAvg() {
        return getCount() > 0 ? getSum() / getCount() : Double.NaN;
    }

    TransactionStatistic ZERO = ImmutableTransactionStatistic.builder()
            .count(0)
            .sum(0.0)
            .max(Double.NaN)
            .min(Double.NaN)
            .build();


    default TransactionStatistic register(double amount) {
        return this.equals(ZERO) ?
                ImmutableTransactionStatistic.builder()
                        .count(1)
                        .sum(amount)
                        .min(amount)
                        .max(amount)
                        .build() :
                ImmutableTransactionStatistic.builder()
                        .count(getCount() + 1)
                        .sum(getSum() + amount)
                        .min(Math.min(getMin(), amount))
                        .max(Math.max(getMax(), amount))
                        .build();
    }

    default TransactionStatistic merge(TransactionStatistic that) {
        if (this.equals(ZERO)) {
            return that;
        }
        if (that.equals(ZERO)) {
            return this;
        }
        return ImmutableTransactionStatistic.builder()
                .count(this.getCount() + that.getCount())
                .sum(this.getSum() + that.getSum())
                .min(Math.min(this.getMin(), that.getMin()))
                .max(Math.max(this.getMax(), that.getMax()))
                .build();
    }
}
