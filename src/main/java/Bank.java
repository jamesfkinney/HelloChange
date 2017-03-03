package main.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Holds a {@link BillsBundle} of each {@link BillType}.  This holding can be used as a bank or an individual 
 * transaction.
 * 
 * @author james
 */
public class Bank {
    
    private Map<BillType, BillsBundle> billsMap;
    
    /**
     * Constructs an empty bank of bills with a value of $0
     */
    public Bank() {
        billsMap = new HashMap<>();
        Arrays.stream(BillType.values())
            .forEach(billType -> billsMap.put(billType, new BillsBundle(billType)));
    }
    
    /**
     * Constructs a bank with the provided number of {@link BillsBundle} per type/denomination.
     * 
     * @param numTwenties
     * @param numTens
     * @param numFives
     * @param numTwos
     * @param numOnes
     */
    public Bank(final int numTwenties, final int numTens, final int numFives, final int numTwos, final int numOnes) {
        this();
        
        setBills( new BillsBundle(BillType.TWENTY, numTwenties) );
        setBills( new BillsBundle(BillType.TEN, numTens) );
        setBills( new BillsBundle(BillType.FIVE, numFives) );
        setBills( new BillsBundle(BillType.TWO, numTwos) );
        setBills( new BillsBundle(BillType.ONE, numOnes) );
    }
    
    public BillsBundle getBills(final BillType billType) {
        return billsMap.get(billType);
    }
    
    public void setBills(final BillsBundle bills) {
        billsMap.put(bills.getBillType(), bills);
    }
    
    @Override
    public String toString() {
        return "Bank [billsMap=" + billsMap + "]";
    }
    
    
    
    /**
     * Validate in bound {@link Bank} used for put and take.  If bad, SimpleException is thrown.  
     * Validation framework might be helpful instead.
     * 
     * @param bank
     * @throws SimpleException
     */
    private void validOrThrow(final Bank bank) throws SimpleException {
        
        if(bank == null) {
            throw SimpleException.Error.InvalidAmount.getException();
        }
        
        // success, made it
    }
    
    /**
     * @return string representation of this bank in the format: $XX #$20s #$10s #$5s #$2s #$1s
     */
    public String show() {
        
        String delimiter = " ";
        String prefix = "$" + getTotalValue() + " ";
        String suffix = "";
        
        return Arrays.stream(BillType.values())
                .map(billType -> String.valueOf( billsMap.get(billType).getNumOfBills() ))
                .collect(Collectors.joining(delimiter, prefix, suffix));
    }
    
    /**
     * Adds the passed bank to this bank. 
     * 
     * @param bankToPut
     * @throws SimpleException
     */
    public void put(final Bank bankToPut) throws SimpleException {
        
        validOrThrow(bankToPut);
        
        for(BillType billType : BillType.values()) {
            BillsBundle iterBundle = bankToPut.getBills(billType);
            billsMap.get(billType).put(iterBundle);
        }
        
    }
    
    /**
     * Subtracts the passed bank from this bank.
     * 
     * @param bankToTake
     * @throws SimpleException
     */
    public void take(final Bank bankToTake) throws SimpleException {
        
        validOrThrow(bankToTake);
        
        for(BillType billType : BillType.values()) {
            BillsBundle iterBundle = bankToTake.getBills(billType);
            billsMap.get(billType).take(iterBundle);
        }
    }
    
    /**
     * Returns a new {@link Bank} of the change amount requested.  Nothing is taken from this bank.  Use 
     * {@link #take(Bank)} to subtract from this bank.
     * 
     * Change making attempt start at largest bill type/denomination and works down with remaining needed amount.
     * 
     * @param amount
     * @return
     * @throws SimpleException
     */
    public Bank change(final int amount) throws SimpleException {
        
        if(amount < 0) {
            throw SimpleException.Error.InvalidAmount.getException();
        }
        
        Bank bankToTake = new Bank();
        int amountStillNeeded = amount;
        
        // no lambda since throws exception
        for(BillType billType : BillType.values()) {
            BillsBundle iterBills = billsMap.get(billType);
            int billTypeDenomToTake = iterBills.getMaxAvailableChange(amountStillNeeded);
            
            BillsBundle billsForChange = new BillsBundle(billType, billTypeDenomToTake);
            bankToTake.setBills(billsForChange);
            
            amountStillNeeded -= billsForChange.getTotalValue();
        }
        
        if(amountStillNeeded > 0) {
            throw SimpleException.Error.InsufficientFunds.getException();
        }
        
        return bankToTake;
    }
    
    /**
     * @return total value of bills contained in this bank.
     */
    public int getTotalValue() {
        return billsMap.values().stream()
                .map(bills -> bills.getTotalValue())
                .reduce(0, (total, nextBills) -> total + nextBills);
    }
    
}
