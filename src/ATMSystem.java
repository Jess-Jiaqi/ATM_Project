
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATMSystem {
    public static void main(String[] args) {
        ArrayList<Account> accounts = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("=============== ATM System ===============");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");

            System.out.println("Please select: ");
            int command = sc.nextInt();

            switch (command) {
                case 1:
                    login(accounts, sc);
                    break;
                case 2:
                    register(accounts, sc);
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }

    private static void login(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("=============== Login ===============");
        if (accounts.isEmpty()) {
            System.out.println("Account doesn't exist. Please sign up first.");
            return;
        }

        while (true) {
            System.out.println("Please enter your account number: ");
            String accNum = sc.next();

            Account acc = getAccByNum(accNum, accounts);

            if (acc != null) {
                while (true) {
                    System.out.println("Please enter your password: ");
                    String password = sc.next();
                    if (acc.getPassword().equals(password)) {
                        System.out.println("Login successful. Hello, " + acc.getUserName() + "!");
                        showUserCommand(acc, sc, accounts);
                        return;
                    } else {
                        System.out.println("Password doesn't match.");
                    }
                }
            } else {
                System.out.println("Account doesn't exist.");
            }
        }
    }

    private static void showUserCommand(Account acc, Scanner sc, ArrayList<Account> accounts) {
        while (true) {
            System.out.println("=============== " + acc.getUserName() + " ===============");
            System.out.println("1. Account Info");
            System.out.println("2. Deposit");
            System.out.println("3. Withdrawal");
            System.out.println("4. Transfer");
            System.out.println("5. Change password");
            System.out.println("6. Logout");
            System.out.println("7. Close account");
            System.out.println("Please select: ");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    showAccountInfo(acc);
                    break;
                case 2:
                    deposit(acc, sc);
                    break;
                case 3:
                    withdrawal(acc, sc);
                    break;
                case 4:
                    transfer(acc,accounts,sc);
                    break;
                case 5:
                    changePassword(acc, sc);
                    return;
                case 6:
                    System.out.println("Logout successful!");
                    return;
                case 7:
                    if(closeAcc(acc, accounts, sc)) return;
                    else break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }

    private static boolean closeAcc(Account acc, ArrayList<Account> accounts, Scanner sc) {
        System.out.println("=============== Close Account ===============");
        System.out.println("Are your sure you want to close this account? y/n");
        String result = sc.next();
        switch (result){
            case"y":
                accounts.remove(acc);
                System.out.println("Successful! Good bye!");
                return true;
            default:
                System.out.println("Ok. Remain this account.");
        }
        return false;
    }

    private static void changePassword(Account acc, Scanner sc) {
        System.out.println("=============== Change Password ===============");
        while (true) {
            System.out.println("Please enter your current password: ");
            String password = sc.next();

            if (password.equals(acc.getPassword())){
                while (true) {
                    System.out.println("Please enter your new password.");
                    String newPassword = sc.next();
                    System.out.println("Please confirm your new password.");
                    String confirmPassword = sc.next();

                    if(confirmPassword.equals(newPassword)){
                        acc.setPassword(confirmPassword);
                        System.out.println("Successful!");
                        return;
                    }else {
                        System.out.println("Password didn't match. Please reenter.");
                    }
                }

            }else {
                System.out.println("Incorrect password.");
            }
        }
    }

    private static void transfer(Account acc, ArrayList<Account> accounts, Scanner sc) {
        System.out.println("=============== Transfer ===============");

        if(accounts.size() < 2){
            System.out.println("The system doesn't have other accounts. Please create one first.");
            return;
        }

        if(acc.getBalance() == 0){
            System.out.println("You don't have money to transfer.");
            return;
        }

        while (true) {
            System.out.println("Please enter account number: ");
            String accNum = sc.next();

            if (accNum.equals(acc.getAccountNum())){
                System.out.println("You can't transfer money to yourself.");
                continue;
            }

            Account account = getAccByNum(accNum, accounts);
            if (account == null){
                System.out.println("This account number doesn't exist.");
            }else {
                System.out.println("Please enter the name of this account holder");
                String holder = sc.next();

                if(holder.equals(account.getUserName())){
                    while (true) {
                        System.out.println("Please enter transfer amount: ");
                        double trans = sc.nextDouble();
                        if(trans > acc.getBalance()){
                            System.out.println("Sorry, your balance is insufficient, you can transfer up to $" + acc.getBalance()+ ".");
                        }else {
                            acc.setBalance(acc.getBalance() - trans);
                            account.setBalance(account.getBalance() + trans);
                            System.out.println("Transfer successful. You have transferred $" +trans+" to " + account.getUserName() + ".");
                            showAccountInfo(acc);
                            return;
                        }
                    }
                }else {
                    System.out.println("That's not the holder of this account.");
                }
            }

        }

    }

    private static void withdrawal(Account acc, Scanner sc) {
        System.out.println("=============== Withdrawal ===============");

        if (acc.getBalance() < 100) {
            System.out.println("Your balance is less than $100. Can not withdrawal money.");
            return;
        }

        while (true) {
            System.out.println("Please enter amount: ");
            double amount = sc.nextDouble();

            if (amount > acc.getWithdrawalLimit()) {
                System.out.println("Exceeding withdrawal limit. Your withdrawal limit is " + acc.getWithdrawalLimit());
            } else {
                if (amount > acc.getBalance()) {
                    System.out.println("Insufficient balance. Your balance is " + acc.getBalance());
                } else {
                    System.out.println("Withdrawal successful.Total withdrawn: " + amount);
                    acc.setBalance(acc.getBalance() - amount);
                    showAccountInfo(acc);
                    return;
                }
            }
        }
    }

    private static void deposit(Account acc, Scanner sc) {
        System.out.println("=============== Deposit ===============");
        System.out.println("Please enter amount: ");
        double amount = sc.nextDouble();

        acc.setBalance(acc.getBalance() + amount);
        System.out.println("Deposit successful! More information:");
        showAccountInfo(acc);
    }

    private static void showAccountInfo(Account acc) {
        System.out.println("=============== Account Info ===============");
        System.out.println("Account number: " + acc.getAccountNum());
        System.out.println("User name: " + acc.getUserName());
        System.out.println("Balance: " + acc.getBalance());
        System.out.println("Withdrawal limit: " + acc.getWithdrawalLimit());

    }

    private static void register(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("=============== Register ===============");
        Account account = new Account();

        System.out.println("Please enter username: ");
        String userName = sc.next();
        account.setUserName(userName);

        while (true) {
            System.out.println("Please enter password: ");
            String password = sc.next();
            System.out.println("Please confirm your password: ");
            String confirmPassword = sc.next();

            if (confirmPassword.equals(password)) {
                account.setPassword(confirmPassword);
                break;
            } else {
                System.out.println("Password doesn't match. Please set again.");
            }
        }

        System.out.println("Please enter withdrawal limit: ");
        double withdrawalLimit = sc.nextDouble();
        account.setWithdrawalLimit(withdrawalLimit);

        String accountNum = getRandomAccNum(accounts);
        account.setAccountNum(accountNum);

        accounts.add(account);
        System.out.println("Congratulation! You opened an account. Your account number is " + accountNum + ".");
        
    }

    private static String getRandomAccNum(ArrayList<Account> accounts) {
        Random r = new Random();

        while (true) {
            String accountNum = "";

            for (int i = 0; i < 8; i++) {
                accountNum += r.nextInt(10);
            }
            Account acc = getAccByNum(accountNum, accounts);
            if (acc == null) {
                return accountNum;
            }
        }
    }

    private static Account getAccByNum(String accountNum, ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getAccountNum().equals(accountNum)) {
                return acc;
            }
        }
        return null;
    }
}
