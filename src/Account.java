public class Account {
    private String accountNum;
    private String userName;
    private String password;
    private double balance ;
    private double withdrawalLimit;

    public Account() {
    }

    public Account(String accountNum, String userName, String password, double balance, double withdrawalLimit) {
        this.accountNum = accountNum;
        this.userName = userName;
        this.password = password;
        this.balance = balance;
        this.withdrawalLimit = withdrawalLimit;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(double withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }
}
