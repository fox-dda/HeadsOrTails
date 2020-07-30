public class PlaySessionParameters {
    private final FlipUnit _flipUnit;
    private final Float _rate;

    public PlaySessionParameters(FlipUnit flipUnit, Float rate) {
        _flipUnit = flipUnit;
        _rate = rate;
    }

    public FlipUnit getFlipUnit() {
        return _flipUnit;
    }

    public Float getRate(){
        return _rate;
    }
}
