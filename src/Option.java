import java.io.IOException;
import java.util.ArrayList;

public abstract class Option {
    public static ArrayList<Option> optionList = new ArrayList<>();
    public static Client currClient;
    public abstract void go(); // run Option
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
        optionList.add(new ChangeAccountId());
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
        NewServer.writer.println(Option.currClient.getName());
        NewServer.writer.println(Option.currClient.getAccountNumber());
        NewServer.writer.println(Option.currClient.getUsername());
        NewServer.writer.println(Option.currClient.getPasswords());
        NewServer.writer.println(Option.currClient.getAccountBalance());
    }
}

class Login extends Option {
    private String username;
    private String password;
    @Override
    public void go(){
        readData();
        System.out.println("This is login");
        if(responseRequest(validateLogin())) {
            sendClientData();
        }
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

    public boolean validateLogin() {
        for (Client client:Client.clientsList) {
            if (client.getUsername().equals(username) && client.getPasswords().equals(password)) {
                currClient = client;
                return true;
            }
        }
        return false;
    }


}

class Signup extends Option {
    private String confirmPassword;
    Client tempClient;
    @Override
    public void go() {
        readData();
        responseRequest(addUser());
        sendClientData();
        Client.showClientsList();
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

    public boolean validateSignup() {
        for (Client client:Client.clientsList) {
            if (client.getUsername().equals(tempClient.getUsername()) || tempClient.getUsername().equals("") ||
            tempClient.getPasswords().equals("") || !tempClient.getPasswords().equals(confirmPassword) ||
                    client.getAccountNumber().equals(tempClient.getAccountNumber()) || tempClient.getName().equals("") || tempClient.getAccountNumber().equals(""))
                return false;
        }
        return true;
    }

    public boolean addUser() {
        if (validateSignup()) {
            Client.clientsList.add(tempClient);
            return true;
        } else return false;
    }

}

class GetCash extends Option {
    @Override
    public void go() {
        try {
            double amount = Double.parseDouble(NewServer.reader.readLine());
            if (amount<=currClient.getAccountBalance()) {
                currClient.setAccountBalance(currClient.getAccountBalance()-amount);
                NewServer.writer.println("true");
                sendClientData();
            } else {
                NewServer.writer.println("false");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            NewServer.writer.println("true");
            sendClientData();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            if (validateInput(amount,destination)) {
                currClient.setAccountBalance(currClient.getAccountBalance()-amount);
                for (Client client:Client.clientsList) {
                    if (client.getAccountNumber().equals(destination)) {
                        client.setAccountBalance(client.getAccountBalance()+amount);
                        NewServer.writer.println("true");
                        sendClientData();
                        return;
                    }
                }
            }
            NewServer.writer.println("false");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateInput(double amount,String destination) {
        for (Client client:Client.clientsList) {
            if (client.getAccountNumber().equals(destination) && amount<currClient.getAccountBalance()) return true;
        }
       return false;
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
                currClient.setName(name);
                NewServer.writer.println("true");
                sendClientData();
                return;
            }
            NewServer.writer.println("false");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                currClient.setPasswords(password);
                NewServer.writer.println("true");
                sendClientData();
                return;
            }
            NewServer.writer.println("false");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                currClient.setUsername(username);
                NewServer.writer.println("true");
                sendClientData();
                return;
            }
            NewServer.writer.println("false");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readData() {

    }
}
class ChangeAccountId extends Option {

    @Override
    public void go() {
        try {
            String accountId = NewServer.reader.readLine();
            String confirmAccountId = NewServer.reader.readLine();
            for (Client client:Client.clientsList) {
                if (!client.getAccountNumber().equals(accountId) && accountId.equals(confirmAccountId) && !accountId.equals("")) {
                    currClient.setAccountNumber(accountId);
                    NewServer.writer.println("true");
                    sendClientData();
                    return;
                }
            }
            NewServer.writer.println("false");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void readData() {

    }
}