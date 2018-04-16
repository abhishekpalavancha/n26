package de.nsmolenskii.experiments.n26.controllers;

import de.nsmolenskii.experiments.n26.domains.ImmutableTransactionStatistic;
import de.nsmolenskii.experiments.n26.domains.TransactionStatistic;
import de.nsmolenskii.experiments.n26.services.MetricsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionStatisticsController.class)
public class TransactionStatisticsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MetricsService metricsService;


    @Test
    public void shouldReturnSampleStatistics() throws Exception {
        when(metricsService.getTransactionStatistics()).thenReturn(ImmutableTransactionStatistic.builder()
                .count(3)
                .sum(43.8)
                .max(42.5)
                .min(0.3)
                .build());

        mvc.perform(get("/api/statistics").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("count", is(3)))
                .andExpect(jsonPath("sum", is(43.8)))
                .andExpect(jsonPath("avg", is(14.6)))
                .andExpect(jsonPath("max", is(42.5)))
                .andExpect(jsonPath("min", is(0.3)));
    }

    @Test
    public void shouldReturnZeroStatistics() throws Exception {
        when(metricsService.getTransactionStatistics()).thenReturn(TransactionStatistic.ZERO);

        mvc.perform(get("/api/statistics").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("count", is(0)))
                .andExpect(jsonPath("sum", is(0.0)))
                .andExpect(jsonPath("avg", is("NaN")))
                .andExpect(jsonPath("max", is("NaN")))
                .andExpect(jsonPath("min", is("NaN")));
    }
}