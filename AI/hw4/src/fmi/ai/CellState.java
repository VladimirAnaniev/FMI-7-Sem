package fmi.ai;

public enum CellState {
    X,
    O,
    EMPTY {
        @Override
        public String toString() {
            return " ";
        }
    }
}
