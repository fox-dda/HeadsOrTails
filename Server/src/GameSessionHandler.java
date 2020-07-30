import java.util.HashMap;

// Класс для обработки игровой сессии, чтобы не засорять этой логикой класс Server
public class GameSessionHandler {

    private static final Float START_BALANCE = 5000f;
    private MessageParser _messageParser = new MessageParser();
    private HashMap<TCPConnection, Float> _clientsMap = new HashMap<>();
    private GameEngine _gameEngine = new GameEngine();

    public String handle(TCPConnection tcpConnection, String message) {
        String response = "";
        PlaySessionParameters playParams = _messageParser.parse(message);
        if(playParams == null) {
            response = "Unknown command";
        } else {
            Float oldBalance = _clientsMap.get(tcpConnection);
            Float newBalance = _gameEngine.play(_clientsMap.get(tcpConnection),
                    playParams);
            _clientsMap.replace(tcpConnection, newBalance);

            if(newBalance > oldBalance)
            {
                response+= "You win! ";
            }
            else
            {
                response+= "You lose! ";
            }

            response+= "Balance: " + newBalance;
        }

        return  response;
    }

    public String addPlayer(TCPConnection tcpConnection) {
        _clientsMap.put(tcpConnection, START_BALANCE);
        return "You connected to server! Balance: " + START_BALANCE;
    }

    public void removePlayer(TCPConnection tcpConnection){
        _clientsMap.remove(tcpConnection);
    }
}
