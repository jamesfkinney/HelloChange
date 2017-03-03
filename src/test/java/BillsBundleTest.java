package test.java;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import main.java.BillType;
import main.java.BillsBundle;
import main.java.SimpleException;

public class BillsBundleTest {
    
    private BillsBundle nullOnes;
    private BillsBundle negOnes;
    private BillsBundle zeroOnes;
    private BillsBundle fiveOnes;
    private BillsBundle tenOnes;
    
    private BillsBundle fourTens;

    @Before
    public void setUp() throws Exception {
        negOnes = new BillsBundle(BillType.ONE, -1);
        zeroOnes = new BillsBundle(BillType.ONE);
        fiveOnes = new BillsBundle(BillType.ONE, 5);
        tenOnes = new BillsBundle(BillType.ONE, 10);
        
        fourTens = new BillsBundle(BillType.TEN, 4);
    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    
    
    @Test
    public void testPut_InvalidAmountByNull() throws SimpleException {        
        thrown.expectMessage(SimpleException.Error.InvalidAmount.getMessage());
        fiveOnes.put(nullOnes);
    }
    
    @Test
    public void testPut_InvalidAmountByNegative() throws SimpleException {        
        thrown.expectMessage(SimpleException.Error.InvalidAmount.getMessage());
        fiveOnes.put(negOnes);
    }
    
    @Test
    public void testPut_MismatchDenomination() throws SimpleException {
        BillsBundle twos = new BillsBundle(BillType.TWO, 5);
        
        thrown.expectMessage(SimpleException.Error.MismatchDenomination.getMessage());
        fiveOnes.put(twos);
    }
    
    @Test
    public void testPut_validZero() throws SimpleException {        
        assertEquals(5, fiveOnes.getNumOfBills()); // sanity
        fiveOnes.put(zeroOnes);
        assertEquals(5, fiveOnes.getNumOfBills());
    }
    
    @Test
    public void testPut_validPositiveNumber() throws SimpleException {
        assertEquals(5, fiveOnes.getNumOfBills()); // sanity
        fiveOnes.put(tenOnes);
        assertEquals(15, fiveOnes.getNumOfBills());
    }
    
    
    
    @Test
    public void testTake_InvalidAmountBundleByNull() throws SimpleException {
        thrown.expectMessage(SimpleException.Error.InvalidAmount.getMessage());
        fiveOnes.take(null);
    }
    
    @Test
    public void testTake_InvalidAmountByNegative() throws SimpleException {
        thrown.expectMessage(SimpleException.Error.InvalidAmount.getMessage());
        fiveOnes.take(negOnes);
    }
    
    @Test
    public void testTake_MismatchDenomination() throws SimpleException {
        BillsBundle twos = new BillsBundle(BillType.TWO, 5);
        
        thrown.expectMessage(SimpleException.Error.MismatchDenomination.getMessage());
        fiveOnes.take(twos);
    }
    
    @Test
    public void testTake_InsufficientFunds() throws SimpleException {
        thrown.expectMessage(SimpleException.Error.InsufficientFunds.getMessage());
        fiveOnes.take(tenOnes);
    }
    
    @Test
    public void testTake_validZero() throws SimpleException {
        assertEquals(5, fiveOnes.getNumOfBills()); // sanity
        fiveOnes.take(zeroOnes);
        assertEquals(5, fiveOnes.getNumOfBills());
    }
    
    @Test
    public void testTake_validPositiveNumber() throws SimpleException {
        assertEquals(10, tenOnes.getNumOfBills()); // sanity
        tenOnes.take(fiveOnes);
        assertEquals(5, fiveOnes.getNumOfBills());
    }
    
    
    
    @Test
    public void testGetMaxAvailableChange_validZero() throws SimpleException {
        assertEquals(0, fourTens.getMaxAvailableChange(0));
    }
    
    @Test
    public void testGetMaxAvailableChange_InvalidAmountByNegative() throws SimpleException {
        thrown.expectMessage(SimpleException.Error.InvalidAmount.getMessage());
        assertEquals(0, fourTens.getMaxAvailableChange(-40));
    }
    
    @Test
    public void testGetMaxAvailableChange_validLessThanDenomination() throws SimpleException {
        assertEquals(0, fourTens.getMaxAvailableChange(2));
    }
    
    @Test
    public void testGetMaxAvailableChange_validPositiveNumber() throws SimpleException {
        assertEquals(1, fourTens.getMaxAvailableChange(10));
        assertEquals(2, fourTens.getMaxAvailableChange(20));
        assertEquals(3, fourTens.getMaxAvailableChange(30));
        assertEquals(4, fourTens.getMaxAvailableChange(40));
        
        // more than have
        assertEquals(4, fourTens.getMaxAvailableChange(50));
        
        // remainder
        assertEquals(2, fourTens.getMaxAvailableChange(21));
        
        // more than have and remainder
        assertEquals(4, fourTens.getMaxAvailableChange(43));
    }
    
    
    
    @Test
    public void testGetTotalValue_validZero() {
        assertEquals(0, zeroOnes.getTotalValue());
    }
    
    @Test
    public void testGetTotalValue_validPositiveNumber() {
        assertEquals(5, fiveOnes.getTotalValue());
        
        assertEquals(40, fourTens.getTotalValue());
    }
}
