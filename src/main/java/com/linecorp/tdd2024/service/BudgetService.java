package com.linecorp.tdd2024.service;

import com.linecorp.tdd2024.extension.BudgetExtensionMethods;
import com.linecorp.tdd2024.extension.LocalDateExtensionMethods;
import com.linecorp.tdd2024.extension.MapExtensionMethods;
import com.linecorp.tdd2024.model.Budget;
import com.linecorp.tdd2024.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.linecorp.tdd2024.util.DateTimeHelper.YEAR_MONTH_FORMATTER;
import static com.linecorp.tdd2024.util.DateTimeHelper.parseFromYearMonth;

@Service
@RequiredArgsConstructor
@ExtensionMethod({
    LocalDateExtensionMethods.class,
    BudgetExtensionMethods.class,
    MapExtensionMethods.class
})
public class BudgetService {

    private static final Budget ZERO_BUDGET = Budget.builder().yearMonth("190001").amount(0).build();

    private static final Supplier<Budget> GET_ZERO_BUDGET = () -> ZERO_BUDGET;

    private static final Supplier<Double> GET_ZERO = () -> 0.0;

    private final BudgetRepository budgetRepository;

    public double query(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            return 0.0;
        }

        Map<YearMonth, Budget> inPeriodBudgets = getInPeriodBudgets(start, end);

        double totalAmount = inPeriodBudgets.values()
                .stream()
                .map(Budget::getAmount)
                .map(Integer::doubleValue)
                .reduce(Double::sum)
                .orElseGet(GET_ZERO);

        totalAmount -= calculateFirstBudgetUncoveredAmount(inPeriodBudgets, start);
        totalAmount -= calculateLastBudgetUncoveredAmount(inPeriodBudgets, end);

        return totalAmount;
    }

    private Map<YearMonth, Budget> getInPeriodBudgets(LocalDate start, LocalDate end) {
        return budgetRepository.getAll()
                .stream()
                .filter(budget -> isBudgetSelected(budget, start, end))
                .collect(Collectors.toMap(
                        budget -> YearMonth.parse(budget.getYearMonth(), YEAR_MONTH_FORMATTER),
                        budget -> budget));
    }

    private boolean isBudgetSelected(Budget budget, LocalDate start, LocalDate end) {
        return parseFromYearMonth(budget.getYearMonth())
                .firstDayOfMonth()
                .isInPeriod(
                        start.firstDayOfMonth(),
                        end.lastDayOfMonth());
    }

    private double calculateFirstBudgetUncoveredAmount(Map<YearMonth, Budget> budgetMap, LocalDate start) {
        return (start.firstDayOfMonth().betweenDays(start) - 1) *
                getBudgetOrDefaultZeroBudget(budgetMap, start.toYearMonth()).getAmountPerDay();
    }

    private double calculateLastBudgetUncoveredAmount(Map<YearMonth, Budget> budgetMap, LocalDate end) {
        return (end.lastDayOfMonth().betweenDays(end) - 1) *
                getBudgetOrDefaultZeroBudget(budgetMap, end.toYearMonth()).getAmountPerDay();
    }

    private Budget getBudgetOrDefaultZeroBudget(Map<YearMonth, Budget> budgetMap, YearMonth key) {
        return Optional.ofNullable(budgetMap.get(key)).orElseGet(GET_ZERO_BUDGET);
    }

}
