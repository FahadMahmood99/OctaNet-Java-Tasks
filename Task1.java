import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Account {
    private String userId;
    private String pin;
    private double balance;
    private List<String> transactionHistory;

    public Account(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add("Transferred: $" + amount + " to " + recipient.getUserId());
            recipient.balance += amount;
            recipient.transactionHistory.add("Received: $" + amount + " from " + this.userId);
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
        System.out.println("Current Balance: $" + balance);
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }
}

class ATM {
    private Map<String, Account> users;

    public ATM() {
        this.users = new HashMap<>();
    }

    public void add_user(String userId, String pin) {
        if (!users.containsKey(userId)) {
            users.put(userId, new Account(userId, pin, 0));
            System.out.println("Account created successfully!");
        } else {
            System.out.println("User ID already exists. Please choose a different one.");
        }
    }

    public Account authenticate_user(String userId, String pin) {
        if (users.containsKey(userId) && users.get(userId).getPin().equals(pin)) {
            System.out.println("Authentication successful!");
            return users.get(userId);
        } else {
            System.out.println("Invalid user ID or PIN.");
            return null;
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Create Account\n2. Log In\n3. Quit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Enter User ID: ");
                String userId = scanner.nextLine();
                System.out.print("Enter PIN: ");
                String pin = scanner.nextLine();
                atm.add_user(userId, pin);
            } else if (choice.equals("2")) {
                System.out.print("Enter User ID: ");
                String userId = scanner.nextLine();
                System.out.print("Enter PIN: ");
                String pin = scanner.nextLine();
                Account user = atm.authenticate_user(userId, pin);
                if (user != null) {
                    while (true) {
                        System.out.println("\n1. Deposit\n2. Withdraw\n3. Transfer\n4. Transaction History\n5. Quit");
                        System.out.print("Enter your choice: ");
                        String option = scanner.nextLine();
                        if (option.equals("1")) {
                            System.out.print("Enter amount to deposit: ");
                            double amount = Double.parseDouble(scanner.nextLine());
                            user.deposit(amount);
                        } else if (option.equals("2")) {
                            System.out.print("Enter amount to withdraw: ");
                            double amount = Double.parseDouble(scanner.nextLine());
                            user.withdraw(amount);
                        } else if (option.equals("3")) {
                            System.out.print("Enter recipient's User ID: ");
                            String recipientId = scanner.nextLine();
                            Account recipient = atm.users.get(recipientId);
                            if (recipient != null) {
                                System.out.print("Enter amount to transfer: ");
                                double amount = Double.parseDouble(scanner.nextLine());
                                user.transfer(recipient, amount);
                            } else {
                                System.out.println("Recipient not found.");
                            }
                        } else if (option.equals("4")) {
                            user.displayTransactionHistory();
                        } else if (option.equals("5")) {
                            break;
                        } else {
                            System.out.println("Invalid choice.");
                        }
                    }
                }
            } else if (choice.equals("3")) {
                System.out.println("Thank you for using the ATM. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }
}
