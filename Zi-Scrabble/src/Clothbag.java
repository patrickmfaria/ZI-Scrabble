
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
            for(int j=0;j<98; j++) {
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
    
    public void insertTile(Tile tile) {
            tiles.add(tile);        
    }
    
    public Clothbag() {
        
        generator = new Random();
            
        insertTile(new Tile("a"), 9);
        insertTile(new Tile("b"), 2);
        insertTile(new Tile("c"), 2);
        insertTile(new Tile("d"), 4);
        insertTile(new Tile("e"), 12);
        insertTile(new Tile("f"), 2);
        insertTile(new Tile("g"), 3);
        insertTile(new Tile("h"), 2);
        insertTile(new Tile("i"), 9);
        insertTile(new Tile("j"), 1);
        insertTile(new Tile("k"), 1);
        insertTile(new Tile("l"), 4);
        insertTile(new Tile("m"), 2);
        insertTile(new Tile("n"), 6);
        insertTile(new Tile("o"), 8);
        insertTile(new Tile("p"), 2);
        insertTile(new Tile("q"), 1);
        insertTile(new Tile("r"), 6);
        insertTile(new Tile("s"), 4);
        insertTile(new Tile("t"), 6);
        insertTile(new Tile("u"), 4);
        insertTile(new Tile("v"), 2);
        insertTile(new Tile("w"), 2);
        insertTile(new Tile("x"), 1);
        insertTile(new Tile("y"), 2);
        insertTile(new Tile("z"), 1);
        //insertTile(new Tile("0"), 2);// joker
    }
    
}
