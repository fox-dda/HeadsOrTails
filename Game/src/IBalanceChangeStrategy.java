public interface IBalanceChangeStrategy {
    public Float handleWin(Float balance, Float rate);
    public Float handleLose(Float balance, Float rate);
}
