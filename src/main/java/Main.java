package main.java;

import java.io.Console;
import java.util.Scanner;

/**
 * Command line execution harness for HelloChange
 * 
 * This is a simple application with a flat package structure and simple exception handling attempting to efficiently 
 * make change (aka change-making problem).
 * 
 * @author james
 */
public class Main {
    
    public static enum VALID_CMDS {
        show,
        put,
        take,
        change,
        quit
    };
    
    public static void main(final String args[]) {
        Bank register = new Bank();
        
        System.out.println("ready");
        
        
        Console console = System.console();
        if (console == null) {
            System.out.println("No console: non-interactive mode");
            System.exit(0);
        }
        
        boolean isExit = false;
        
        Scanner scanner = new Scanner(console.reader());
        
        while(!isExit) {
            
            try {
                
                switch (VALID_CMDS.valueOf(scanner.next())) {
                    case show:
                        doShow(scanner, register);
                        break;
                        
                    case put:
                        doPut(scanner, register);
                        doShow(scanner, register);
                        break;
                        
                    case take:
                        doTake(scanner, register);
                        doShow(scanner, register);
                        break;
                        
                    case change:
                        doChange(scanner, register);
                        doShow(scanner, register);
                        break;
                        
                    case quit:
                        isExit = true;
                        break;
    
                    default:
                        throw SimpleException.Error.InvalidCommand.getException();
                }
                                
            } catch(SimpleException e) {
                System.out.println(e.getMessage());
            } catch (Exception t) {
                t.printStackTrace(System.out);
            }
        }
        
        System.exit(0);
    }
    
    private static Bank generateBankFromScanner (final Scanner scanner) throws SimpleException {
        int numTwenties = scanner.nextInt();
        int numTens = scanner.nextInt();
        int numFives = scanner.nextInt();
        int numTwos = scanner.nextInt();
        int numOnes = scanner.nextInt();
        
        return new Bank(
                numTwenties,
                numTens,
                numFives,
                numTwos,
                numOnes
                );
    }
    
    private static void doShow(final Scanner scanner, final Bank register) {
        System.out.println(register.show());
    }
    
    private static void doPut(final Scanner scanner, final Bank register) throws SimpleException {;
        Bank billsToPut = generateBankFromScanner(scanner);
        register.put(billsToPut);
    }

    private static void doTake(final Scanner scanner, final Bank register) throws SimpleException {
        Bank billsToTake = generateBankFromScanner(scanner);
        register.take(billsToTake);
    }

    private static void doChange(final Scanner scanner, final Bank register) throws SimpleException {
        int amountToChange = scanner.nextInt();
        Bank billsToChangeAndTake = register.change(amountToChange);
        register.take(billsToChangeAndTake);
    }
    
}
