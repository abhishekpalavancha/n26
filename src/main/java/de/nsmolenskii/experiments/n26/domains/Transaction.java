package de.nsmolenskii.experiments.n26.domains;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.nsmolenskii.experiments.n26.utils.json.JsonSnakeCase;
import de.nsmolenskii.experiments.n26.utils.validation.WithinLast;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import javax.validation.constraints.Min;

import static java.time.temporal.ChronoUnit.SECONDS;

@Immutable
@JsonSnakeCase
@JsonSerialize(as = ImmutableTransaction.class)
@JsonDeserialize(as = ImmutableTransaction.class)
public interface Transaction {

    @Min(0)
    @Parameter
    double getAmount();

    @Parameter
    @WithinLast(duration = 60, unit = SECONDS)
    long getTimestamp();
}
