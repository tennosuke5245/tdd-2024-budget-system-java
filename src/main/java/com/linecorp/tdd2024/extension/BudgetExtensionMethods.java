package com.linecorp.tdd2024.extension;

import com.linecorp.tdd2024.model.Budget;
import com.linecorp.tdd2024.util.DateTimeHelper;

public class BudgetExtensionMethods {

    public static double getAmountPerDay(Budget obj) {
        return (double)obj.getAmount() / (double)DateTimeHelper.parseFromYearMonth(obj.getYearMonth()).lengthOfMonth();
    }

}
