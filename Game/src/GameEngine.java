// Класс ответственный непосредственно за игровой процесс
public class GameEngine {

    private IBalanceChangeStrategy _balanceChangeStrategy;                        // Точка расширения - стратегия изменения баланса
    private ICoinFlipper _coinFlipper;                                            // Точка расширения - Подкидыватель монеты
                                                                                  // пока только одна реализация - Честный подкидыватель
    public GameEngine()
    {
        _balanceChangeStrategy = new OneToOneBalanceChanger();
        _coinFlipper = new HonestCoinFlipper();
    }

    public GameEngine(IBalanceChangeStrategy rateChangeStrategy,
                      ICoinFlipper coinFlipper)
    {
        _balanceChangeStrategy = rateChangeStrategy;
        _coinFlipper = coinFlipper;
    }

    // Метод для игры принимающий баланс, ставку и сторону монеты
    // Возвращает новый баланс
    public Float play(Float balance, PlaySessionParameters playSessionParameters) {

        if (_coinFlipper.flipCoin() == playSessionParameters.getFlipUnit()){
            return _balanceChangeStrategy.handleWin(balance, playSessionParameters.getRate());
        }
        else {
            return _balanceChangeStrategy.handleLose(balance, playSessionParameters.getRate());
        }
    }

    public IBalanceChangeStrategy getRateChanger()
    {
        return _balanceChangeStrategy;
    }

    public void setRateChanger(IBalanceChangeStrategy rateChanger)
    {
         _balanceChangeStrategy = rateChanger;
    }

    public ICoinFlipper getCoinFlipper() {
        return _coinFlipper;
    }

    public void setCoinFlipper(ICoinFlipper coinFlipper) {
        _coinFlipper = coinFlipper;
    }
}
