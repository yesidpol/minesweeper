package mines;

public enum SquareStatus {
    ACTIVE("  "), PLAYED(" p"), MARKED(" m");
    
    private String text;
    
    SquareStatus(String text){
        this.text = text;
    }
    
    public String toString() {
        return text;
    }
}
