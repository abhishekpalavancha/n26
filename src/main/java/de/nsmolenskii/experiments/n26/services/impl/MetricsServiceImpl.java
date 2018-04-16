package de.nsmolenskii.experiments.n26.services.impl;

import com.google.common.annotations.VisibleForTesting;
import de.nsmolenskii.experiments.n26.domains.Transaction;
import de.nsmolenskii.experiments.n26.domains.TransactionStatistic;
import de.nsmolenskii.experiments.n26.services.MetricsService;
import de.nsmolenskii.experiments.n26.utils.storage.AtomicMetricsStorage;
import de.nsmolenskii.experiments.n26.utils.storage.MetricsStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static de.nsmolenskii.experiments.n26.domains.TransactionStatistic.ZERO;

@Service
public class MetricsServiceImpl implements MetricsService {

    private final MetricsStorage<TransactionStatistic> transactions;

    @Autowired
    public MetricsServiceImpl() {
        this(AtomicMetricsStorage.lastMinute(() -> ZERO));
    }

    @VisibleForTesting
    protected MetricsServiceImpl(MetricsStorage<TransactionStatistic> transactions) {
        this.transactions = transactions;
    }

    @Override
    public void register(Transaction transaction) {
        transactions.update(transaction.getTimestamp(), statistic -> statistic.register(transaction.getAmount()));
    }

    @Override
    public TransactionStatistic getTransactionStatistics() {
        return transactions.reduce(TransactionStatistic::merge);
    }
}
