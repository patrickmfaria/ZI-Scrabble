
import java.util.ArrayList;

public class Word {
    private ArrayList<Tile> word;
    
   public Word () {
        word = new ArrayList<Tile>() ;
    }
   
   public Word(Tile tile) {
        word = new ArrayList<Tile>() ;
        word.add(tile);
   }
        
   public Word(Word word) {
        this.word = new ArrayList<Tile>() ;
        for(int i=0;i<word.lenght();i++)
            this.word.add(word.getTile(i));
   }
    void addLetter(Tile tile) {
        word.add(tile);
    }
    
    Tile getTile(int index) {
        return word.get(index);
    }
    
    int lenght() {
        return word.size();
    }
    
    String getLetter(int index) {
        return word.get(index).toString();
    }
    
    int getScore() {
        int ret = 0;
        for(int i = 0; i< word.size(); i++)
            ret = ret + word.get(i).getValue();
        return ret;     
    }
    
    void clear() {
        word.clear();
    }
    
    @Override
    public String toString() {
        String ret = "";
        for(int i = 0; i< word.size(); i++)
            ret = ret + word.get(i).getLetter();
        return ret;
    }

    char removeLastLetter() {
        return word.remove(word.size()-1).getLetter();
    }
    
    void Reverse() {
        ArrayList<Tile> tmpword = new ArrayList<Tile>();
        ArrayList<Tile> tmp = word;

        for(int i = word.size()-1; i>=0; i--)
            tmpword.add(word.get(i));

        tmp = null;
        word = tmpword;        
    }

}
