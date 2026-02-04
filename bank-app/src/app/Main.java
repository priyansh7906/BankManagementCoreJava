package app;

import java.util.Scanner;

import domain.Account;
import service.BankService;
import service.impl.BankServiceImpl;

@SuppressWarnings("all")
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankServiceImpl();
        boolean appRunning = true;
        System.out.println("welcome to Console Banking Application");
        System.out.println("---------------------------------------");
        while (appRunning) {
            System.out.println("""
                    1. Open Account
                    2. Deposit
                    3. Withdraw
                    4. Transfer
                    5. Statement
                    6. List Accounts
                    7. Search Account
                    0. Exit
                    """);
            System.out.print("Please select an option: ");
            int choice = scanner.nextInt();
            System.out.println("you selected option: " + choice);
            switch (choice) {
                case 1 -> openAccount(scanner, bankService);
                case 2 -> deposit(scanner, bankService);
                case 3 -> withdraw(scanner, bankService);
                case 4 -> transfer(scanner, bankService);
                case 5 -> Statement(scanner, bankService);
                case 6 -> listAccounts(scanner, bankService);
                case 7 -> searchAccount(scanner, bankService);
                case 0 -> {
                    appRunning = false;
                    System.out.println("Thank you for using our banking application. Goodbye!");
                }

            }
        }

    }
//method to open account
    private static void openAccount(Scanner scanner, BankService bankService) {
        System.out.println("Opening a new account...");
        System.out.print("Customer Name: ");
        String name = scanner.next().trim();
        System.out.print("Customer Email: ");
        String email = scanner.next().trim();
        System.out.print("Account Type (SAVINGS/CHECKING): ");
        String accountType = scanner.next().trim();
        System.out.print("Initial Deposit Amount: ");
        String amountStr = scanner.next().trim();
        Double initial = Double.valueOf(amountStr);
        String accountNumber =bankService.openAccount(name, email, accountType);
        if (initial>0) {
            bankService.deposit(accountNumber, initial, "Initial Deposit");
            System.out.println("Account opened successfully with account number: " + accountNumber + " and initial deposit of " + initial);  
        }
    }
// method to deposit
    private static void deposit(Scanner scanner, BankService bankService) {
    scanner.nextLine(); // clear any leftover newline from previous input

    System.out.print("Account number: ");
    String accountNumber = scanner.nextLine().trim(); // use nextLine() for safety

    System.out.print("Amount to deposit: ");
    while (!scanner.hasNextDouble()) { // validate numeric input
        System.out.print("Please enter a valid number: ");
        scanner.next(); // discard invalid input
    }
    double amount = scanner.nextDouble();
    scanner.nextLine(); // consume leftover newline

    bankService.deposit(accountNumber, amount, "Deposit"); // call your service method
    System.out.println("✅ Deposited successfully into account: " + accountNumber);
}

// method to withdraw
    private static void withdraw(Scanner scanner, BankService bankService) {
        scanner.nextLine(); // clear any leftover newline from previous input

    System.out.print("Account number: ");
    String accountNumber = scanner.nextLine().trim(); // use nextLine() for safety

    System.out.print("Amount to Withdraw : ");
    while (!scanner.hasNextDouble()) { // validate numeric input
        System.out.print("Please enter a valid number: ");
        scanner.next(); // discard invalid input
    }
    double amount = scanner.nextDouble();
    scanner.nextLine(); // consume leftover newline

    bankService.withdraw(accountNumber, amount, "withdraw"); // call your service method
    System.out.println("✅ withdraw successfully into account: " + accountNumber);
    }
// method to transfer
    private static void transfer(Scanner scanner, BankService bankService) {
        scanner.nextLine(); // clear any leftover newline from previous input
        System.out.println("From Account number: ");
        String fromAccountNumber = scanner.nextLine().trim(); // use nextLine() for safety
        System.out.println("To Account number: ");
        String toAccountNumber = scanner.nextLine().trim(); // use nextLine() for safety
        System.out.print("Amount to Transfer : ");
        while (!scanner.hasNextDouble()) { // validate numeric input
            System.out.print("Please enter a valid number: ");
            scanner.next(); // discard invalid input
        }
        double amount = Double.valueOf(scanner.nextLine().trim());
        bankService.transfer(fromAccountNumber, toAccountNumber, amount, toAccountNumber); // call your service method
        System.out.println("✅ transfer successfully from account: " + fromAccountNumber + " to account: " + toAccountNumber);
    }
//method to statement
    private static void Statement(Scanner scanner, BankService bankService) {
        scanner.nextLine(); // clear any leftover newline from previous input
        System.out.println("Account number: ");
        String account = scanner.nextLine().trim();
       bankService.getStatement(account).forEach(t -> {
    System.out.println(t.getDateTime() + " | " + t.getType() + " | " + t.getAmount() + " | " + t.getNote());
});

    }

    private static void listAccounts(Scanner scanner, BankService bankService) {
        bankService.listAccounts().forEach(a ->{
            System.out.println(a.getAccountNumber() + " | " + a.getAccountType() + " | " + a.getBalance());
        });
    }
// method to searchAccount
    private static void searchAccount(Scanner scanner, BankService bankService) {
         scanner.nextLine(); // clear any leftover newline from previous input
          System.out.println("Customer name contains: ");
        String q = scanner.nextLine().trim();
        bankService.searchAccountsByCustomerName(q).forEach(account ->
                System.out.println(account.getAccountNumber() + " | " + account.getAccountType() + " | " + account.getBalance())
        );
    }

}