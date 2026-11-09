package com.example.bookmanager.service;

import java.util.concurrent.CompletableFuture;

public interface ReportService {

    public CompletableFuture<String> generateBookReport();
}
