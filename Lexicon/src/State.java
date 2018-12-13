
import java.util.ArrayList;
import java.util.List;


public class State {
    public boolean terminal;
    public List<Transition> transitions;

    public State() {
        terminal = false;
        transitions = new ArrayList<Transition>();
    }
}
