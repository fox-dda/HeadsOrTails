import java.util.Scanner;

///Консоль для работы с клиентом
public class ClientConsole {
    private static final String IP_ADDR = "127.0.0.1";
    private static final int PORT = 8000;

    private Client _client;
    private Float _currentRate = 500f;

    public ClientConsole(String ipAddress, int port) {

        printMenu();

        _client = new Client(ipAddress, port);

        while (true)
        {
            Scanner in = new Scanner(System.in);
            String consoleStr = in.nextLine();
            if(consoleStr != null){
                handleConsoleLine(consoleStr);
            }
        }
    }

    public static void main(String[] args){
        new ClientConsole(IP_ADDR, PORT);
    }

    private void handleConsoleLine(String line){
        switch (line)
        {
            case "1":
                _client.sendToServer("play head " + _currentRate);
                break;
            case "2":
                _client.sendToServer("play tail " + _currentRate);
                break;
            case "3":
                changeRate();
                break;
            default: printMenu(); break;
        }
    }

    private void printMenu() {
        System.out.println("Hello!\n");
        System.out.println("Menu:\n");
        System.out.println("1: Bet on heads\n");
        System.out.println("2: Bet on tails\n");
        System.out.println("3: Change rate\n");
        System.out.println("Current rate: " + _currentRate + "\n");
    }

    private void changeRate(){
        while (true) {
            System.out.println("Enter a new rate:\n");
            Scanner in = new Scanner(System.in);
            String consoleStr = in.nextLine();
            if(consoleStr != null){
                try{
                    _currentRate = Float.parseFloat(consoleStr);
                } catch (NumberFormatException e){
                    System.out.println("Error\n");
                    continue;
                }

                break;
            }
        }

        printMenu();
    }
}
