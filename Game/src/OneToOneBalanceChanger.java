public class OneToOneBalanceChanger implements IBalanceChangeStrategy {
    @Override
    public Float handleWin(Float balance, Float rate) {                 // В текущей реализации начисляем или списываем
        Float absRate = Math.abs(rate);                                 // ровно столько, сколько поставили - без множителя.
        return balance >= absRate ? balance + absRate : balance;        // Если баланс меньше ставки - баланс не изменится.
    }

    @Override
    public Float handleLose(Float balance, Float rate) {
        Float absRate = Math.abs(rate);
        return balance >= absRate ? balance - absRate : balance;
    }
}
