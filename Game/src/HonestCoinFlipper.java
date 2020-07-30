public class HonestCoinFlipper implements ICoinFlipper
{
    public FlipUnit flipCoin()
    {
        double randomNumber = Math.random() * 100;
        return randomNumber >= 50 ? FlipUnit.HEADS : FlipUnit.TAILS;        // В текущей реализации подкидываем честно
    }                                                                       // без подкрутки шансов
}
