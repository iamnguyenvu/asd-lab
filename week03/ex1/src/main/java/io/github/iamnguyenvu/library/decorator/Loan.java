package io.github.iamnguyenvu.library.decorator;

import lombok.Getter;

import java.time.LocalDate;

/**
 * DECORATOR PATTERN - Loan Enhancements
 */

// Component Interface
public interface Loan {
    int getLoanDuration(); // days
    double getFee();
    String getDescription();
    LocalDate calculateDueDate(LocalDate startDate);
}

/**
 * Concrete Component - Base Loan
 */
@Getter
class BaseLoan implements Loan {
    private static final int BASE_DURATION = 14; // 2 weeks
    private static final double BASE_FEE = 0.0;

    @Override
    public int getLoanDuration() {
        return BASE_DURATION;
    }

    @Override
    public double getFee() {
        return BASE_FEE;
    }

    @Override
    public String getDescription() {
        return "Standard Loan (14 days, Free)";
    }

    @Override
    public LocalDate calculateDueDate(LocalDate startDate) {
        return startDate.plusDays(BASE_DURATION);
    }
}

/**
 * Decorator Base Class
 */
abstract class LoanDecorator implements Loan {
    protected Loan wrappedLoan;

    public LoanDecorator(Loan loan) {
        this.wrappedLoan = loan;
    }

    @Override
    public int getLoanDuration() {
        return wrappedLoan.getLoanDuration();
    }

    @Override
    public double getFee() {
        return wrappedLoan.getFee();
    }

    @Override
    public String getDescription() {
        return wrappedLoan.getDescription();
    }

    @Override
    public LocalDate calculateDueDate(LocalDate startDate) {
        return wrappedLoan.calculateDueDate(startDate);
    }
}

/**
 * Concrete Decorator - Extended Loan
 */
class ExtendedLoan extends LoanDecorator {
    private static final int EXTRA_DAYS = 7;
    private static final double EXTRA_FEE = 2.0;

    public ExtendedLoan(Loan loan) {
        super(loan);
    }

    @Override
    public int getLoanDuration() {
        return wrappedLoan.getLoanDuration() + EXTRA_DAYS;
    }

    @Override
    public double getFee() {
        return wrappedLoan.getFee() + EXTRA_FEE;
    }

    @Override
    public String getDescription() {
        return wrappedLoan.getDescription() + " + Extended (+7 days, +$2)";
    }

    @Override
    public LocalDate calculateDueDate(LocalDate startDate) {
        return wrappedLoan.calculateDueDate(startDate).plusDays(EXTRA_DAYS);
    }
}

/**
 * Concrete Decorator - Premium Loan
 */
class PremiumLoan extends LoanDecorator {
    private static final int EXTRA_DAYS = 30;
    private static final double EXTRA_FEE = 5.0;

    public PremiumLoan(Loan loan) {
        super(loan);
    }

    @Override
    public int getLoanDuration() {
        return wrappedLoan.getLoanDuration() + EXTRA_DAYS;
    }

    @Override
    public double getFee() {
        return wrappedLoan.getFee() + EXTRA_FEE;
    }

    @Override
    public String getDescription() {
        return wrappedLoan.getDescription() + " + Premium (+30 days, +$5)";
    }

    @Override
    public LocalDate calculateDueDate(LocalDate startDate) {
        return wrappedLoan.calculateDueDate(startDate).plusDays(EXTRA_DAYS);
    }
}

/**
 * Concrete Decorator - Digital Access
 */
class DigitalAccessLoan extends LoanDecorator {
    private static final double EXTRA_FEE = 1.5;

    public DigitalAccessLoan(Loan loan) {
        super(loan);
    }

    @Override
    public double getFee() {
        return wrappedLoan.getFee() + EXTRA_FEE;
    }

    @Override
    public String getDescription() {
        return wrappedLoan.getDescription() + " + Digital Access (+$1.5)";
    }
}
