package fmi.ai;

public enum Winner {
    X,
    O,
    DRAW,
    UNKNOWN;

    public static Winner fromCellState(CellState cellState) {
        switch (cellState) {
        case X:
            return X;
        case O:
            return O;
        case EMPTY:
            return UNKNOWN;
        default:
            return DRAW;
        }
    }
}
