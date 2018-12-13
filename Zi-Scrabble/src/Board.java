
import java.util.ArrayList;

public class Board {

    // ROW x COL
    private Square[][] board = new Square[15][15];
    private int lastRowMoving;
    private int lastColMoving;
    private int firstRowMoving;
    private int firstColMoving;
    
    public Board () {

        lastRowMoving = -1;
        lastColMoving = -1;

        for(int i = 0; i<15 ; i++)
            for(int j =0; j<15; j++)
            {
                board[i][j] = new Square();
                board[i][j].type = SquareType.nonpremium;
                board[i][j].tile = null;
            }
        
        // triple words
        board[0][0].type = SquareType.tripleletter;
        board[0][7].type = SquareType.tripleletter;
        board[0][14].type = SquareType.tripleletter;
        board[7][0].type = SquareType.tripleletter;
        board[14][0].type = SquareType.tripleletter;
        board[7][14].type = SquareType.tripleletter;
        board[14][14].type = SquareType.tripleletter;
        board[14][7].type = SquareType.tripleletter;
        board[7][7].type = SquareType.tripleletter;
        
        // double words
        board[1][1].type = SquareType.doubleword;
        board[2][2].type = SquareType.doubleword;
        board[3][3].type = SquareType.doubleword;
        board[4][4].type = SquareType.doubleword;
        board[1][13].type = SquareType.doubleword;
        board[2][12].type = SquareType.doubleword;
        board[3][11].type = SquareType.doubleword;
        board[4][10].type = SquareType.doubleword;
        board[10][4].type = SquareType.doubleword;
        board[11][3].type = SquareType.doubleword;
        board[12][2].type = SquareType.doubleword;
        board[13][1].type = SquareType.doubleword;
        board[10][10].type = SquareType.doubleword;
        board[11][11].type = SquareType.doubleword;
        board[12][12].type = SquareType.doubleword;
        board[13][13].type = SquareType.doubleword;
        
        // triple letters
        board[1][5].type = SquareType.tripleletter;
        board[1][9].type = SquareType.tripleletter;
        board[13][5].type = SquareType.tripleletter;
        board[13][9].type = SquareType.tripleletter;
        board[5][1].type = SquareType.tripleletter;
        board[9][1].type = SquareType.tripleletter;
        board[5][13].type = SquareType.tripleletter;
        board[9][13].type = SquareType.tripleletter;
        board[5][5].type = SquareType.tripleletter;
        board[5][9].type = SquareType.tripleletter;
        board[9][5].type = SquareType.tripleletter;
        board[9][9].type = SquareType.tripleletter;
        
        // double letter
        board[0][3].type = SquareType.doubleletter;
        board[0][11].type = SquareType.doubleletter;
        board[14][3].type = SquareType.doubleletter;
        board[14][11].type = SquareType.doubleletter;
        board[3][0].type = SquareType.doubleletter;
        board[11][0].type = SquareType.doubleletter;
        board[3][14].type = SquareType.doubleletter;
        board[11][14].type = SquareType.doubleletter;
        board[2][6].type = SquareType.doubleletter;
        board[2][8].type = SquareType.doubleletter;
        board[3][7].type = SquareType.doubleletter;
        board[12][6].type = SquareType.doubleletter;
        board[12][8].type = SquareType.doubleletter;
        board[11][7].type = SquareType.doubleletter;
        board[6][2].type = SquareType.doubleletter;
        board[8][2].type = SquareType.doubleletter;
        board[7][3].type = SquareType.doubleletter;
        board[6][12].type = SquareType.doubleletter;
        board[8][12].type = SquareType.doubleletter;
        board[7][11].type = SquareType.doubleletter;
        board[6][6].type = SquareType.doubleletter;
        board[6][8].type = SquareType.doubleletter;
        board[8][6].type = SquareType.doubleletter;
        board[8][8].type = SquareType.doubleletter;
    }
    
    void startMoving() {
        firstRowMoving = -1;
        firstColMoving = -1;
        lastRowMoving = -1;
        lastColMoving = -1;
    }
    
    boolean insertTile(int row, int col, Tile tile) {
        
        // Check if the location is empty
        if(board[row][col].tile != null)
            return false;
        
        // Check if diagonal 
        if(lastRowMoving != -1 && lastColMoving != -1)
            if((row != lastRowMoving +1 && col != lastColMoving) ||(row != lastRowMoving && col != lastColMoving +1))
                return false;
        
        // Assing
        board[row][col].tile = tile;
        
        if(firstRowMoving == -1 && firstColMoving == -1)
            firstRowMoving = row;
            firstColMoving = col;
            
        // Hold last moving
        lastRowMoving = row;
        lastColMoving = col;
        
        return true;
    }  

    // Get all acnhors from the board
    ArrayList<Anchor> getAchors() {

        // Will hold the found anchors
        ArrayList<Anchor> achors = new ArrayList<Anchor>();

        int freeUp; // How many squares free to up
        int freeDown; // How many squares free to down
        int freeRight; // How many squares free to right
        int freeLeft; // // How many squares free to down

        // Scan the whole board
        for(int i= 0; i<15;i++)
            for(int j= 0; j<15;j++) {
                freeUp = 0;
                freeDown = 0;
                freeRight = 0;
                freeLeft = 0;

                // if finds a tile check if it is an anchor
                if(board[i][j].tile != null) {
                    // Counts number rooms free up (consecutive)
                    for(int k=i-1;k>=0;k--)
                        if(board[k][j].tile == null)
                           freeUp++;
                        else
                           k=-1; // break out loop
                                        
                    // Counts number rooms free left (consecutive)
                    for(int k=j-1;k>=0;k--)
                        if(board[i][k].tile == null)
                           freeLeft++;
                        else
                           k=-1; // break out loop

                    // Counts number rooms free down (consecutive)
                    for(int k=i+1;k<15;k++)
                        if(board[k][j].tile == null)
                           freeDown++;
                        else
                           k=16; // break out loop

                    // Counts number rooms free right (consecutive)
                    for(int k=j+1;k<15;k++)
                        if(board[i][k].tile == null)
                           freeRight++;
                        else
                           k=16; // break out loop

                    // new Achor
                    if(freeUp > 2 || freeDown > 2 || freeRight > 2 || freeLeft > 2){
                        Anchor tmpanchor = new Anchor(i,j, freeUp, freeDown, freeRight, freeLeft);
                        achors.add(tmpanchor);
                    }  
                }
            }
        return achors;
        
    }
    
   Word getWordfromAnchor(Anchor currentAnchor, WordPos wp) {
       
       Word retWord = new Word();
       
       if(wp == WordPos.Rightwards) {
           int col = currentAnchor.col;
           Tile tmpTile;
           do {               
               tmpTile = board[currentAnchor.row][col].tile;
               if(tmpTile != null) {
                    retWord.addLetter(tmpTile);
                    col++;                   
               }
           } while(tmpTile != null && col < 15);
       }
       
       if(wp == WordPos.Leftwards) {
           int col = currentAnchor.col;
           Tile tmpTile;
           do {               
               tmpTile = board[currentAnchor.row][col].tile;
               if(tmpTile != null) {
                    retWord.addLetter(tmpTile);
                    col--;                   
               }
           } while(tmpTile != null && col >=0);
       }
       
       if(wp == WordPos.Upwards) {
           int row = currentAnchor.row;
           Tile tmpTile;
           do {               
               tmpTile = board[row][currentAnchor.col].tile;
               if(tmpTile != null) {
                    retWord.addLetter(tmpTile);
                    row--;                   
               }
           } while(tmpTile != null && row >=0);
       }
       
       if(wp == WordPos.Downwards) {
           int row = currentAnchor.row;
           Tile tmpTile;
           do {               
               tmpTile = board[row][currentAnchor.col].tile;
               if(tmpTile != null) {
                    retWord.addLetter(tmpTile);
                    row++;                   
               }
           } while(tmpTile != null && row <15);
       }
       
       return retWord;
   }
      
   Square getSquare(int row, int col) {
       if(row<0 || col >= 15 || col < 0 || row>=15)
           return null;
       return board[row][col];
   }

   SquareType getSquareType(int row, int col) {
       return board[row][col].type;
   }
   void printBoard() {
       System.out.print(" ");
       for(int k=0;k<15;k++)
           if(k<9)
               System.out.print("|-"+String.valueOf(k+1)+"-");
           else
               System.out.print("|-"+String.valueOf(k+1-10)+"-");
       System.out.print("|");
       System.out.println();
       for (int i =0;i <15;i++) {
           if(i<9)
               System.out.print(String.valueOf(i+1));
           else
               System.out.print(String.valueOf(i+1-10));
           for(int j=0;j<15;j++) {
              if(getSquare(i,j).tile != null) 
                  System.out.print("| " + getSquare(i,j).tile.getLetter() + " ");
              else System.out.print("|   ");
           }
           System.out.println("|");
       }
       System.out.print(" ");
       for(int k=0;k<15;k++)
           System.out.print("|---");
       System.out.println("|");
   }
}
