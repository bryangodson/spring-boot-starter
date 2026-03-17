package com.example.demo.fraud;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fraud")
public class FraudDetectionController {

    private final FraudDetectionService fraudDetectionService;

    public FraudDetectionController(FraudDetectionService fraudDetectionService) {
        this.fraudDetectionService = fraudDetectionService;
    }

    @PostMapping("/detect")
    public FraudDetectionResult detect(@RequestBody Transaction transaction) {
        boolean isFraudulent = fraudDetectionService.isFraudulent(transaction);
        return new FraudDetectionResult(isFraudulent);
    }
}

class FraudDetectionResult {
    private boolean isFraudulent;

    public FraudDetectionResult(boolean isFraudulent) {
        this.isFraudulent = isFraudulent;
    }

    public boolean isFraudulent() {
        return isFraudulent;
    }

    public void setFraudulent(boolean fraudulent) {
        isFraudulent = fraudulent;
    }
}
