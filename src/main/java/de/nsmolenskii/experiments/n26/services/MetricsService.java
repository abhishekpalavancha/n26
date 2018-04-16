package de.nsmolenskii.experiments.n26.services;

import de.nsmolenskii.experiments.n26.domains.Transaction;
import de.nsmolenskii.experiments.n26.domains.TransactionStatistic;

public interface MetricsService {
    void register(Transaction transaction);

    TransactionStatistic getTransactionStatistics();
}
