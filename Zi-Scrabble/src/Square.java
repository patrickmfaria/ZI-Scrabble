
public class Square {
    public SquareType type;
    public Tile tile;
    
    public Square() {
        type = SquareType.nonpremium;
        tile = null;
    }
    
    public String toString() {
        String tmp ;
        if(tile != null) tmp = tile.toString();
        else tmp = "(T=NULL)";
        return tmp;
    }
}
