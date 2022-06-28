import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
public class ATM implements MyTheme{
    MyFrame getCashFrame;
    MyFrame loginFrame;
    MyFrame signUpFrame;
    MyFrame menuFrame;
    MyFrame depositFrame;
    MyFrame transferFrame;
    MyFrame accountSettingFrame;
    public MyTextField userField;
    public MyPasswordField passField;
    public MyPasswordField confirmPassFiled;
    public MyTextField accountNumberField;
    public MyTextField nameField;
    public Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    Client currClient = new Client();
    private String optionSignal;

    private MyTextField depositAmount;
    private MyTextField getCashAmount;
    private MyTextField transferAmount;
    private MyTextField destination;
    private MyTextField typeField;
    private MyTextField typeAgainField;
    private MyFrame changeNameFrame;
    private MyFrame deleteAccountFrame;
    private MyFrame changeUserNameFrame;
    private MyFrame changePasswordFrame;

    public static void main(String[] args) {
        ATM a = new ATM();
        a.loadGui();
        a.setUpNetworking();
    }

    private void loadGui() {
        loginGui();
    }
    private void loginGui() {
        loginFrame = new MyFrame("Revel ATM");

        MyPanel loginPanel = new MyPanel(new BorderLayout(),"main");

        userField = new MyTextField(10);
        passField = new MyPasswordField(10);

        MyButton loginButton = new MyButton("Login");
        loginButton.addActionListener(new LoginListener());
        MyButton signUpButton = new MyButton("Sign Up");
        signUpButton.addActionListener(new LoginToSignUpListener());

        MyPanel grid = new MyPanel(new GridLayout(3,2,0,15));

        MyTitle title = new MyTitle();
        MyLabel usernameLabel = new MyLabel("Username");
        MyLabel passLabel = new MyLabel("Password");

        grid.add(usernameLabel);
        grid.add(userField);
        grid.add(passLabel);
        grid.add(passField);
        grid.add(loginButton);

        SouthPanel southPanel = new SouthPanel(new GridLayout(1,1));
        MyLabel orLabel = new MyLabel("Or you can");

        southPanel.add(orLabel);
        southPanel.add(signUpButton);

        loginPanel.add(BorderLayout.NORTH,title);
        loginPanel.add(BorderLayout.CENTER,grid);
        loginPanel.add(BorderLayout.SOUTH,southPanel);

        loginFrame.getContentPane().add(loginPanel);
        loginFrame.setVisible(true);
        loginFrame.pack();
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void signUpGUi() {
        signUpFrame = new MyFrame("Sign Up");

        MyPanel signUpPanel = new MyPanel(new BorderLayout(),"main");

        userField = new MyTextField(10);
        passField = new MyPasswordField(10);
        confirmPassFiled = new MyPasswordField(10);
        accountNumberField = new MyTextField(10);
        nameField = new MyTextField(10);

        MyButton loginButton = new MyButton("Login");
        loginButton.addActionListener(new SignupToLoginListener());
        MyButton signUpButton = new MyButton("Sign Up");
        signUpButton.addActionListener(new SignUpListener());

        MyPanel grid = new MyPanel(new GridLayout(6,2,0,15));

        MyTitle title = new MyTitle();
        MyLabel usernameLabel = new MyLabel("Username");
        MyLabel passLabel = new MyLabel("Password");
        MyLabel passAgainLabel = new MyLabel("Confirm the password");
        MyLabel accountNumberLabel = new MyLabel("Account ID");
        MyLabel nameLabel = new MyLabel("Your name");

        grid.add(nameLabel);
        grid.add(nameField);
        grid.add(accountNumberLabel);
        grid.add(accountNumberField);
        grid.add(usernameLabel);
        grid.add(userField);
        grid.add(passLabel);
        grid.add(passField);
        grid.add(passAgainLabel);
        grid.add(confirmPassFiled);
        grid.add(signUpButton);

        SouthPanel southPanel = new SouthPanel(new GridLayout(1,1));
        MyLabel orLabel = new MyLabel("Or you can");

        southPanel.add(orLabel);
        southPanel.add(loginButton);

        signUpPanel.add(BorderLayout.NORTH,title);
        signUpPanel.add(BorderLayout.CENTER,grid);
        signUpPanel.add(BorderLayout.SOUTH,southPanel);

        signUpFrame.getContentPane().add(signUpPanel);
        signUpFrame.setVisible(true);
        signUpFrame.pack();
        signUpFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void menuGui() {
        menuFrame = new MyFrame("Menu");

        MyPanel menuPanel = new MyPanel(new BorderLayout(),"main");

        MyTitle title = new MyTitle();

        MyLabel nameLabel = new MyLabel("Welcome "+currClient.getName());
        MyLabel accountNumberLabel = new MyLabel("Account ID: "+currClient.getAccountNumber());
        MyLabel balanceLabel = new MyLabel("Your balance $$$: ");
        MyLabel getBalanceLabel = new MyLabel(currClient.getAccountBalance() +" $");

        MyButton accountSettingButton = new MyButton("Account Setting");
        accountSettingButton.addActionListener(new MenuToAccountSettingListener());
        MyButton getCashButton = new MyButton("Get Cash");
        getCashButton.addActionListener(new MenuToGetCashListener());
        MyButton depositButton = new MyButton("Deposit");
        depositButton.addActionListener(new MenuToDepositListener());
        MyButton transferButton = new MyButton("Transfers");
        transferButton.addActionListener(new MenuToTransferListener());
        MyButton loginMenuButton = new MyButton("Login Menu");
        loginMenuButton.addActionListener(new MenuToLoginListener());

        MyPanel optionPanel = new MyPanel(new GridLayout(2,2,20,20));
        optionPanel.add(getCashButton);
        optionPanel.add(depositButton);
        optionPanel.add(transferButton);
        optionPanel.add(accountSettingButton);

        optionPanel.setBorder(BorderFactory.createEmptyBorder(0,100,0,0));

        SouthPanel southPanel = new SouthPanel();
        southPanel.add(loginMenuButton);

        Box westBox = new Box(BoxLayout.Y_AXIS);
        westBox.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        westBox.add(nameLabel);
        westBox.add(accountNumberLabel);
        westBox.add(balanceLabel);
        westBox.add(getBalanceLabel);

        menuPanel.add(BorderLayout.WEST,westBox);
        menuPanel.add(BorderLayout.NORTH,title);
        menuPanel.add(BorderLayout.CENTER,optionPanel);
        menuPanel.add(BorderLayout.SOUTH,southPanel);

        menuFrame.getContentPane().add(BorderLayout.CENTER,menuPanel);
        menuFrame.setVisible(true);
        menuFrame.pack();
        menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void getCashGui() {
        getCashFrame = new MyFrame("Get Cash");

        MyPanel getCashPanel = new MyPanel(new BorderLayout(),"main");

        MyTitle title = new MyTitle();

        MyLabel balanceLabel = new MyLabel("Your balance $$$: ");
        MyLabel getBalanceLabel = new MyLabel(currClient.getAccountBalance()+" $");
        MyLabel inputLabel = new MyLabel("Enter your amount ($)");

        getCashAmount = new MyTextField(10);

        MyPanel grid = new MyPanel(new GridLayout(2,2,20,20));
        grid.add(balanceLabel);
        grid.add(getBalanceLabel);
        grid.add(inputLabel);
        grid.add(getCashAmount);

        SouthPanel southPanel = new SouthPanel(new GridLayout(1,2,20,0));
        MyButton getCashButton = new MyButton("Confirm >>>");
        getCashButton.addActionListener(new TakeOutMoneyListener());
        MyButton backButton = new MyButton("<<< Back to Menu");
        backButton.addActionListener(new GetCashToMenuListener());
        southPanel.add(backButton);
        southPanel.add(getCashButton);

        getCashPanel.add(BorderLayout.NORTH,title);
        getCashPanel.add(BorderLayout.CENTER,grid);
        getCashPanel.add(BorderLayout.SOUTH,southPanel);

        getCashFrame.getContentPane().add(BorderLayout.CENTER,getCashPanel);
        getCashFrame.setVisible(true);
        getCashFrame.pack();
        getCashFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void depositGui() {
        depositFrame = new MyFrame("Deposit");

        MyPanel depositPanel = new MyPanel(new BorderLayout(),"main");

        MyTitle title = new MyTitle();

        MyLabel balanceLabel = new MyLabel("Your balance $$$: ");
        MyLabel getBalanceLabel = new MyLabel(currClient.getAccountBalance()+" $");
        MyLabel inputLabel = new MyLabel("Enter your amount ($)");

        depositAmount = new MyTextField(20);

        MyPanel grid = new MyPanel(new GridLayout(2,2,20,20));
        grid.add(balanceLabel);
        grid.add(getBalanceLabel);
        grid.add(inputLabel);
        grid.add(depositAmount);

        SouthPanel southPanel = new SouthPanel(new GridLayout(1,2,20,0));
        MyButton depositButton = new MyButton("Confirm >>>");
        depositButton.addActionListener(new putMoneyListener());
        MyButton backButton = new MyButton("<<< Back to Menu");
        backButton.addActionListener(new DepositToMenuListener());
        southPanel.add(backButton);
        southPanel.add(depositButton);

        depositPanel.add(BorderLayout.NORTH,title);
        depositPanel.add(BorderLayout.CENTER,grid);
        depositPanel.add(BorderLayout.SOUTH,southPanel);

        depositFrame.getContentPane().add(BorderLayout.CENTER,depositPanel);
        depositFrame.setVisible(true);
        depositFrame.pack();
        depositFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void transferGui() {
        transferFrame = new MyFrame("Transfer");

        MyPanel transferPanel = new MyPanel(new BorderLayout(),"main");

        MyTitle title = new MyTitle();

        MyLabel balanceLabel = new MyLabel("Your balance $$$: ");
        MyLabel getBalanceLabel = new MyLabel(currClient.getAccountBalance()+" $");
        MyLabel inputLabel = new MyLabel("Enter your amount ($)");
        MyLabel destinationLabel = new MyLabel("Enter your destination (Account ID)");

        transferAmount = new MyTextField(20);
        destination = new MyTextField(20);

        MyPanel grid = new MyPanel(new GridLayout(3,2,20,20));
        grid.add(balanceLabel);
        grid.add(getBalanceLabel);
        grid.add(inputLabel);
        grid.add(transferAmount);
        grid.add(destinationLabel);
        grid.add(destination);

        SouthPanel southPanel = new SouthPanel(new GridLayout(1,2,20,0));
        MyButton depositButton = new MyButton("Confirm >>>");
        depositButton.addActionListener(new TransferToPopUpListener());
        MyButton backButton = new MyButton("<<< Back to Menu");
        backButton.addActionListener(new TransferToMenuListener());
        southPanel.add(backButton);
        southPanel.add(depositButton);

        transferPanel.add(BorderLayout.NORTH,title);
        transferPanel.add(BorderLayout.CENTER,grid);
        transferPanel.add(BorderLayout.SOUTH,southPanel);

        transferFrame.getContentPane().add(BorderLayout.CENTER,transferPanel);
        transferFrame.setVisible(true);
        transferFrame.pack();
        transferFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void accountSettingGui() {
        accountSettingFrame = new MyFrame("Account Settings");

        MyTitle title = new MyTitle();

        MyPanel accountSettingPanel = new MyPanel(new BorderLayout(),"main");

        MyLabel nameLabel = new MyLabel("Welcome "+currClient.getName());
        MyLabel balanceLabel = new MyLabel("Your balance $$$: "+currClient.getAccountBalance()+" $");
        MyLabel usernameLabel = new MyLabel("Username: "+currClient.getUsername());
        MyLabel accountIdLabel = new MyLabel("Account ID: "+currClient.getAccountNumber());

        MyButton changeNameButton = new MyButton("Change your name");
        changeNameButton.addActionListener(new ChangeNameListener());
        MyButton changeUsernameButton = new MyButton("Change your username");
        changeUsernameButton.addActionListener(new ChangeUsernameListener());
        MyButton changePasswordButton = new MyButton("Change your password");
        changePasswordButton.addActionListener(new ChangePasswordListener());
        MyButton changeAccountIdButton = new MyButton("Delete your account");
        changeAccountIdButton.addActionListener(new DeleteAccountListener());

        MyPanel grid = new MyPanel(new GridLayout(4,2,20,20));
        grid.add(nameLabel);
        grid.add(changeNameButton);
        grid.add(usernameLabel);
        grid.add(changeUsernameButton);
        grid.add(accountIdLabel);
        grid.add(changeAccountIdButton);
        grid.add(balanceLabel);
        grid.add(changePasswordButton);

        SouthPanel southPanel = new SouthPanel();
        MyButton backButton = new MyButton("<<< Back to Menu");
        backButton.addActionListener(new AccountSettingToMenuListener());
        southPanel.add(backButton);

        accountSettingPanel.add(BorderLayout.NORTH,title);
        accountSettingPanel.add(BorderLayout.SOUTH,southPanel);
        accountSettingPanel.add(BorderLayout.CENTER,grid);

        accountSettingFrame.add(BorderLayout.CENTER, accountSettingPanel);
        accountSettingFrame.setVisible(true);
        accountSettingFrame.pack();
        accountSettingFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void changeNamePopUp() {
        changeNameFrame = new MyFrame("Change Name");

        MyTitle title = new MyTitle();

        MyPanel changeNamePanel = new MyPanel(new BorderLayout(),"main");

        MyLabel typeLabel = new MyLabel("Type your new name");
        MyLabel typeAgainLabel = new MyLabel("Type your new name again");

        typeField = new MyTextField(20);
        typeAgainField = new MyTextField(20);

        MyPanel centerPanel = new MyPanel(new GridLayout(2,2,20,20));
        centerPanel.add(typeLabel);
        centerPanel.add(typeField);
        centerPanel.add(typeAgainLabel);
        centerPanel.add(typeAgainField);

        MyButton confirmButton = new MyButton("Confirm >>>");
        SouthPanel southPanel = new SouthPanel();
        southPanel.add(confirmButton);
        confirmButton.addActionListener(new ChangeNameToWindowPopUpListener());

        changeNamePanel.add(BorderLayout.NORTH,title);
        changeNamePanel.add(BorderLayout.CENTER,centerPanel);
        changeNamePanel.add(BorderLayout.SOUTH,southPanel);

        changeNameFrame.add(BorderLayout.CENTER,changeNamePanel);
        changeNameFrame.setVisible(true);
        changeNameFrame.pack();
        changeNameFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void deleteAccountPopUp() {
        deleteAccountFrame = new MyFrame("Delete your account");

        MyTitle title = new MyTitle();

        MyPanel deleteAccountPanel = new MyPanel(new BorderLayout(),"main");

        MyLabel typeLabel = new MyLabel("Type your password");
        MyLabel typeAgainLabel = new MyLabel("Type your password again");

        passField = new MyPasswordField(20);
        confirmPassFiled = new MyPasswordField(20);

        MyPanel centerPanel = new MyPanel(new GridLayout(2,2,20,20));
        centerPanel.add(typeLabel);
        centerPanel.add(passField);
        centerPanel.add(typeAgainLabel);
        centerPanel.add(confirmPassFiled);

        MyButton confirmButton = new MyButton("Confirm >>>");
        SouthPanel southPanel = new SouthPanel();
        southPanel.add(confirmButton);
        confirmButton.addActionListener(new ChangeAccountIdToWindowPopUpListener());

        deleteAccountPanel.add(BorderLayout.NORTH,title);
        deleteAccountPanel.add(BorderLayout.CENTER,centerPanel);
        deleteAccountPanel.add(BorderLayout.SOUTH,southPanel);

        deleteAccountFrame.add(BorderLayout.CENTER,deleteAccountPanel);
        deleteAccountFrame.setVisible(true);
        deleteAccountFrame.pack();
        deleteAccountFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void changeUsernamePopUp() {
        changeUserNameFrame = new MyFrame("Change Username");

        MyTitle title = new MyTitle();

        MyPanel changeUsernamePanel = new MyPanel(new BorderLayout(),"main");

        MyLabel typeLabel = new MyLabel("Type your new username");
        MyLabel typeAgainLabel = new MyLabel("Type your new username again");

        typeField = new MyTextField(20);
        typeAgainField = new MyTextField(20);

        MyPanel centerPanel = new MyPanel(new GridLayout(2,2,20,20));
        centerPanel.add(typeLabel);
        centerPanel.add(typeField);
        centerPanel.add(typeAgainLabel);
        centerPanel.add(typeAgainField);

        MyButton confirmButton = new MyButton("Confirm >>>");
        SouthPanel southPanel = new SouthPanel();
        southPanel.add(confirmButton);
        confirmButton.addActionListener(new ChangeUsernameToWindowPopUpListener());

        changeUsernamePanel.add(BorderLayout.NORTH,title);
        changeUsernamePanel.add(BorderLayout.CENTER,centerPanel);
        changeUsernamePanel.add(BorderLayout.SOUTH,southPanel);

        changeUserNameFrame.add(BorderLayout.CENTER,changeUsernamePanel);
        changeUserNameFrame.setVisible(true);
        changeUserNameFrame.pack();
        changeUserNameFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void changePasswordPopUp() {
        changePasswordFrame = new MyFrame("Change Password");

        MyTitle title = new MyTitle();

        MyPanel changePasswordPanel = new MyPanel(new BorderLayout(),"main");

        MyLabel typeLabel = new MyLabel("Type your new password");
        MyLabel typeAgainLabel = new MyLabel("Type your new password again");

        passField = new MyPasswordField(20);
        confirmPassFiled = new MyPasswordField(20);

        MyPanel centerPanel = new MyPanel(new GridLayout(2,2,20,20));
        centerPanel.add(typeLabel);
        centerPanel.add(passField);
        centerPanel.add(typeAgainLabel);
        centerPanel.add(confirmPassFiled);

        MyButton confirmButton = new MyButton("Confirm >>>");
        SouthPanel southPanel = new SouthPanel();
        southPanel.add(confirmButton);
        confirmButton.addActionListener(new ChangePasswordToWindowPopUpListener());

        changePasswordPanel.add(BorderLayout.NORTH,title);
        changePasswordPanel.add(BorderLayout.CENTER,centerPanel);
        changePasswordPanel.add(BorderLayout.SOUTH,southPanel);

        changePasswordFrame.add(BorderLayout.CENTER,changePasswordPanel);
        changePasswordFrame.setVisible(true);
        changePasswordFrame.pack();
        changePasswordFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void setUpNetworking() {
        try {
            socket = new Socket("localhost",2014);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // read from server
            writer = new PrintWriter(socket.getOutputStream(),true); // send request to server
            System.out.println("Connected to the server");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getServerSignal() {
        try {
            optionSignal = reader.readLine();
            System.out.println("Option Signal: "+optionSignal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getClientData() throws IOException {
        currClient.setName(reader.readLine());
        currClient.setAccountNumber(reader.readLine());
        currClient.setUsername(reader.readLine());
        currClient.setPasswords(reader.readLine());
        currClient.setAccountBalance(Double.parseDouble(reader.readLine()));
    }
    public class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sendRequest("Login");
            writer.println(userField.getText());
            writer.println(passField.getPassword());
            getServerSignal();
            System.out.println(optionSignal);
            if (checkRequest()) {
                try {
                    getClientData();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                loginFrame.dispose();
                menuGui();
            } else {
                PopupDialog popupDialog = new PopupDialog();
                popupDialog.showMessage(loginFrame,"Invalid Username or Password","Error","Error");
            }
        }
    }

    public class SignupToLoginListener implements ActionListener    {
        @Override
        public void actionPerformed(ActionEvent e) {
            signUpFrame.dispose();
            loginGui();
        }

    }
    public class LoginToSignUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Sign Up has been clicked");
            loginFrame.dispose();
            signUpGUi();
        }
    }

    public class GetCashToMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getCashFrame.dispose();
            menuGui();
        }
    }

    public class SignUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendRequest("Signup");
            writer.println(nameField.getText());
            writer.println(accountNumberField.getText());
            writer.println(userField.getText());
            writer.println(passField.getPassword());
            writer.println(confirmPassFiled.getPassword());
            getServerSignal();
            PopupDialog popupDialog = new PopupDialog();
            if (checkRequest()) {
                popupDialog.showMessage(signUpFrame,"Signup successfully! Close this window and login","Info","Info");
                signUpFrame.dispose();
                loginGui();
            } else {
                popupDialog.showMessage(signUpFrame,"Invalid username or password! Please try again","Error","Error");
            }
        }
    }
    public class MenuToGetCashListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose();
            getCashGui();
        }
    }

    public class AccountSettingListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public class MenuToDepositListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose();
            depositGui();
        }
    }

    public class MenuToLoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loginFrame.dispose();
            menuFrame.dispose();
            loginGui();
        }
    }

    public class TakeOutMoneyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendRequest("GetCash");
            writer.println(getCashAmount.getText());
            getServerSignal();
            PopupDialog popup = new PopupDialog();
            if (checkRequest()) {
                popup.showMessage(depositFrame,"Succeeded","Info","Info");
//                JOptionPane.showMessageDialog(depositFrame,"Succeeded","Info",JOptionPane.INFORMATION_MESSAGE);
                getCashFrame.dispose();
                try {
                    getClientData();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(currClient.getAccountBalance());
                menuGui();
            } else
                popup.showMessage(depositFrame,"There something wrong! Please try again","Error","Error");
        }
    }

    public class putMoneyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendRequest("Deposit");
            writer.println(depositAmount.getText());
            getServerSignal();
            PopupDialog popupDialog = new PopupDialog();
            if (checkRequest()) {
//                JOptionPane.showMessageDialog(depositFrame,"Succeeded","Info",JOptionPane.INFORMATION_MESSAGE);
                popupDialog.showMessage(depositFrame,"Succeeded","Info","Info");
                depositFrame.dispose();
                //windowPopUp("Succeeded","Info");
                try {
                    getClientData();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(currClient.getAccountBalance());
                menuGui();
            } else popupDialog.showMessage(depositFrame,"There something wrong! Please try again","Error","Error");
        }
    }

    public class DepositToMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            depositFrame.dispose();
            menuGui();
        }
    }

    public class TransferToPopUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendRequest("Transfer");
            writer.println(transferAmount.getText());
            writer.println(destination.getText());
            getServerSignal();
            PopupDialog popupDialog = new PopupDialog();
            if (checkRequest()) {
                popupDialog.showMessage(transferFrame,"Succeeded","Info","Info");
                transferFrame.dispose();
                try {
                    getClientData();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(currClient.getAccountBalance());
                menuGui();
            } else popupDialog.showMessage(transferFrame,"There something wrong! Please try again","Error","Error");
        }
    }

    public class TransferToMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            transferFrame.dispose();
            menuGui();
        }
    }

    public class MenuToTransferListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose();
            transferGui();
        }
    }

    public class AccountSettingToMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            accountSettingFrame.dispose();
            menuGui();
        }
    }

    public class MenuToAccountSettingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose();
            accountSettingGui();
        }
    }

    public class ChangeNameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeNamePopUp();
        }
    }

    public class DeleteAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteAccountPopUp();
        }
    }

    public class ChangeUsernameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeUsernamePopUp();
        }
    }

    public class ChangePasswordListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePasswordPopUp();
        }
    }

    public class ChangeNameToWindowPopUpListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sendRequest("ChangeName");
            writer.println(typeField.getText());
            writer.println(typeAgainField.getText());
            getServerSignal();
            PopupDialog popupDialog = new PopupDialog();
            if (checkRequest()) {
                popupDialog.showMessage(changeNameFrame,"Succeeded","Info","Info");
                changeNameFrame.dispose();
                accountSettingFrame.dispose();
                try {
                    getClientData();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                accountSettingGui();
            } else popupDialog.showMessage(changeNameFrame,"There something wrong! Please try again","Error","Error");
        }
    }

    public class ChangeUsernameToWindowPopUpListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            sendRequest("ChangeUsername");
            writer.println(typeField.getText());
            writer.println(typeAgainField.getText());
            getServerSignal();
            PopupDialog popupDialog = new PopupDialog();
            if (checkRequest()) {
                popupDialog.showMessage(changeUserNameFrame,"Succeeded","Info","Info");
                changeUserNameFrame.dispose();
                accountSettingFrame.dispose();
                try {
                    getClientData();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                accountSettingGui();
            } else popupDialog.showMessage(changeUserNameFrame,"There something wrong! Please try again","Error","Error");
        }
    }

    public class ChangePasswordToWindowPopUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendRequest("ChangePassword");
            writer.println(passField.getPassword());
            writer.println(confirmPassFiled.getPassword());
            getServerSignal();
            PopupDialog popupDialog = new PopupDialog();
            if (checkRequest()) {
                popupDialog.showMessage(changePasswordFrame,"Succeeded","Info","Info");
                changePasswordFrame.dispose();
                accountSettingFrame.dispose();
                try {
                    getClientData();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                accountSettingGui();
            } else popupDialog.showMessage(changePasswordFrame,"There something wrong! Please try again","Error","Error");
        }
    }

    public class ChangeAccountIdToWindowPopUpListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            sendRequest("DeleteAccount");
            writer.println(passField.getPassword());
            writer.println(confirmPassFiled.getPassword());
            getServerSignal();
            PopupDialog popupDialog = new PopupDialog();
            if (checkRequest()) {
                popupDialog.showMessage(deleteAccountFrame,"Succeeded","Info","Info");
                deleteAccountFrame.dispose();
                accountSettingFrame.dispose();
                loginGui();
            } else popupDialog.showMessage(deleteAccountFrame,"There's something wrong! Please try again","Error","Error");
        }
    }

    public void sendRequest(String message) {
        try {
            writer.println(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean checkRequest() {
        return optionSignal.equals("true");
    }
}
