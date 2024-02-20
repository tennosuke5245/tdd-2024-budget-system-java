package com.linecorp.tdd2024.repository;

import com.linecorp.tdd2024.model.Budget;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BudgetRepository {

    private final List<Budget> budgets = List.of(
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
                    .amount(3000)
                    .build(),
            Budget.builder()
                    .yearMonth("202412")
                    .amount(620)
                    .build()
    );

    public List<Budget> getAll() {
        return budgets;
    }

}
