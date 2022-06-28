import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Queries {
    private String name;
    private String username;
    private String accountNumber;
    private String password;
    private double accountBalance;

    public Queries(Client client) {
        setAccountNumber(client.getAccountNumber());
        setPassword(client.getPasswords());
        setUsername(client.getUsername());
        setName(client.getName());
        setAccountBalance(client.getAccountBalance());
    }

    public Queries(){}

    public PreparedStatement addClient() throws SQLException {
        PreparedStatement statement = NewServer.connection.prepareStatement("INSERT INTO clients(account_number, name, username, password) " +
                "VALUES(?, ?, ?, ?)");
        statement.setString(1,getAccountNumber());
        statement.setString(2,getName());
        statement.setString(3,getUsername());
        statement.setString(4,getPassword());
        return statement;
    }

    public ResultSet sendClientData(String username) throws SQLException {
        PreparedStatement statement = NewServer.connection.prepareStatement("SELECT * FROM clients WHERE username = ?");
        statement.setString(1,username);
        return statement.executeQuery();
    }

    public boolean validateLogin(String username, String password) throws SQLException {
        PreparedStatement statement = NewServer.connection.prepareStatement("SELECT username, password FROM clients WHERE username = ? AND password = ?");
        statement.setString(1,username);
        statement.setString(2,password);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public boolean validateSignup(String username, String password, String confirmPassword, String accountNumber) throws SQLException {
        if (password.equals(confirmPassword)) {
            PreparedStatement statement = NewServer.connection.prepareStatement("SELECT username, account_number FROM clients WHERE username =? OR account_number = ?");
            statement.setString(1,username);
            statement.setString(2,accountNumber);
            ResultSet resultSet = statement.executeQuery();
            return (!resultSet.next());
        }
        return false;
    }

    public void getCash(String accountNumber, double amount) throws SQLException {
        PreparedStatement statement = NewServer.connection.prepareStatement("UPDATE clients SET account_balance = account_balance - ? WHERE account_number = ?");
        statement.setDouble(1,amount);
        statement.setString(2,accountNumber);
        statement.executeUpdate();
    }

    public void deposit(String accountNumber, double amount) throws SQLException {
        PreparedStatement statement = NewServer.connection.prepareStatement("UPDATE clients SET account_balance = account_balance + ? WHERE account_number = ?");
        statement.setDouble(1,amount);
        statement.setString(2,accountNumber);
        statement.executeUpdate();
    }

    public void transfer(String destinationAccountNumber, String accountNumber, double amount) throws SQLException {
        PreparedStatement decrease = NewServer.connection.prepareStatement("UPDATE clients SET account_balance = account_balance - ? WHERE account_number = ?");
        PreparedStatement increase = NewServer.connection.prepareStatement("UPDATE clients SET account_balance = account_balance + ? WHERE account_number = ?");
        decrease.setDouble(1,amount);
        decrease.setString(2,accountNumber);
        increase.setDouble(1,amount);
        increase.setString(2,destinationAccountNumber);
        decrease.executeUpdate();
        increase.executeUpdate();
    }

    public boolean validateTransfer(String accountNumber, String destinationAccountNumber, double amount, double currBalance) throws SQLException {
        if (!accountNumber.equals(destinationAccountNumber) && amount<=currBalance) {
            PreparedStatement statement = NewServer.connection.prepareStatement("SELECT account_number FROM clients WHERE account_number = ?");
            statement.setString(1,destinationAccountNumber);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
        return false;
    }

    public void changeName(String accountNumber, String name) throws SQLException {
        PreparedStatement statement = NewServer.connection.prepareStatement("UPDATE clients SET name = ? WHERE account_number = ?");
        statement.setString(1,name);
        statement.setString(2,accountNumber);
        statement.executeUpdate();
    }

    public void changePassword(String accountNumber, String password) throws SQLException {
        PreparedStatement statement = NewServer.connection.prepareStatement("UPDATE clients SET password = ? WHERE account_number = ?");
        statement.setString(1,password);
        statement.setString(2,accountNumber);
        statement.executeUpdate();
    }

    public void changeUsername(String accountNumber, String username) throws SQLException {
        PreparedStatement statement = NewServer.connection.prepareStatement("UPDATE clients SET username = ? WHERE account_number = ?");
        statement.setString(1,username);
        statement.setString(2,accountNumber);
        statement.executeUpdate();
    }

    public void deleteAccount(String accountNumber) throws SQLException {
        PreparedStatement statement = NewServer.connection.prepareStatement("DELETE FROM clients WHERE account_number = ?");
        statement.setString(1,accountNumber);
        statement.executeUpdate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
