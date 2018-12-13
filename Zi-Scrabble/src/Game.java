import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Game {

   class CrossComparator implements Comparator<HumanMoving> {

        public int compare(HumanMoving o1, HumanMoving o2) {
            if(o1.col < o2.col) return -1;
            else if (o1.col == o2.col) return 0;
            else return 1;
        }

   }

   class DownComparator implements Comparator<HumanMoving> {

        public int compare(HumanMoving o1, HumanMoving o2) {
            if(o1.row < o2.row) return -1;
            else if (o1.row == o2.row) return 0;
            else return 1;
        }
   }

   public class HumanMoving {
        Tile tile;
        int row;
        int col;
        
        public HumanMoving(Tile tile, int row,  int col) {
            this.tile = tile;
            this.row = row;
            this.col = col;      
        }
    }

    public Rack humanRack = new Rack();
    public ArrayList<HumanMoving> bufMoving;
    public Board board;

    Comparator<HumanMoving> byCross = new CrossComparator();
    Comparator<HumanMoving> byDown = new DownComparator();

    public int humanScore = 0;

    public Game(Board board) {
        this.board = board;
    }

    // 1 - computer
    // 2 - human
    // 0 - Draw again
    int whoStarts(Tile t1, Tile t2) {
        
        if(t1.getLetter() == t2.getLetter())
            return 0;
        
        int d1 = 97 - (int)t1.getLetter();
        int d2 = 97 - (int)t2.getLetter();
        
        if(d1 > d2)
            return 2;
        else
            return 1;
        
    }

    public void insertHumanMoving(Tile tile, int row,  int col) {
        bufMoving.add(new HumanMoving(tile, row, col));
    }

    public void clearBuffer() {
        bufMoving = null;
        bufMoving = new ArrayList<HumanMoving>();
    }

    public String checkHumanMoving() {
        // Check if a cross moving
        int row = bufMoving.get(0).row;
        for(int i = 1; i< bufMoving.size(); i++) {
            if (bufMoving.get(i).row != row) // is not down
                i =  bufMoving.size(); // get out
            else if (bufMoving.get(i).row == row && i == bufMoving.size()-1)
                return "cross";
            if(i< bufMoving.size())
                row = bufMoving.get(i).row;
        }
        // Check if a down moving
        int col = bufMoving.get(0).col;
        for(int i = 1; i< bufMoving.size(); i++) {
            if (bufMoving.get(i).col != col) // is not cross
                i =  bufMoving.size(); // get out
            else if (bufMoving.get(i).col == col && i == bufMoving.size()-1)
                return "down";
            if(i< bufMoving.size()) 
                col = bufMoving.get(i).col;
        }
        // Diagonal or another kind of moving
        return "diag";
    }

    public void takeOutHumanMovingTiles() {
        for(int i = 0; i< bufMoving.size(); i++) {
            humanRack.addTile(bufMoving.get(i).tile);
            board.getSquare(bufMoving.get(i).row, bufMoving.get(i).col).tile = null;
        }
    }

    public void sortHumanMoving(String typeMoving) {
        if(typeMoving.equalsIgnoreCase("down"))
            Collections.sort(bufMoving, byDown);
        else
            Collections.sort(bufMoving, byCross);
    }


    public int countDownScoring(int row, int col) {
        int score =0;
        boolean doubleword = false;
        boolean tripleword = false;
        Square tmpSquareUp = board.getSquare(row-1, col);
        Square tmpSquareDown = board.getSquare(row+1, col);

        if(tmpSquareUp != null && tmpSquareDown != null) {
            // if only has tiles down
            if(tmpSquareDown.tile != null &&  tmpSquareUp.tile == null ) {
                Square tmpSquare;
                int crow = row;
                do {
                    tmpSquare =  board.getSquare(crow,col );
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        score = score + getScore(crow, col, tmpSquare.tile);
                        if( board.getSquare(crow, col).type == SquareType.doubleword)
                            doubleword  = true;
                        if( board.getSquare(crow, col).type == SquareType.tripleword)
                            tripleword  = true;
                    crow++;
                } while (tmpSquare.tile != null);
                if(doubleword)
                    score = score * 2;
                if(tripleword)
                    score = score * 3;
                return score;
            }
            // if only has tiles up
            if(tmpSquareDown.tile == null &&  tmpSquareUp.tile != null ) {
                Square tmpSquare;
                int crow = row;
                do {
                    tmpSquare =  board.getSquare(crow, col );
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        score = score + getScore(crow, col, tmpSquare.tile);
                        if( board.getSquare(crow, col).type == SquareType.doubleword)
                            doubleword  = true;
                        if( board.getSquare(crow, col).type == SquareType.tripleword)
                            tripleword  = true;
                    crow--;
                } while (tmpSquare.tile != null);
                if(doubleword)
                    score = score * 2;
                if(tripleword)
                    score = score * 3;
                return score;
            }
            // if has tiles up and down
            if(tmpSquareDown.tile != null &&  tmpSquareUp.tile != null ) {
                // get at Up
                Square tmpSquare;
                int crow = row;
                do {
                    tmpSquare =  board.getSquare(crow, col );
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        score = score + getScore(crow, col, tmpSquare.tile);
                        if( board.getSquare(crow, col).type == SquareType.doubleword)
                            doubleword  = true;
                        if( board.getSquare(crow, col).type == SquareType.tripleword)
                            tripleword  = true;
                    crow--;
                } while (tmpSquare.tile != null);
                if(doubleword)
                    score = score * 2;
                if(tripleword)
                    score = score * 3;

                //get at down
                crow = row+1;
                tmpSquare = null;
                do {
                    tmpSquare =  board.getSquare(crow, col );
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                         score = score + getScore(crow, col, tmpSquare.tile);
                        if( board.getSquare(crow, col).type == SquareType.doubleword)
                            doubleword  = true;
                        if( board.getSquare(crow, col).type == SquareType.tripleword)
                            tripleword  = true;

                    crow++;
                } while (tmpSquare.tile != null);

                if(doubleword)
                    score = score * 2;
                if(tripleword)
                    score = score * 3;
                return score;
            }
            if(tmpSquareDown.tile == null &&  tmpSquareUp.tile == null ) {
                return score;
            }

        }
        return score;
    }

    public int countCrossScoring(int row, int col) {
        int score =0;
        boolean doubleword = false;
        boolean tripleword = false;
        Square tmpSquareUp = board.getSquare(row, col-1);
        Square tmpSquareDown = board.getSquare(row, col+1);

        if(tmpSquareUp != null && tmpSquareDown != null) {
            // if only has tiles down
            if(tmpSquareDown.tile != null &&  tmpSquareUp.tile == null ) {
                Square tmpSquare;
                int ccol = col;
                do {
                    tmpSquare =  board.getSquare(row,ccol );
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        score = score + getScore(row, ccol, tmpSquare.tile);
                        if( board.getSquare(row, ccol).type == SquareType.doubleword)
                            doubleword  = true;
                        if( board.getSquare(row, ccol).type == SquareType.tripleword)
                            tripleword  = true;
                    ccol++;
                } while (tmpSquare.tile != null);
                if(doubleword)
                    score = score * 2;
                if(tripleword)
                    score = score * 3;
                return score;
            }
            // if only has tiles up
            if(tmpSquareDown.tile == null &&  tmpSquareUp.tile != null ) {
                Square tmpSquare;
                int ccol = col;
                do {
                    tmpSquare =  board.getSquare(row, ccol );
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        score = score + getScore(row, ccol, tmpSquare.tile);
                        if( board.getSquare(row, ccol).type == SquareType.doubleword)
                            doubleword  = true;
                        if( board.getSquare(row, ccol).type == SquareType.tripleword)
                            tripleword  = true;
                    ccol--;
                } while (tmpSquare.tile != null);
                if(doubleword)
                    score = score * 2;
                if(tripleword)
                    score = score * 3;
                return score;
            }
            // if has tiles up and down
            if(tmpSquareDown.tile != null &&  tmpSquareUp.tile != null ) {
                // get at Up
                Square tmpSquare;
                int ccol = col;
                do {
                    tmpSquare =  board.getSquare(row, ccol );
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        score = score + getScore(row, ccol, tmpSquare.tile);
                        if( board.getSquare(row, ccol).type == SquareType.doubleword)
                            doubleword  = true;
                        if( board.getSquare(row, ccol).type == SquareType.tripleword)
                            tripleword  = true;
                    ccol--;
                } while (tmpSquare.tile != null);
                if(doubleword)
                    score = score * 2;
                if(tripleword)
                    score = score * 3;

                //get at down
                ccol = col+1;
                tmpSquare = null;
                do {
                    tmpSquare =  board.getSquare(row, ccol );
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                         score = score + getScore(row, ccol, tmpSquare.tile);
                        if( board.getSquare(row, ccol).type == SquareType.doubleword)
                            doubleword  = true;
                        if( board.getSquare(row, ccol).type == SquareType.tripleword)
                            tripleword  = true;

                    ccol++;
                } while (tmpSquare.tile != null);

                if(doubleword)
                    score = score * 2;
                if(tripleword)
                    score = score * 3;
                return score;
            }
            if(tmpSquareDown.tile == null &&  tmpSquareUp.tile == null ) {
                return score;
            }

        }
        return score;
    }

    public int getScore(int row, int col, Tile tile) {
        int points = 0;

        Tile tmp = tile;
        points = points + tmp.getValue();
        SquareType st = board.getSquare(row, col).type;
        if( st == SquareType.doubleletter)
            points = points + tmp.getValue() * 2;
        if( st == SquareType.tripleletter)
            points = points + tmp.getValue() * 3;

        return points;
    }

}
