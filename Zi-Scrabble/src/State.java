
import java.util.ArrayList;
import java.util.List;


public class State {
    public boolean terminal;
    public List<Transition> transitions;

    public State() {
        terminal = false;
        transitions = new ArrayList<Transition>();
    }
    
    @Override
    public String toString() {
        String ret = "";
        for(int i = 0; i< transitions.size(); i++)
            ret = ret + transitions.get(i).toString() + " ";
        return ret;
    }
    
    
}
