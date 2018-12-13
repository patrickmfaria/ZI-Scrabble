
import java.util.ArrayList;


public class Anchor {
    int row;
    int col;
    int freeUp;
    int freeDown;
    int freeRight;
    int freeLeft;

    // All possibles wrods we generate from that anchor
    ArrayList<WordSet> wordSetList = new ArrayList<WordSet>();
    
    
    public Anchor(int row, int col, int freeUp, int freeDown, int freeRight ,int freeLeft) {
        this.col = col;
        this.row = row;
        this.freeUp = freeUp;
        this.freeDown = freeDown;
        this.freeRight = freeRight;
        this.freeLeft = freeLeft;        
    }
    
    public String toString() {
        String tmp;
        tmp = "Row= " + String.valueOf(row) + " Col= " + String.valueOf(col) + " up - " + String.valueOf(freeUp) + " down - " + String.valueOf(freeDown) + " left - " + String.valueOf(freeLeft) + " right - " + String.valueOf(freeRight);
        return tmp;
    }
    
}
