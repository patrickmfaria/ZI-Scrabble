
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class Main {

    public static void main(String[] args) {
        
        Lexicon lexicon = new Lexicon();
                
        try {
            BufferedReader in = new BufferedReader(new FileReader("finaldict.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                lexicon.insertWord(str);
            }
            in.close();
        } catch (IOException e) {
        }        
        
        lexicon.print();
    }

}
