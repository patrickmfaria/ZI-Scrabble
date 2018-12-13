
import java.util.ArrayList;
import java.util.Random;

public class Clothbag {

    ArrayList<Tile> tiles = new ArrayList<Tile>();
    Random generator; 

    public int getNumberTiles() {
        return tiles.size();
    }
    
    private void swap(int source, int dest) {
        Tile tmpTile;
        tmpTile = tiles.get(source);
        tiles.set(source, tiles.get(dest));
        tiles.set(dest, tmpTile);
    }
    
    public void Shuffle() {
        int n;
        
        for(int i = 0; i<8 ; i++ )
            for(int j=0;j<100; j++) {
                n = generator.nextInt(tiles.size());
                swap(j,n);
            }
                
    }
    
    public Tile drawOneTile() {
        Tile tmpTile ;
        int n = generator.nextInt(tiles.size());
        tmpTile = tiles.get(n);
        tiles.remove(n);
        return tmpTile;
    }
    
    private void insertTile(Tile tile, int qty) {
        for(int i=0;i<qty;i++)
            tiles.add(tile);
    }
    
    public Clothbag() {
        
        generator = new Random();
            
        insertTile(new Tile("A"), 9);
        insertTile(new Tile("B"), 2);
        insertTile(new Tile("C"), 2);
        insertTile(new Tile("D"), 4);
        insertTile(new Tile("E"), 12);
        insertTile(new Tile("F"), 2);
        insertTile(new Tile("G"), 3);
        insertTile(new Tile("H"), 2);
        insertTile(new Tile("I"), 9);
        insertTile(new Tile("J"), 1);
        insertTile(new Tile("K"), 1);
        insertTile(new Tile("L"), 4);
        insertTile(new Tile("M"), 2);
        insertTile(new Tile("N"), 6);
        insertTile(new Tile("O"), 8);
        insertTile(new Tile("P"), 2);
        insertTile(new Tile("Q"), 1);
        insertTile(new Tile("R"), 6);
        insertTile(new Tile("S"), 4);
        insertTile(new Tile("T"), 6);
        insertTile(new Tile("U"), 4);
        insertTile(new Tile("V"), 2);
        insertTile(new Tile("W"), 2);
        insertTile(new Tile("X"), 1);
        insertTile(new Tile("Y"), 2);
        insertTile(new Tile("Z"), 1);
        insertTile(new Tile("XX"), 2);
    }
    
}
