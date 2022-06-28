import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable {

    private String username;
    private String name;
    private String passwords;
    private double accountBalance;
    private String accountNumber;
    static List<Client> clientsList = new ArrayList<>();
    public Client(){

    }

    public Client(String name, String accountNumber, String username, String passwords,double accountBalance) {
        setAccountNumber(accountNumber);
        setName(name);
        setUsername(username);
        setPasswords(passwords);
        setAccountBalance(accountBalance);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getUsername() {
        return username;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void showInfo() {
        System.out.println("Name: "+ getName());
        System.out.println("Account ID: "+ getAccountNumber());
        System.out.println("Username: "+ getUsername());
        System.out.println("Password: "+ getPasswords());
        System.out.println("Account Balance: "+getAccountBalance());
    }
}
