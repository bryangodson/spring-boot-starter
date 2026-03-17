package com.example.demo.fraud;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FraudDetectionService {

    private static final double HIGH_VALUE_TRANSACTION_THRESHOLD = 10000.00;
    private static final int HIGH_FREQUENCY_TRANSACTION_COUNT = 3;
    private static final long HIGH_FREQUENCY_TIME_WINDOW_MINUTES = 2;
    private static final int UNUSUAL_HOUR_START = 0; // Midnight
    private static final int UNUSUAL_HOUR_END = 6;   // 6 AM

    private final Map<String, List<Transaction>> recentTransactions = new ConcurrentHashMap<>();

    public boolean isFraudulent(Transaction transaction) {
        // Prune old transactions
        pruneOldTransactions(transaction.getUserId());

        // Rule 1: High value transactions
        if (transaction.getAmount() > HIGH_VALUE_TRANSACTION_THRESHOLD) {
            return true;
        }

        // Rule 2: High frequency of transactions
        List<Transaction> userTransactions = recentTransactions.computeIfAbsent(transaction.getUserId(), k -> new ArrayList<>());
        userTransactions.add(transaction);
        if (userTransactions.size() >= HIGH_FREQUENCY_TRANSACTION_COUNT) {
            return true;
        }

        // Rule 3: Transactions at unusual times (e.g., late at night)
        int hour = Instant.ofEpochMilli(transaction.getTimestamp()).atZone(java.time.ZoneOffset.UTC).getHour();
        if (hour >= UNUSUAL_HOUR_START && hour < UNUSUAL_HOUR_END) {
            return true;
        }

        // Rule 4: Suspiciously round amounts
        if (transaction.getAmount() % 500 == 0) {
            return true;
        }


        return false;
    }

    private void pruneOldTransactions(String userId) {
        if (userId == null) return;
        List<Transaction> userTransactions = recentTransactions.get(userId);
        if (userTransactions != null) {
            Instant twoMinutesAgo = Instant.now().minus(HIGH_FREQUENCY_TIME_WINDOW_MINUTES, ChronoUnit.MINUTES);
            userTransactions.removeIf(t -> Instant.ofEpochMilli(t.getTimestamp()).isBefore(twoMinutesAgo));
        }
    }
}
