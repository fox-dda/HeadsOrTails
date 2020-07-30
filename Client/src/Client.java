import java.io.IOException;
import java.util.Scanner;

//Класс ответственный за сетевую составляющую клиента
public class Client implements ITCPConnectionListener {

    private static final String IP_ADDR = "127.0.0.1";
    private static final int PORT = 8000;

    private TCPConnection _connection;

    public Client(String ipAddress, int port){
        try {
            _connection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (IOException e) {
            System.out.println("Connection exception: " + e);
        }
    }


//    public static void main(String[] args){
//        new Client(IP_ADDR, PORT);
//    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Connection ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        System.out.println(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        System.out.println("Connection close!");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("Connection exception: " + e);
    }

    public void sendToServer(String value) {
        _connection.sendString(value);
    }
}
