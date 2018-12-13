public class Tile {
    private String letter;
    private int value;
    private String reserved;  // Generic use, up to now used by the rack to mark as available or unavailable  tile on the rack
    
    public Tile(String letter) {
        this.letter = letter;
        this.value = pointValue(letter);
        this.reserved = "";
    }
    
    char getLetter() {
        return letter.charAt(0);
    }
    
    int getValue() {
        return value;
    }
    
    void setReserved(String reserved) {
        this.reserved = reserved;
    }
    
    String getReserved() {
        return this.reserved;
    }
    
    private int pointValue(String letter) {
        int ret = 0;
        
        if(letter.equalsIgnoreCase("a")) return 1;
        if(letter.equalsIgnoreCase("b")) return 3;
        if(letter.equalsIgnoreCase("c")) return 3;
        if(letter.equalsIgnoreCase("d")) return 2;
        if(letter.equalsIgnoreCase("e")) return 1;
        if(letter.equalsIgnoreCase("f")) return 4;
        if(letter.equalsIgnoreCase("g")) return 2;
        if(letter.equalsIgnoreCase("h")) return 4;
        if(letter.equalsIgnoreCase("i")) return 1;
        if(letter.equalsIgnoreCase("j")) return 8;
        if(letter.equalsIgnoreCase("k")) return 5;
        if(letter.equalsIgnoreCase("l")) return 1;
        if(letter.equalsIgnoreCase("m")) return 3;
        if(letter.equalsIgnoreCase("n")) return 1;
        if(letter.equalsIgnoreCase("o")) return 1;
        if(letter.equalsIgnoreCase("p")) return 3;
        if(letter.equalsIgnoreCase("q")) return 10;
        if(letter.equalsIgnoreCase("r")) return 1;
        if(letter.equalsIgnoreCase("s")) return 1;
        if(letter.equalsIgnoreCase("t")) return 1;
        if(letter.equalsIgnoreCase("u")) return 1;
        if(letter.equalsIgnoreCase("v")) return 4;
        if(letter.equalsIgnoreCase("w")) return 4;
        if(letter.equalsIgnoreCase("x")) return 8;
        if(letter.equalsIgnoreCase("y")) return 4;
        if(letter.equalsIgnoreCase("z")) return 10;
        return 0;
    }
    
    
    @Override
    public String toString() {
        return letter;
    }
        
    
}

