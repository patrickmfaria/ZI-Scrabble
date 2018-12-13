
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Lexicon {

    public State mainState;
    private WordSet wordset;
    
    public Lexicon() {
        mainState = new State();
    }
        
    void loadDictionary() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("finaldict.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                insertWord(str);
            }
            in.close();
        } catch (IOException e) {
        }              
    }
    
    boolean hasTrasition(State state, char letter) {
        for(int i=0;i<state.transitions.size();i++) 
            if(state.transitions.get(i).letter == letter)
                return true;
        return  false;
    }
    
     Transition getTransaction(State state, char letter) {
        for(int i=0;i<state.transitions.size();i++) 
            if(state.transitions.get(i).letter == letter)
                return state.transitions.get(i);
        return  null;
         
     }
    
    State getNextState(State state, char letter) {
        for(int i=0;i<state.transitions.size();i++) 
            if(state.transitions.get(i).letter == letter)
                return state.transitions.get(i).nextState;
        return  null;
    }
    
    public void insertWord(String word)
    {
        char letter;
        int found;
        State actualState;
        
        actualState = mainState;
        for(int i = 0; i<word.length(); i++) {
            letter = word.toLowerCase().charAt(i);
            found = -1;
            for(int j=0; j< actualState.transitions.size();j++) {
                if(actualState.transitions.get(j).letter == letter)
                    found = j;
            }                
            if(found==-1) {
                Transition newTr = new Transition();
                newTr.letter = letter;
                State newState = new State();

                if(i==word.length()-1)
                    newState.terminal = true;

                newTr.nextState = newState;
                actualState.transitions.add(newTr);
                actualState = newState;
            } else {
                actualState = actualState.transitions.get(found).nextState;
            }
                
        }
            
    }
    
    public WordSet getWords(Rack rack) {
        Word word = new Word();
        wordset = new WordSet();
        _getWords(rack, mainState, word) ;
        return wordset;
    }

    private void _getWords(Rack rack, State currentState, Word word) {
        ArrayList<Transition> transitions = new ArrayList<Transition>();
        // get all transactions from the current state which have correspondent tiles on the rack
        for(int i =1;i<=rack.numberTiles();i++) {
            // Only get available tiles tile 
            if(rack.getTile(i).getReserved().length()<=0) {
                Transition tmpTrans;
                tmpTrans = getTransaction(currentState, rack.getTile(i).getLetter());
                // Don't allow repeated tiles
                if(tmpTrans != null) {
                    boolean found = false;
                    for(int j =0;j<transitions.size();j++)
                        if(transitions.get(j).letter ==  tmpTrans.letter)
                            found = true;
                    if(! found) transitions.add(tmpTrans);
                }
            }
        }  
        // if there is no transitions fot the partial word get out
        if(transitions.size()<=0) {
            return;
        }
        // Scan all selected transitions
        int trasitionCounter = 0;
        while (trasitionCounter < transitions.size()) {
            // Mark all tiles with the same letter as unavailable
            rack.markTileUnavailable(transitions.get(trasitionCounter).letter);
            // transition doesn't end up on a terminal state
            // Add letter to word 
            word.addLetter(new Tile(String.valueOf(transitions.get(trasitionCounter).letter)));
            State nextState = transitions.get(trasitionCounter).nextState;
            if(!nextState.terminal) {
                _getWords(rack, transitions.get(trasitionCounter).nextState, word);
                // Get back the tile to rack before try another combination
                char letter = word.removeLastLetter();
                rack.markTileAvailable(letter);
            } else { // Found word
                wordset.insertWord(word);
                // set next state
                _getWords(rack, transitions.get(trasitionCounter).nextState, word);
                // Get back the tile to rack before try another combination
                char letter = word.removeLastLetter();
                rack.markTileAvailable(letter);                            
            }
            trasitionCounter++;
        }        
    }        
        
    public boolean findWord(String word) {

        State state = mainState;
        int found;
        for(int i=0; i< word.length(); i++) {
            found = -1;
            for(int j=0;j<state.transitions.size();j++)  // scann the transitions                
                if(state.transitions.get(j).letter == word.charAt(i))
                    found = j;
            if(found != - 1) {
                state = state.transitions.get(found).nextState;
            }
            else
                return false;
        }

        if(state.terminal)
            return true;
        else
            return false;
    }
    
    public void print() {
        _print(mainState, "");
    }
           
    private String  _print(State state, String buf) {
        if(state.terminal && state.transitions.size()>0) {
              System.out.println(buf);
        }
        if(state.terminal && state.transitions.size()==0) {
            System.out.println(buf);
            return buf;
        }
        for(int i = 0; i<state.transitions.size(); i++) {
            buf = buf + state.transitions.get(i).letter;
            if(state.transitions.get(i).nextState != null) {
                buf = _print(state.transitions.get(i).nextState, buf);
            } 
        }
        return buf;
    }
}
