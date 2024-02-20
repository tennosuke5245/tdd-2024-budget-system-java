package com.linecorp.tdd2024.service;

import com.linecorp.tdd2024.model.Budget;
import com.linecorp.tdd2024.repository.BudgetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class BudgetServiceTest {

    @InjectMocks
    private BudgetService budgetService;

    @Mock
    private BudgetRepository budgetRepository;

    @Test
    void testSingleDay() {
        when(budgetRepository.getAll()).thenReturn(List.of(
                Budget.builder()
                        .yearMonth("202409")
                        .amount(300)
                        .build()
        ));

        LocalDate start = LocalDate.parse("2024-09-01", DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse("2024-09-01", DateTimeFormatter.ISO_DATE);

        amountShouldBe(10,  budgetService.query(start, end));
    }

    @Test
    void testPartialDaysInSameMonth() {
        when(budgetRepository.getAll()).thenReturn(List.of(
                Budget.builder()
                        .yearMonth("202409")
                        .amount(600)
                        .build()
        ));

        LocalDate start = LocalDate.parse("2024-09-01", DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse("2024-09-05", DateTimeFormatter.ISO_DATE);

        amountShouldBe(100,  budgetService.query(start, end));
    }

    @Test
    void testCrossOneMonth() {
        when(budgetRepository.getAll()).thenReturn(List.of(
                Budget.builder()
                        .yearMonth("202409")
                        .amount(900)
                        .build(),
                Budget.builder()
                        .yearMonth("202410")
                        .amount(3100)
                        .build()
        ));

        LocalDate start = LocalDate.parse("2024-09-30", DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse("2024-10-02", DateTimeFormatter.ISO_DATE);

        amountShouldBe(230,  budgetService.query(start, end));
    }

    @Test
    void testCrossMultipleMonth() {
        when(budgetRepository.getAll()).thenReturn(List.of(
                Budget.builder()
                        .yearMonth("202409")
                        .amount(900)
                        .build(),
                Budget.builder()
                        .yearMonth("202410")
                        .amount(310)
                        .build(),
                Budget.builder()
                        .yearMonth("202411")
                        .amount(600)
                        .build()
        ));

        LocalDate start = LocalDate.parse("2024-09-29", DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse("2024-11-01", DateTimeFormatter.ISO_DATE);

        amountShouldBe(390,  budgetService.query(start, end));
    }

    @Test
    void testNoData() {
        when(budgetRepository.getAll()).thenReturn(List.of());

        LocalDate start = LocalDate.parse("2024-09-01", DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse("2024-09-01", DateTimeFormatter.ISO_DATE);

        amountShouldBe(0,  budgetService.query(start, end));
    }

    @Test
    void testCrossMultipleMonthWithNoData() {
        when(budgetRepository.getAll()).thenReturn(List.of(
                Budget.builder()
                        .yearMonth("202409")
                        .amount(30)
                        .build(),
                Budget.builder()
                        .yearMonth("202410")
                        .amount(620)
                        .build(),
                Budget.builder()
                        .yearMonth("202412")
                        .amount(310)
                        .build()
        ));

        LocalDate start = LocalDate.parse("2024-09-30", DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse("2024-12-02", DateTimeFormatter.ISO_DATE);

        amountShouldBe(641,  budgetService.query(start, end));
    }

    @Test
    void testInvalidateInput() {
        when(budgetRepository.getAll()).thenReturn(List.of(
                Budget.builder()
                        .yearMonth("202409")
                        .amount(300)
                        .build()
        ));

        LocalDate start = LocalDate.parse("2024-09-02", DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse("2024-09-01", DateTimeFormatter.ISO_DATE);

        amountShouldBe(0,  budgetService.query(start, end));
    }

    private void amountShouldBe(double expect, double actual) {
        assertEquals(expect, actual);
    }

}
