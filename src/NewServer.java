import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NewServer {
    ArrayList<Option> optionList = new ArrayList<>();
    ServerSocket serverSocket;
    Socket clientSocket;
    public static BufferedReader reader;
    public static PrintWriter writer;
    public static String requestOption;

    public static void main(String[] args) throws IOException {
        NewServer server = new NewServer();
        Client client1 =  new Client("Thu","2014","redvelvet","140801");
        Client client2 = new Client("Cau","2003","cau123","anhthu2003");
        client1.setAccountBalance(100);
        Client.clientsList.add(client1);
        Client.clientsList.add(client2);
        server.clientHandler();
    }

    public void clientHandler() throws IOException {
        Option.addOptionList();

        serverSocket = new ServerSocket(2014);
        clientSocket = serverSocket.accept();

        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new PrintWriter(clientSocket.getOutputStream(),true);

        while (true) {
            requestOption = reader.readLine();
            System.out.println("Option read: "+requestOption);

            System.out.println("Sending back signal...");
            optionHandler();
        }
    }
    public void optionHandler(){
        for (Option opt:Option.optionList) {
            if (opt.checkOption(requestOption)) {
                System.out.println(opt.getClass());
                opt.go();
            }
        }
    }
}
