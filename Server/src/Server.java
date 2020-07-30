import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

// Игровой сервер
public class Server implements ITCPConnectionListener{

    private GameSessionHandler _gameSessionHandler = new GameSessionHandler();

    public static void main(String[] args)
    {
        new Server();
    }

    private Server(){
        System.out.println("Server running...");
        //TODO:ini file
        try(ServerSocket serverSocket = new ServerSocket(8000)){
            while (true){                                                   // Ждем поключений
                try {                                                       // Как только есть входящее подключение
                    new TCPConnection(this, serverSocket.accept());  // Слушаем в новом потоке (см. TCPConnection)
                } catch (IOException e){
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Client connected: " + tcpConnection);
        sendToClient(tcpConnection, _gameSessionHandler.addPlayer(tcpConnection));
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        System.out.println("Receive: " + value + " from: " + tcpConnection);
        String response = _gameSessionHandler.handle(tcpConnection, value);
        sendToClient(tcpConnection, response);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        System.out.println("Client disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e + " from: " + tcpConnection);
    }

    private void sendToClient(TCPConnection clientConnection, String value) {
        clientConnection.sendString(value);
        System.out.println("Server response: " + value);
    }
}
