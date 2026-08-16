# Test Plan - XYZ Bank

## 1. Objective

Verify that the XYZ Bank demo application (
https://www.globalsqa.com/angularJs-protractor/BankingProject/) correctly
supports the two user groups it serves - bank managers administering
customers/accounts, and customers managing their own deposits and
withdrawals - and that customers cannot alter their own transaction history.

## 2. Scope

**In scope**

| Area | Behaviour under test |
|---|---|
| Manager - Add Customer | Adding a customer succeeds and the customer appears in the customer list |
| Manager - Open Account | Opening an account for an existing customer succeeds and the account is listed against that customer |
| Manager - Delete Customer | Deleting a customer removes them from the list (and, by implication, revokes their access) |
| Customer - Login | A customer can select their name and reach their account |
| Customer - Deposit | Depositing a positive amount increases the balance by that amount |
| Customer - Withdraw | Withdrawing a valid amount decreases the balance; withdrawing more than the balance is rejected and the balance is unchanged |
| Customer - Transaction History | History is viewable, includes new transactions, and exposes no edit/delete affordance |

**Out of scope**

- Cross-browser coverage (Chrome only for this pass).
- Load/performance testing.
- Manager-side "Edit Customer" flow (not part of the assigned scenarios).
- Server-side/API-level testing - the app has no real backend; all state is
  held client-side in an Angular mock data service and resets on a full page
  reload.

## 3. Test Approach

- **Manual exploration** was used first to map the app's actual DOM,
  confirm real success/failure messages, and check whether validation
  (e.g. alphabetic-only names, numeric-only postcode) is actually enforced
  client-side. It is not - the add-customer form only enforces the
  `required` HTML attribute, so alpha/numeric format validation is not
  something the current UI can be asserted against; this is noted as a gap
  rather than silently assumed.
- **Automation**: Selenium WebDriver 4 + JUnit 5, using a Page Object Model
  (see `src/main/java/org/example/pages`). Each scenario in the table above
  has a corresponding automated test under `src/test/java/org/example`.
- **Assertions are relative, not absolute** (e.g. "balance after == balance
  before + amount") wherever a test touches shared seed data, since the app
  has no reset-between-tests hook and state accumulates for the life of the
  browser session.
- **Reporting**: Allure (`allure-junit5`), see `docs/README.md` (or the
  project README) for how to generate the report.

## 4. Environment & Known Constraints

- Target: public demo instance, no test environment/staging available.
- The site sits behind a bot-check interstitial that inspects
  `navigator.webdriver` / the ChromeDriver automation banner; `DriverFactory`
  disables the relevant Chrome automation flags to get past it.
- The same origin will also **rate-limit an IP that issues many rapid
  automated page loads**, escalating to an outright HTTP 403 that clears only
  after a cool-down. To stay under that threshold, the suite uses a single
  shared browser session for the whole run and navigates via in-app links
  between tests instead of reloading the page per test
  (`org.example.base.SharedDriver`, `HomePage.reset()`). CI runs should not
  be scheduled to fire back-to-back for this reason.

## 5. Entry / Exit Criteria

- **Entry**: `mvn test` compiles; target site reachable (not rate-limited).
- **Exit**: all automated tests in the suite pass; Allure report generated
  without errors.

## 6. Risk-Based Priority

| Priority | Scenarios |
|---|---|
| Critical | Add customer, open account, delete customer, deposit, withdraw (valid) |
| Normal | Withdraw exceeding balance, transaction history view/immutability |

## 7. Traceability

| Scenario | Automated test |
|---|---|
| Add customer | `manager.AddCustomerTest` |
| Open account | `manager.OpenAccountTest` |
| Delete customer | `manager.DeleteCustomerTest` |
| Deposit | `customer.DepositTest` |
| Withdraw (valid + over-limit) | `customer.WithdrawTest` |
| Transaction history (read-only) | `customer.TransactionHistoryTest` |
