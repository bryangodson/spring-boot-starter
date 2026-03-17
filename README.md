# Fraud Detection API

This is a Spring Boot application that provides a REST API for detecting fraudulent financial transactions. The purpose of this project is to provide a simple yet effective fraud detection system that can be integrated into other applications.

## Features

- REST API endpoint for fraud detection
- Multiple fraud detection rules:
    - **High-Value Transactions**: Flags transactions that exceed a certain amount.
    - **High-Frequency Transactions**: Detects when a user makes multiple transactions in a short period.
    - **Transactions at Unusual Hours**: Identifies transactions that occur during odd hours (e.g., late at night).
    - **Suspiciously Round Amounts**: Flags transactions with unusually round numbers (e.g., $1,000.00).

## Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.2 or later

### Running the Application

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```

2. Navigate to the project directory:
   ```bash
   cd fraud-detection-api
   ```

3. Run the application using Maven:
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`.

## API Usage

To check if a transaction is fraudulent, send a `POST` request to the `/fraud/detect` endpoint.

- **URL**: `/fraud/detect`
- **Method**: `POST`
- **Headers**: `Content-Type: application/json`

**Request Body**

```json
{
    "userId": "user-123",
    "amount": 15000.00,
    "currency": "USD",
    "timestamp": 1678886400000
}
```

**Responses**

- **Fraudulent Transaction**

  ```json
  {
      "isFraudulent": true
  }
  ```

- **Non-Fraudulent Transaction**

  ```json
  {
      "isFraudulent": false
  }
  ```

## Fraud Detection Logic

The fraud detection logic is implemented in the `FraudDetectionService` class and includes the following rules:

1.  **High-Value Transactions**: Any transaction with an amount greater than **$10,000** is considered fraudulent.

2.  **High-Frequency Transactions**: If a user makes **3 or more transactions** within a **2-minute window**, the transactions are flagged as fraudulent.

3.  **Transactions at Unusual Hours**: Transactions that occur between **midnight and 6 AM UTC** are considered suspicious.

4.  **Suspiciously Round Amounts**: Transactions with amounts that are perfectly divisible by **500** (e.g., $500, $1000, $2500) are flagged as suspicious.
