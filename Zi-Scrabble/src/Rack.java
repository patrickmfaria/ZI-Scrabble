
import java.util.ArrayList;

public class Rack {

    private ArrayList<Tile> rack;
    
    public Rack () {
        rack = new ArrayList<Tile>() ;
    }
    // ranges starting from 1 not zero
    Tile getTile(int index) {
        return rack.get(index-1);
    }
    
    void addTile(Tile tile) {
            rack.add(tile);
    }
    
    void addWord(Word word) {
        for(int i = 0; i< word.lenght();i++)
            addTile(word.getTile(i));
    }
    
    boolean removeTile(Tile tile) {
        if(!rack.isEmpty())
            return rack.remove(tile);
        return false;
    }

    
    Tile removeTile(int index) {
        if(index< rack.size())
            return rack.remove(index);
        return null;
    }

    int numberTiles() {
        return rack.size();
    }
    
    int getTile(char letter) {
        int ret = -1;
        for(int i = 0; i< rack.size(); i++)
            if(letter == rack.get(i).getLetter())
                ret = i;
        return ret;
    }
    
    @Override
    
    // only available ones
    public String toString() {
        String ret = "";
        for(int i = 0; i< rack.size(); i++)
            if(rack.get(i).getReserved().length()<=0)
                ret = ret + rack.get(i).getLetter();
        return ret;
    }
    
    // Mark te first tile with the same letter as available
    void markTileAvailable(char letter) {
        for(int i = 0; i< rack.size(); i++)
            if(rack.get(i).getLetter() == letter && rack.get(i).getReserved().equalsIgnoreCase("X"))
            {
                rack.get(i).setReserved("");
                return;
            }
    }
    
    // Mark te first tile with the same letter as Unavailable
    void markTileUnavailable (char letter) {
        for(int i = 0; i< rack.size(); i++)
            if(rack.get(i).getLetter() == letter && rack.get(i).getReserved().length()<=0)
            {
                rack.get(i).setReserved("X");
                return;
            }
        
    }
    
    Rack copy() {
        Rack newrack = new Rack();
        for(int i=0;i<this.rack.size();i++)
            newrack.addTile(this.rack.get(i));
        return newrack;
    }
    
}
