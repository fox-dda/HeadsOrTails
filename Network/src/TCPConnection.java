import java.io.*;
import java.net.Socket;


// Класс для сетевого взаимедействия
public class TCPConnection {

    private final Socket _socket;
    private final Thread _thread;
    private final ITCPConnectionListener _eventListener;
    private final BufferedReader _input;
    private final BufferedWriter _output;

    // Удобный конструктод для клиента
    public TCPConnection(ITCPConnectionListener listener, String ipAddr, int port) throws IOException{
        this(listener, new Socket(ipAddr, port));
    }

    // Удобный конструктор для сервера
    public TCPConnection(ITCPConnectionListener listener, Socket socket) throws IOException{
        _eventListener = listener;
        _socket = socket;
        _input = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        _output = new BufferedWriter(new OutputStreamWriter(
                socket.getOutputStream()));

        _thread = new Thread( new Runnable() {                  // Прослушка сокета проиходит в отдельном новом потоке
            @Override                                           // благодаря чему сервер может обслуживать много клиентов
            public void run() {
                try {
                    _eventListener.onConnectionReady(TCPConnection.this);
                    while(!_thread.isInterrupted())
                    {
                        _eventListener.onReceiveString(TCPConnection.this, _input.readLine());
                    }

                } catch (IOException e) {
                    _eventListener.onException(TCPConnection.this, e);
                } finally {
                    _eventListener.onDisconnect(TCPConnection.this);
                }
            }
        });
        _thread.start();
    }

    public synchronized void sendString(String value) {
        try {
            _output.write(value + "\r\n");
            _output.flush();
        } catch (IOException e) {
            _eventListener.onException(TCPConnection.this, e);
            disconnect();
        }
    }

    public synchronized void disconnect(){
        _thread.interrupt();
        try {
            _socket.close();
        } catch (IOException e) {
            _eventListener.onException(TCPConnection.this, e);
        }
    }

    @Override
    public String toString(){

        return "TCPConnection: " +
                _socket.getInetAddress() +
                ": " + _socket.getPort();
    }
}
