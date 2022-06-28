import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NewServer {
    static Connection connection;
    ServerSocket serverSocket;
    Socket clientSocket;
    public static BufferedReader reader;
    public static PrintWriter writer;
    public static String requestOption;

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        NewServer server = new NewServer();
        server.clientHandler();
    }

    public void clientHandler() throws IOException, SQLException, ClassNotFoundException {
        Option.addOptionList();

        serverSocket = new ServerSocket(2014);
        clientSocket = serverSocket.accept();

        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new PrintWriter(clientSocket.getOutputStream(),true);
        while (true) {
            requestOption = reader.readLine();
            System.out.println("Option read: "+requestOption);

            System.out.println("Sending back signal...");
            dbInitialize();
            optionHandler();
        }
    }
    public void optionHandler() throws SQLException {
        for (Option opt:Option.optionList) {
            if (opt.checkOption(requestOption)) {
                System.out.println(opt.getClass());
                opt.go();
                return;
            }
        }
    }
    public void dbInitialize() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_withdrawl","root","140801");
    }
}
