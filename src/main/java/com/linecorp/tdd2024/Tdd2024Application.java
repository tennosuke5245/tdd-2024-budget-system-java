package com.linecorp.tdd2024;

import com.linecorp.tdd2024.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@RequiredArgsConstructor
public class Tdd2024Application implements CommandLineRunner {

    private final BudgetService budgetService;

    public static void main(String[] args) {
        SpringApplication.run(Tdd2024Application.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(budgetService.query(LocalDate.of(2024, 9, 30), LocalDate.of(2024, 12, 2)));

    }
}
