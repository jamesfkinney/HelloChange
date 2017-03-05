package com.james;

/**
 * Simple, no frills exception used throughout application.
 * 
 * @author james
 */
public class SimpleException extends Exception {

    /**
     * Randomly generated serial UID
     */
    private static final long serialVersionUID = -7682813254011761284L;
    
    public enum Error {
        InsufficientFunds("Sorry - Insufficient Funds"),
        InvalidAmount("Sorry - Invalid Amount"), 
        MismatchDenomination("Sorry - Mismatched Denominations"), 
        InvalidCommand("Sorry - Invalid Command");
        
        private String message;
        
        private Error(final String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return this.message;
        }
        
        public SimpleException getException() {
            return new SimpleException(this);
        }
    }
    
    public SimpleException(Error error) {
        super(error.getMessage());
    }

}
