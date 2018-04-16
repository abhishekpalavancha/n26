package de.nsmolenskii.experiments.n26.services.impl;

import de.nsmolenskii.experiments.n26.domains.ImmutableTransaction;
import de.nsmolenskii.experiments.n26.domains.TransactionStatistic;
import de.nsmolenskii.experiments.n26.utils.storage.MetricsStorage;
import de.nsmolenskii.experiments.n26.utils.validation.CastsUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MetricsServiceImplTest {

    private static final Class<UnaryOperator<TransactionStatistic>> STATISTIC_UPDATER = CastsUtils.cast(UnaryOperator.class);
    private static final Class<BinaryOperator<TransactionStatistic>> STATISTIC_REDUCER = CastsUtils.cast(BinaryOperator.class);
    private static final Long TIMESTAMP = new Random().nextLong();
    private static final double AMOUNT = new Random().nextDouble();

    @Mock
    private MetricsStorage<TransactionStatistic> storage;

    private MetricsServiceImpl service;

    @Mock
    private TransactionStatistic statistic;

    @Mock
    private TransactionStatistic anotherStatistic;

    @Mock
    private TransactionStatistic reducedStatistic;


    @Before
    public void setUp() throws Exception {
        service = new MetricsServiceImpl(storage);
    }

    @Test
    public void shouldRegisterTransaction() throws Exception {
        doAnswer(invocation -> invocation.getArgumentAt(1, STATISTIC_UPDATER).apply(statistic))
                .when(storage).update(eq(TIMESTAMP), any(STATISTIC_UPDATER));

        service.register(ImmutableTransaction.of(AMOUNT, TIMESTAMP));

        verify(statistic).register(AMOUNT);
    }

    @Test
    public void shouldReturnTransactionStatistics() throws Exception {
        doAnswer(invocation -> {
            invocation.getArgumentAt(0, STATISTIC_REDUCER).apply(statistic, anotherStatistic);
            return reducedStatistic;
        }).when(storage).reduce(any(STATISTIC_REDUCER));

        TransactionStatistic actual = service.getTransactionStatistics();

        assertThat(actual, is(reducedStatistic));

        verify(statistic).merge(anotherStatistic);
    }
}