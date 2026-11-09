package com.example.bookmanager.service;

import java.util.concurrent.CompletableFuture;

public interface ReportService {

    public CompletableFuture<String> generateBookReport();

    public void generateDailyReport(long totalBooks);

    public void generateWeeklyReport();

    public void generateMonthlyReport();
}
