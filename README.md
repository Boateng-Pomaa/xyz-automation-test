# XYZ Bank Test Automation

Selenium WebDriver + JUnit 5 automation for the XYZ Bank demo application:
https://www.globalsqa.com/angularJs-protractor/BankingProject/

See [`docs/TEST_PLAN.md`](docs/TEST_PLAN.md) for scope, approach, and
known constraints (including an important one about the site's
rate-limiting - read that before running the suite repeatedly).

## Prerequisites

- JDK 24
- Maven 3.9+
- Google Chrome installed locally (not required if running via Docker)
- Docker, if you want to run the suite in a container / reproduce CI locally

## Project layout

```
src/main/java/org/example/
  driver/DriverFactory.java      # Chrome/ChromeDriver setup (headless in CI, anti-bot-detection flags)
  pages/                         # Page Object Model
    HomePage.java
    manager/                     # AddCustomerPage, OpenAccountPage, CustomersPage, ManagerDashboardPage
    customer/                    # CustomerLoginPage, AccountPage, DepositPage, WithdrawPage, TransactionsPage

src/test/java/org/example/
  base/BaseTest.java             # shared setup - all tests extend this
  base/SharedDriver.java         # one browser session for the whole test run
  manager/                       # AddCustomerTest, OpenAccountTest, DeleteCustomerTest
  customer/                      # DepositTest, WithdrawTest, TransactionHistoryTest
```

## Running the tests locally

```bash
mvn test
```

This launches a single Chrome session for the entire run and reuses it
across every test class (see `docs/TEST_PLAN.md` for why - the site
rate-limits repeated full page loads). Don't re-run this back-to-back;
space runs out a bit if you're iterating.

## Allure report

Test results are written to `target/allure-results` on every `mvn test`
run. To view them as an HTML report:

```bash
mvn allure:serve
```

This builds the report and opens it in your browser. To just generate the
static report without opening it:

```bash
mvn allure:report
# output in target/site/allure-maven-plugin
```

## Running in Docker

```bash
docker build -t xyz-bank-tests .
docker run --rm -v "$(pwd)/target:/workspace/target" xyz-bank-tests
```

The container installs JDK 24, Maven, and Chrome; Selenium Manager resolves
a matching chromedriver automatically. Allure results land in your local
`target/allure-results` via the volume mount, so `mvn allure:serve` works
the same way afterwards.

## CI/CD

`.github/workflows/ci.yml` builds the Docker image, runs the suite, and
publishes the Allure report to GitHub Pages. It's triggered manually
(`workflow_dispatch`) or on pushes to `main` - intentionally not on every
push/PR, to avoid piling more automated traffic onto a site that's already
known to rate-limit it.
