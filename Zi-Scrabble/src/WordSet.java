
import java.util.ArrayList;

public class WordSet {

     ArrayList<Word> words = new ArrayList<Word>();
     String ID;  // Not mandadtory
     
    void insertWord(Word word) {
        boolean found = false;
        for(int i = 0; i< words.size();i++) 
            if(words.get(i).toString().equalsIgnoreCase(word.toString()))
                found = true;
        
        if (!found) {
            Word newWord = new Word();
            for(int i=0;i< word.lenght();i++)
                newWord.addLetter(word.getTile(i));
            words.add(newWord);
        }
            
    }
 
    WordSet Filter(Word word) {
        WordSet retWordSet = new WordSet();
        for(int i = 0; i< words.size();i++)
            if(!words.get(i).toString().equalsIgnoreCase(word.toString()))
                if(words.get(i).toString().contains(word.toString()))
                    retWordSet.insertWord(words.get(i));
        return retWordSet;
    }
    
    WordSet Filter(Tile tile) {
        WordSet retWordSet = new WordSet();
        for(int i = 0; i< words.size();i++)
            if(words.get(i).toString().contains(tile.toString()))
                retWordSet.insertWord(words.get(i));
        return retWordSet;
    }

    int size() {
        return words.size();
    } 
    
    Word getWord(int index) {
        return words.get(index);
    }
    
    String getID() {
        return ID;
    }
    
    void setID(String id) {
        this.ID = id;
    }
    
    @Override
    public String toString() {
        String ret = "";
        for(int i = 0; i< words.size(); i++)
            ret = ret + words.get(i).toString() + " ";
        if(ID != null)
            ret = ID + " - " + ret;
        return ret;
    }        
    
}
