import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public abstract class Option {
    public static ArrayList<Option> optionList = new ArrayList<>();
    public static Client currClient;
    public abstract void go() throws SQLException; // run Option
    public abstract void readData();

    public boolean checkOption(String option) {
        return (this.getClass().toString().contains(option) || this.getClass().toString().equalsIgnoreCase(option));
    }
    static void addOptionList() {
        optionList.add(new Login());
        optionList.add(new Signup());
        optionList.add(new GetCash());
        optionList.add(new Deposit());
        optionList.add(new ChangeName());
        optionList.add(new Transfer());
        optionList.add(new ChangeUsername());
        optionList.add(new ChangePassword());
        optionList.add(new DeleteAccount());
    }
    public boolean responseRequest(boolean condition) {
        if (condition) {
            NewServer.writer.println("true");
            return true;
        } else {
            NewServer.writer.println("false");
            return false;
        }
    }
    public void sendClientData() {
        NewServer.writer.println(currClient.getName());
        NewServer.writer.println(currClient.getAccountNumber());
        NewServer.writer.println(currClient.getUsername());
        NewServer.writer.println(currClient.getPasswords());
        NewServer.writer.println(currClient.getAccountBalance());
    }
}

class Login extends Option {
    private String username;
    private String password;
    Queries q;
    @Override
    public void go() throws SQLException {
        readData();
        System.out.println("This is login");
        q = new Queries();
        if(responseRequest(q.validateLogin(username,password))) {
            setCurrClient();
            sendClientData();
        }
    }

    public void setCurrClient() throws SQLException {
        ResultSet resultSet = q.sendClientData(username);
        resultSet.next();
        System.out.println(resultSet.getString("name"));
        currClient = new Client(resultSet.getString("name"),
                resultSet.getString("account_number"),
                resultSet.getString("username"),
                resultSet.getString("password"),resultSet.getDouble("account_balance"));

        System.out.println("Inside setCurrClient: ");
        currClient.showInfo();
    }

    @Override
    public void readData() {
        try {
            username = NewServer.reader.readLine();
            password = NewServer.reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class Signup extends Option {
    private String confirmPassword;
    Queries q;
    Client tempClient;
    @Override
    public void go() {
        readData();
        try {
            responseRequest(addUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readData() {
        try {
            tempClient = new Client();
            tempClient.setName(NewServer.reader.readLine());
            tempClient.setAccountNumber(NewServer.reader.readLine());
            tempClient.setUsername(NewServer.reader.readLine());
            tempClient.setPasswords(NewServer.reader.readLine());
            confirmPassword = NewServer.reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addUser() throws SQLException {
        q = new Queries();
        if (q.validateSignup(tempClient.getUsername(),tempClient.getPasswords(),confirmPassword,tempClient.getAccountNumber())) {
            Queries q = new Queries(tempClient);
            q.addClient().executeUpdate();
            return true;
        } else return false;
    }
}

class GetCash extends Option {
    @Override
    public void go() {
        try {
            System.out.println("Inside getCash:");
            currClient.showInfo();
            double amount = Double.parseDouble(NewServer.reader.readLine());
            if (amount<=currClient.getAccountBalance()) {
                currClient.setAccountBalance(currClient.getAccountBalance()-amount);
                Queries q = new Queries();
                q.getCash(currClient.getAccountNumber(),amount);
                NewServer.writer.println("true");
                sendClientData();
            } else {
                NewServer.writer.println("false");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readData() {

    }
}

class Deposit extends Option {
    @Override
    public void go() {
        try {
            double amount = Double.parseDouble(NewServer.reader.readLine());
            currClient.setAccountBalance(currClient.getAccountBalance()+amount);
            Queries q = new Queries();
            q.deposit(currClient.getAccountNumber(),amount);
            NewServer.writer.println("true");
            sendClientData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void readData() {

    }
}

class Transfer extends Option {
    @Override
    public void go() {
        try {
            double amount = Double.parseDouble(NewServer.reader.readLine());
            String destination = NewServer.reader.readLine();
            Queries q = new Queries();
            if (q.validateTransfer(currClient.getAccountNumber(), destination, amount, currClient.getAccountBalance())) {
                currClient.setAccountBalance(currClient.getAccountBalance()-amount);
                q.transfer(destination, currClient.getAccountNumber(), amount);
                NewServer.writer.println("true");
                sendClientData();
                return;
            }
            NewServer.writer.println("false");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readData() {

    }
}

class ChangeName extends Option {

    @Override
    public void go() {
        try {
            String name = NewServer.reader.readLine();
            String confirmName = NewServer.reader.readLine();
            if (name.equals(confirmName) && !name.equals("")) {
                Queries q = new Queries();
                q.changeName(currClient.getAccountNumber(),name);
                currClient.setName(name);
                NewServer.writer.println("true");
                sendClientData();
                return;
            }
            NewServer.writer.println("false");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void readData() {

    }
}

class ChangePassword extends Option {
    @Override
    public void go() {
        try {
            String password = NewServer.reader.readLine();
            String confirmPassword = NewServer.reader.readLine();
            if (password.equals(confirmPassword) && !password.equals("")) {
                Queries q = new Queries();
                q.changePassword(currClient.getAccountNumber(),password);
                currClient.setPasswords(password);
                NewServer.writer.println("true");
                sendClientData();
                return;
            }
            NewServer.writer.println("false");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readData() {

    }
}

class ChangeUsername extends Option {

    @Override
    public void go() {
        try {
            String username = NewServer.reader.readLine();
            String confirmUsername = NewServer.reader.readLine();
            if (username.equals(confirmUsername) && !username.equals("")) {
                Queries q = new Queries();
                q.changeUsername(currClient.getAccountNumber(),username);
                currClient.setUsername(username);
                NewServer.writer.println("true");
                sendClientData();
                return;
            }
            NewServer.writer.println("false");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readData() {

    }
}
class DeleteAccount extends Option {

    @Override
    public void go() {
        try {
            String password = NewServer.reader.readLine();
            String confirmPassword = NewServer.reader.readLine();
            Queries q = new Queries();
            if (password.equals(confirmPassword) && !password.equals("")) {
                q.deleteAccount(currClient.getAccountNumber());
                NewServer.writer.println("true");
                return;
            }
            NewServer.writer.println("false");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void readData() {

    }
}