# Banking Project

A command-driven banking simulator written in Java. The system reads a list of text commands, validates each one, applies the valid ones to an in-memory bank, and produces a report of final account states, per-account transaction history, and rejected commands.

Built as a course project at Drexel University with a test-first workflow — JUnit 5, JaCoCo line coverage, and PIT mutation testing.

## How it works

The system is a pipeline. There is no `main` method; `MasterControl` is the entry point and is exercised through the test suite.

```
List<String> input
      │
      ▼
CommandValidator ──► delegates to CreateValidator / DepositValidator /
      │              WithdrawValidator / TransferValidator / PassTimeValidator
      │
      ├─ valid ────► CommandProcessor ──► Bank ──► Checking / Savings / CD
      │                                     │
      │                                TimeService
      │
      └─ invalid ──► CommandStorage
                          │
                          ▼
                   OutputGenerator ──► List<String>
```

- **`MasterControl`** — loops over the input, routes each command to validation, processing, and storage.
- **`CommandValidator`** — parses the command word and hands off to a type-specific subclass. Base class returns `false`, so unrecognized command types are rejected.
- **`CommandProcessor`** — converts a validated command string into calls on `Bank`.
- **`Bank`** — owns the accounts (a `LinkedHashMap` keyed by account ID) and the time service. Handles deposits, withdrawals, transfers, and month-passing.
- **`Account`** — abstract base for `Checking`, `Savings`, and `CD`. Each subclass enforces its own deposit and withdrawal rules.
- **`TimeService` / `BankTimeService`** — month counter behind an interface so tests can inject a fake clock.
- **`CommandStorage`** — keeps valid and invalid commands in the order they arrived.
- **`OutputGenerator`** — formats the final report.

## Command language

Commands are space-delimited and case-insensitive on the command word. Account IDs must be exactly 8 digits.

| Command | Syntax | Notes |
|---|---|---|
| Create | `create checking <id> <apr>` | APR must be greater than 0 and at most 10 |
| Create | `create savings <id> <apr>` | |
| Create | `create cd <id> <apr> <amount>` | Opening amount must be between 1000 and 10000 |
| Deposit | `deposit <id> <amount>` | |
| Withdraw | `withdraw <id> <amount>` | |
| Transfer | `transfer <from-id> <to-id> <amount>` | Source and destination must differ |
| Pass time | `pass <months>` | 1 to 60 months |

Example:

```
create savings 12345678 0.6
deposit 12345678 700
create checking 98765432 0.1
transfer 12345678 98765432 300
pass 12
```

### Account rules

**Checking**
- Withdrawals capped at 400 per transaction.
- Deposits capped at 1000 by the account; the deposit validator currently rejects anything at or above 400.

**Savings**
- Deposits capped at 2500.
- Withdrawals capped at 1000 and limited to one per month.

**CD**
- Opened with a lump sum between 1000 and 10000. No deposits afterward.
- No withdrawals until 12 months have passed, and the withdrawal must take the entire balance.
- Interest compounds four times per month instead of once.

### Passing time

For each month elapsed, `Bank.passTime` runs three steps in order:

1. Close any account with a balance of 0.
2. Deduct 25 from any account with a balance below 100.
3. Accrue interest — monthly rate is `APR / 100 / 12`, truncated (not rounded) to two decimal places.

### Output format

Each surviving account prints as `<Type> <id> <balance> <apr>`, followed by the transactions that touched it, in order. Invalid commands are echoed verbatim at the end.

```
Savings 12345678 400.00 0.60
transfer 12345678 98765432 300
Checking 98765432 300.00 0.00
transfer 12345678 98765432 300
depositt 12345678 100
```

Balances and APRs are truncated toward zero to two decimals, not rounded.

## Requirements

- JDK 11 or later
- Gradle 8.10

The `gradle/wrapper` directory is present but the `gradlew` scripts are not committed, so use a locally installed Gradle. To regenerate the wrapper:

```bash
gradle wrapper --gradle-version 8.10
```

## Build and test

```bash
gradle build          # compile and run tests
gradle test           # tests only
gradle jacocoTestReport   # coverage → builds/jacoco/jacoco.xml
gradle pitest         # mutation coverage → build/reports/pitest/
```

The test suite lives in `src/test/java/banking` and covers the bank, each account type, each validator, the time service, and end-to-end scenarios through `MasterControl`.

## Project structure

```
src/main/java/banking/     domain and application classes
src/test/java/banking/     JUnit 5 test suite
build.gradle               Java, JaCoCo, and PIT configuration
.gitlab-ci.yml             CI pipeline (build, test, code quality, mutation coverage)
.codeclimate.yml           static analysis configuration
```

## Known gaps

- `Checking` discards the APR passed to its constructor and always reports 0.00.
- The withdraw validator allows savings withdrawals up to 2500, while the `Savings` account itself rejects anything over 1000 — the command is accepted but silently does nothing.
- The savings one-withdrawal-per-month rule is enforced in the account but not in the validator, so a second withdrawal in the same month is reported as valid.
- Validators index into the command array before checking its length, so short commands such as `create checking` raise an `ArrayIndexOutOfBoundsException` rather than being rejected.
- `pass` with a non-numeric argument throws a `NumberFormatException`.
- The monthly low-balance deduction applies to CDs along with everything else.
- `CommandProcessorTest` is an empty placeholder.
- CI is configured for GitLab; the pipeline does not run on GitHub.
- Build output and IDE files (`.gradle/`, `.idea/`, `bin/`) are tracked in version control. The project has no `.gitignore`.
