package main.java;

/**
 * Enum of valid bill types and denominations.
 * 
 * @author james
 */
public enum BillType {
    TWENTY(20),
    TEN(10),
    FIVE(5),
    TWO(2),
    ONE(1);
    
    private int denomination;
    
    private BillType(final int denomination) {
        this.denomination = denomination;
    }
    
    public int getDenomination() {
        return denomination;
    }
    
    /**
     * @param amount to compare
     * @return true if the provided amount is less than this bill denomination.
     */
    public boolean isLessThanDenomination(final int amount) {
        return amount <  getDenomination();
    }
}
