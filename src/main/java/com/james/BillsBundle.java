package com.james;

/**
 * Object containing a bundle of bills of the same type.  For example, 2 $10 bills.
 * 
 * @author james
 */
public class BillsBundle {
    
    private BillType billType;
    private int numOfBills;
    
    // empty constructor purposefully disallowed
    
    /**
     * Constructs a bundle of bills of the same type/denomination with 0 of said bills.
     * 
     * @param billType
     */
    public BillsBundle(final BillType billType) {
        this.billType = billType;
    }
    
    /**
     * Constructs a bundle of bills of the same type/denomination with the provided number of said bills.
     * 
     * @param billType
     * @param initialNumOfBills
     */
    public BillsBundle(final BillType billType, final int initialNumOfBills) {
        this(billType);
        this.numOfBills = initialNumOfBills;
    }
    
    public BillType getBillType() {
        return billType;
    }
    
    public int getNumOfBills() {
        return numOfBills;
    }
    
    @Override
    public String toString() {
        return "Bills [billType=" + billType + ", numOfBills=" + numOfBills + "]";
    }
    
    
    /**
     * Validate in bound {@link BillsBundle} used for put and take.  If bad, SimpleException is thrown.  
     * Validation framework might be helpful instead.
     * 
     * @param bills
     * @throws SimpleException
     */
    private void validOrThrow(final BillsBundle bills) throws SimpleException {
        
        if(bills == null || 0 > bills.getNumOfBills()) {
            throw SimpleException.Error.InvalidAmount.getException();
        }
        
        if(billType != bills.getBillType()) {
            throw SimpleException.Error.MismatchDenomination.getException();
        }
        
        // success, made it
    }
    
    /**
     * Adds the provided number of bills of the same type/denomination to this bundle.
     * 
     * @param bills
     * @return new balance of number of bills
     * @throws SimpleException
     */
    public int put(final BillsBundle bills) throws SimpleException {
        
        validOrThrow(bills);
        
        return this.numOfBills += bills.getNumOfBills();
    }
    
    /**
     * Subtracts the provided number of bills of the same type/denomination from this bundle.
     * 
     * @param bills
     * @return new balance number of bills
     * @throws SimpleException
     */
    public int take(final BillsBundle bills) throws SimpleException {
        
        validOrThrow(bills);
        
        if(this.numOfBills < bills.getNumOfBills()) {
            throw SimpleException.Error.InsufficientFunds.getException();
        }
        
        return this.numOfBills -= bills.getNumOfBills();
    }
    
    /**
     * Finds the maximum available number of bills that this bundle can provide.  Nothing is taken from this bundle.  
     * Use {@link #take(BillsBundle)} to subtract from the bundle.
     * 
     * 
     * @param amount to change into available bills
     * @return the maximum number of available bills this contains in able to fulfill as much of the requested amount 
     * as possible.
     * @throws SimpleException 
     */
    public int getMaxAvailableChange(final int amount) throws SimpleException {
        
        if(amount < 0) {
            throw SimpleException.Error.InvalidAmount.getException();
        }
        
        if(billType.isLessThanDenomination(amount)) {
            return 0;
        }
        
        // integer division  gives only quotient, skipping any remainder/modulus
        int numBillsNeeded = amount / billType.getDenomination();
        
        if(numBillsNeeded <= numOfBills) {
            // have the bills
            return numBillsNeeded;
        } else {
            // greater need that available, return all of what we have
            return numOfBills;
        }
        
    }
    
    /**
     * @return total value of bills contained in this bundle.
     */
    public int getTotalValue() {
        return numOfBills * billType.getDenomination();
    }
}
