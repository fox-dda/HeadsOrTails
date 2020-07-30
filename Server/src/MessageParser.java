import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Парсер входящих сообшений на скорую руку
public class MessageParser {

    public PlaySessionParameters parse(String message) {
        if(message == null) return null;
        FlipUnit flipUnit;
        Float rate;
        //TODO:Regex
        if(message.startsWith("play")
            && message.length() > 11
            && message.length() < 22) {
            try {
                if (message.toLowerCase().contains("head")) {
                    flipUnit = FlipUnit.HEADS;
                } else if (message.toLowerCase().contains("tail")) {
                    flipUnit = FlipUnit.TAILS;
                } else {
                    return null;
                }

                Pattern ptrn = Pattern.compile("[-+]?\\b\\d+(?:\\.\\d+)?\\b");
                Matcher matcher = ptrn.matcher(message);
                List<String> res = new ArrayList<>();
                while (matcher.find()) {
                    res.add(matcher.group());
                }

                String rateStr = res.get(res.size() - 1);

                try {
                    rate = Float.parseFloat(rateStr);
                } catch (NumberFormatException e) {
                    return null;
                }

                return new PlaySessionParameters(flipUnit, rate);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
