package com.example.demo.fraud;

import org.springframework.stereotype.Service;

@Service
public class FraudDetectionService {

    private static final double HIGH_VALUE_TRANSACTION_THRESHOLD = 10000.00;

    public boolean isFraudulent(Transaction transaction) {
        // Rule 1: High value transactions
        if (transaction.getAmount() > HIGH_VALUE_TRANSACTION_THRESHOLD) {
            return true;
        }

        return false;
    }
}
