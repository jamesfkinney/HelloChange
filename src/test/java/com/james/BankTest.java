package com.james;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.james.Bank;
import com.james.SimpleException;

public class BankTest {
    
    private Bank nonZeroBank;
    private Bank nonZeroAssignmentBank;
    private Bank zeroBank;
   

    @Before
    public void setUp() throws Exception {
        nonZeroBank = new Bank(2, 2, 2, 2, 2);
        nonZeroAssignmentBank = new Bank(1, 2, 3, 4, 5);
        zeroBank = new Bank();
    }

    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    
    
    @Test
    public void testShow_validEmptyZero() {
        assertEquals("$0 0 0 0 0 0", zeroBank.show());
    }
    
    @Test
    public void testShow_validValues() {
        assertEquals("$76 2 2 2 2 2", nonZeroBank.show());
        
        assertEquals("$68 1 2 3 4 5", nonZeroAssignmentBank.show());
    }
    
    
    
    @Test
    public void testPut_InvalidAmountByNull() throws SimpleException {
        thrown.expectMessage(SimpleException.Error.InvalidAmount.getMessage());
        nonZeroBank.put(null);
    }
    
    @Test
    public void testPut_validZero() throws SimpleException {
        Bank put0 = new Bank();
        nonZeroBank.put(put0);
        assertEquals("$76 2 2 2 2 2", nonZeroBank.show());
    }
    
    @Test
    public void testPut_validValues() throws SimpleException {
        Bank put38 = new Bank(1, 1, 1, 1, 1);
        nonZeroBank.put(put38);
        assertEquals("$114 3 3 3 3 3", nonZeroBank.show());
        
        Bank put12 = new Bank(0, 1, 0, 1, 0);
        nonZeroBank.put(put12);
        assertEquals("$126 3 4 3 4 3", nonZeroBank.show());
        
        Bank put26 = new Bank(1, 0, 1, 0, 1);
        nonZeroBank.put(put26);
        assertEquals("$152 4 4 4 4 4", nonZeroBank.show());
    }
    
    
    
    @Test
    public void testTake_InvalidAmountByNull() throws SimpleException {
        thrown.expectMessage(SimpleException.Error.InvalidAmount.getMessage());
        nonZeroBank.take(null);
    }
    
    @Test
    public void testTake_InsufficientFunds() throws SimpleException {
        thrown.expectMessage(SimpleException.Error.InsufficientFunds.getMessage());
        Bank take38 = new Bank(1, 1, 1, 1, 1);
        zeroBank.take(take38);
    }
    
    @Test
    public void testTake_validZero() throws SimpleException {
        Bank take0 = new Bank();
        nonZeroBank.take(take0);
        assertEquals("$76 2 2 2 2 2", nonZeroBank.show());
    }
    
    @Test
    public void testTake_validValues() throws SimpleException {
        Bank take38 = new Bank(1, 1, 1, 1, 1);
        nonZeroBank.take(take38);
        assertEquals("$38 1 1 1 1 1", nonZeroBank.show());
        
        Bank take12 = new Bank(0, 1, 0, 1, 0);
        nonZeroBank.take(take12);
        assertEquals("$26 1 0 1 0 1", nonZeroBank.show());
        
        Bank take26 = new Bank(1, 0, 1, 0, 1);
        nonZeroBank.take(take26);
        assertEquals("$0 0 0 0 0 0", nonZeroBank.show());
    }
    
    
    
    @Test
    public void testChange_InvalidAmountByNegative() throws SimpleException {
        thrown.expectMessage(SimpleException.Error.InvalidAmount.getMessage());
        nonZeroBank.change(-1);
    }
    
    @Test
    public void testChange_InsufficientFunds() throws SimpleException {
        thrown.expectMessage(SimpleException.Error.InsufficientFunds.getMessage());
        nonZeroBank.change(999999);
    }
    
    @Test
    public void testChange_validZero() throws SimpleException {
        assertEquals("$0 0 0 0 0 0", nonZeroBank.change(0).show());
    }
    
    @Test
    public void testChange_validExactValues() throws SimpleException {
        
        assertEquals("$20 1 0 0 0 0", nonZeroBank.change(20).show());
        
        assertEquals("$10 0 1 0 0 0", nonZeroBank.change(10).show());
        
        assertEquals("$5 0 0 1 0 0", nonZeroBank.change(5).show());
        
        assertEquals("$2 0 0 0 1 0", nonZeroBank.change(2).show());
        
        assertEquals("$1 0 0 0 0 1", nonZeroBank.change(1).show());
    }
    
    @Test
    public void testChange_validNonExactValues() throws SimpleException {
        
        assertEquals("$59 2 1 1 2 0", nonZeroBank.change(59).show());
        
        assertEquals("$38 1 1 1 1 1", nonZeroBank.change(38).show());
        
        assertEquals("$14 0 1 0 2 0", nonZeroBank.change(14).show());
    }
    
    @Test
    public void testChange_validNonExactValueWithNonStandardDenomination() throws SimpleException {
        
        Bank bank43 = new Bank(1, 0, 3, 4, 0);
        assertEquals("$11 0 0 1 3 0", bank43.change(11).show());
        
    }
    
    @Test
    public void testPerChallengePDF() throws SimpleException {
        
        assertEquals("$68 1 2 3 4 5", nonZeroAssignmentBank.show());
        
        nonZeroAssignmentBank.put(new Bank(1, 2, 3, 0, 5));
        assertEquals("$128 2 4 6 4 10", nonZeroAssignmentBank.show());
        
        nonZeroAssignmentBank.take(new Bank(1, 4, 3, 0, 10));
        assertEquals("$43 1 0 3 4 0", nonZeroAssignmentBank.show());
        
        Bank toTakeChange = nonZeroAssignmentBank.change(11);
        assertEquals("$11 0 0 1 3 0", toTakeChange.show());
        nonZeroAssignmentBank.take( toTakeChange );
        assertEquals("$32 1 0 2 1 0", nonZeroAssignmentBank);
        
        thrown.expectMessage(SimpleException.Error.InsufficientFunds.getMessage());
        nonZeroAssignmentBank.change(14);
        
    }
    
    @Test 
    public void testGetTotalValue_validZero() {
        assertEquals(0, zeroBank.getTotalValue());
    }
    
    @Test
    public void testGetTotalValue_validPositiveNumber() {
        assertEquals(68, nonZeroAssignmentBank.getTotalValue());
        
        assertEquals(76, nonZeroBank.getTotalValue());
    }
    
}
