

public class Lexicon {

    public State mainState;
    
    public Lexicon() {
        mainState = new State();
    }
           
    public void insertWord(String word)
    {
        char letter;
        int found;
        State actualState;
        
        actualState = mainState;
        for(int i = 0; i<word.length(); i++) {
            letter = word.charAt(i);
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
