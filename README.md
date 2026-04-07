# UI Test Automation – Demo E-commerce Application

This project presents a simple UI test automation framework created using Java, Selenium WebDriver and TestNG.
The tests are implemented for a demo e-commerce web application (Demoblaze).

## Tech Stack
- Java
- Selenium WebDriver
- TestNG
- Maven

## Project Structure
- pages – Page Object classes
- tests – Test classes
- utils – Utility and helper classes

## Test Coverage
- Basic UI validation
- Key user flows validation
- Element visibility and navigation checks

## Continuous Integration
The project is integrated with GitHub Actions.  
Tests are automatically executed on every pull request in a headless browser environment.

The pipeline:
- builds the project using Maven
- runs UI tests in headless mode
- generates test reports (Surefire + ExtentReports)
- uploads reports as artifacts for further analysis

## How to Run Tests
1. Clone the repository
2. Run tests using Maven:
```bash
mvn test
```

## Design Decisions
- Page Object Pattern used to separate test logic from UI interactions.
- Explicit waits preferred over implicit waits to reduce flaky tests.
- Method chaining applied in Page Object classes to improve test readability and express user flows more clearly.

This project is intended for learning purposes and demonstrates good practices in UI test automation.
