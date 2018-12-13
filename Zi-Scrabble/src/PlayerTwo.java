
import java.util.ArrayList;
public class PlayerTwo {
        
    Rack rack;
    protected Lexicon lexicon;
    protected Board board;

    public int Score;

    @Override
    public String toString() {
        String ret = "";
        for(int i = 0; i< rack.numberTiles(); i++)
            ret = ret + rack.getTile(i+1).getLetter();
        return ret;
    }

    // Choosing the best scoring
    int maxPoints;
    Anchor maxAnchor;
    String maxType;
    Word maxBoardWord;
    Word maxWord;

    int extraPoints;


    public PlayerTwo(Lexicon lexicon, Board board) {
        this.rack = new Rack();
        this.lexicon = lexicon;
        this.board = board;
        this.Score = 0;
    }

    
    // For each anchor form all possible words using the anchor tile PLUS the in hands tiles (rack)
    public void formWordsByAnchors(ArrayList<Anchor> anchors) {
        Anchor currentAnchor = null;
        Rack copyRack = null;
        Word word = null;

        // Scan all the found anchors
        for(int i =0 ;i<anchors.size(); i++) {
            currentAnchor = anchors.get(i);
            // by definition an anchor must have at least one side empty
            if(currentAnchor.freeLeft != 0 || currentAnchor.freeRight != 0) {

                // Is there rightwards word?
                if(currentAnchor.freeLeft > 2 && currentAnchor.freeRight==0) {
                    // Get across word (rightward word)
                    word = board.getWordfromAnchor(currentAnchor, WordPos.Rightwards);
                    // clone the current rack
                    copyRack = rack.copy();
                    // add the word to the cloned rack
                    copyRack.addWord(word);
                }

                // is there a leftwards word?
                if(currentAnchor.freeLeft ==0 && currentAnchor.freeRight>2) {
                    // Get across word (leftward word)
                    word = board.getWordfromAnchor(currentAnchor, WordPos.Leftwards);
                    copyRack = rack.copy();
                    copyRack.addWord(word);
                    word.Reverse();
                }

                // is just one alone tile right and left wards
                if(currentAnchor.freeLeft >2 && currentAnchor.freeRight>2) {
                    copyRack = rack.copy();
                    word = new Word(board.getSquare(currentAnchor.row,currentAnchor.col).tile);
                    copyRack.addWord(word);
                }

                // Find all words to the new rack
                //System.out.println(copyRack.toString());

                if(copyRack != null) {
                    WordSet wordSetMaster =  lexicon.getWords(copyRack);
                    // Select only the words which contains the word found beforewards
                    WordSet wordSetTmp = wordSetMaster.Filter(word);
                    // Set the ID
                    wordSetTmp.setID("Cross");
                    // insert the words slected to the anchor wordset under a specific id
                    currentAnchor.wordSetList.add(wordSetTmp);
                    copyRack = null;
                    wordSetMaster = null;
                    word = null;
                }

            }
         }

        currentAnchor = null;
        copyRack = null;
        word = null;

        for(int i =0 ;i<anchors.size(); i++) {
            currentAnchor = anchors.get(i);
            //System.out.println(currentAnchor.toString());
            if(currentAnchor.freeUp != 0 || currentAnchor.freeDown != 0) {
                
                // Is there rightwards word?
                if(currentAnchor.freeUp > 2 && currentAnchor.freeDown==0) {
                    // Get across word (rightward word)
                    word = board.getWordfromAnchor(currentAnchor, WordPos.Downwards);
                    // clone the current rack
                    copyRack = rack.copy();
                    // add the word to the cloned rack
                    copyRack.addWord(word);
                }

                // is there a leftwards word?
                if(currentAnchor.freeUp ==0 && currentAnchor.freeDown>2) {
                    // Get across word (leftward word)
                    word = board.getWordfromAnchor(currentAnchor, WordPos.Upwards);
                    copyRack = rack.copy();
                    copyRack.addWord(word);
                    word.Reverse();
                }

                // is just one alone tile right and left wards
                if(currentAnchor.freeUp >2 && currentAnchor.freeDown>2) {
                    copyRack = rack.copy();
                    word = new Word(board.getSquare(currentAnchor.row,currentAnchor.col).tile);
                    copyRack.addWord(word);
                }

                if(copyRack != null) {
                    // Find all words to the new rack
                    WordSet wordSetMaster =  lexicon.getWords(copyRack);
                    // Select only the words which contains the word found beforewards
                    WordSet wordSetTmp = wordSetMaster.Filter(word);
                    // Set the ID
                    wordSetTmp.setID("Down");
                    // insert the words slected to the anchor wordset under a specific id
                    currentAnchor.wordSetList.add(wordSetTmp);
                    copyRack = null;
                    wordSetMaster = null;
                    word = null;

                }
         }
        
       }
    }
    
    private boolean crossBorder(Word boardWord, Word word, Anchor anchor) {
        // Match words
        // Just get the position where word starts in currentword
        int indexpos = word.toString().indexOf(boardWord.toString());
        
        if(indexpos < 0 ) return true;
        
        // word at first
        if(word.lenght()- indexpos == boardWord.lenght()) 
            if(  anchor.col - (word.lenght()- boardWord.lenght()) < 0  )
                return true;
        
        // word at end
        if(indexpos == 0) 
            if( anchor.col + word.lenght() >= 15  )
                return true;
        
        // at middle
        if(indexpos > 0 && indexpos < word.lenght()-1) {
            if(anchor.col + indexpos + (word.lenght()-indexpos )  >=15)
                return true;
            if(anchor.col - indexpos < 0)
                return true;
        }
                
        return false;
        
    }
    
    private boolean downBorder(Word boardWord, Word word, Anchor anchor) {
        // Match words
        // Just get the position where word starts in currentword
        int indexpos = word.toString().indexOf(boardWord.toString());
        
        if(indexpos < 0 ) return true;
        
        // word at first
        if(word.lenght()- indexpos == boardWord.lenght()) 
            if(  anchor.row - (word.lenght()- boardWord.lenght()) < 0  )            
                return true;
        
        // word at end
        if(indexpos == 0) 
            if( anchor.row + word.lenght() >= 15  )
                return true;
        
        // at middle
        if(indexpos > 0 && indexpos < word.lenght()-1) {
            if(anchor.row + indexpos + (word.lenght()-indexpos )  >=15)
                return true;
            if(anchor.row - indexpos < 0)
                return true;
        }
                
        return false;
        
    }
    
    
    private boolean crossTouchAnotherTile (Word boardWord, Word word, Anchor anchor) {
        int steps;
        
        // Match words
        // Just get the position where word starts in currentword
        int indexpos = word.toString().indexOf(boardWord.toString());
        
        // word at first
        if(word.lenght()- indexpos == boardWord.lenght()) {
            steps = word.lenght()- boardWord.lenght();
            for(int i = 1; i <= steps; i++ ) {
                Tile tmpTile = board.getSquare( anchor.row, anchor.col - i).tile;
                if(tmpTile != null )
                    return true;
            }
        }

        // word at end
        else if(indexpos == 0) {
            steps =  word.lenght()-boardWord.lenght();
            for(int i = 1; i <= steps; i++ ) {
                Tile tmpTile = board.getSquare(anchor.row, anchor.col + boardWord.lenght() + (i-1) ).tile;
                if(tmpTile != null )
                    return true;
            }
        }
        
        // at middle
        else if(indexpos > 0 && indexpos < word.lenght()-1) {
             steps = (word.lenght() - indexpos) -  boardWord.lenght() ;
            for(int i = 1; i <= steps; i++ ) {
                Tile tmpTile = board.getSquare(anchor.row, anchor.col + boardWord.lenght() + (i-1) ).tile;
                if(tmpTile != null )
                    return true;
            }
            steps = indexpos ;
            for(int i = 1; i <= steps; i++ ) {
                Tile tmpTile = board.getSquare( anchor.row, anchor.col - i).tile;
                if(tmpTile != null )
                    return true;
            }
        }
        
        return false;
    }
    
    private boolean downTouchAnotherTile (Word boardWord, Word word, Anchor anchor) {
        int steps;
        
        // Match words
        // Just get the position where word starts in currentword
        int indexpos = word.toString().indexOf(boardWord.toString());
        
        // word at first
        if(word.lenght()- indexpos == boardWord.lenght()) {
            steps = word.lenght()- boardWord.lenght();
            for(int i = 1; i <= steps; i++ ) {
                Tile tmpTile = board.getSquare( anchor.row- i, anchor.col).tile;
                if(tmpTile != null )
                    return true;
            }
        }

        // word at end
        else if(indexpos == 0) {
            steps =  word.lenght()-boardWord.lenght();
            for(int i = 1; i <= steps; i++ ) {
                Tile tmpTile = board.getSquare(anchor.row + boardWord.lenght() + (i-1), anchor.col).tile;
                if(tmpTile != null )
                    return true;
            }
        }
        
        // at middle
        else if(indexpos > 0 && indexpos < word.lenght()-1) {
            steps = (word.lenght() - indexpos) -  boardWord.lenght() ;
            for(int i = 1; i <= steps; i++ ) {
                Tile tmpTile = board.getSquare(anchor.row+ boardWord.lenght() + (i-1), anchor.col ).tile;
                if(tmpTile != null )
                    return true;
            }
           steps = indexpos ;
            for(int i = 1; i <= steps; i++ ) {
                Tile tmpTile = board.getSquare(anchor.row- i, anchor.col ).tile;
                if(tmpTile != null )
                    return true;
            }
        }
        
        return false;
    }

    public Word getDownWordFromPos(int row, int col, Tile tile) {
        Word retWord = new Word();
        
        Square tmpSquareUp = board.getSquare(row-1, col);
        Square tmpSquareDown = board.getSquare(row+1, col);

        if(tmpSquareUp != null && tmpSquareDown != null) {
            // if only has tiles down
            if(tmpSquareDown.tile != null &&  tmpSquareUp.tile == null ) {
                retWord.addLetter(tile);
                Square tmpSquare;
                int crow = row+1;
                do {
                    tmpSquare =  board.getSquare(crow,col );
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        retWord.addLetter(tmpSquare.tile);
                    crow++;
                } while (tmpSquare.tile != null);
                return retWord;
            }
            // if only has tiles up
            if(tmpSquareDown.tile == null &&  tmpSquareUp.tile != null ) {
                retWord.addLetter(tile);
                Square tmpSquare;
                int crow = row-1;
                do {
                    tmpSquare =  board.getSquare(crow, col );
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        retWord.addLetter(tmpSquare.tile);
                    crow--;
                } while (tmpSquare.tile != null);
                retWord.Reverse();
                return retWord;
            }
            // if has tiles up and down            
            if(tmpSquareDown.tile != null &&  tmpSquareUp.tile != null ) {
                // get at Up
                retWord.addLetter(tile);
                Square tmpSquare;
                int crow = row-1;
                do {
                    tmpSquare =  board.getSquare(crow, col );
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        retWord.addLetter(tmpSquare.tile);
                    crow--;
                } while (tmpSquare.tile != null);
                retWord.Reverse();
                
                //get at down
                 crow = row+1;
                tmpSquare = null;
                do {
                    tmpSquare =  board.getSquare(crow, col );
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        retWord.addLetter(tmpSquare.tile);
                    crow++;
                } while (tmpSquare.tile != null);
                
                return retWord;
            }
            if(tmpSquareDown.tile == null &&  tmpSquareUp.tile == null ) {
                return retWord;
            }
        }
  
        // is at the up border
        if(tmpSquareUp == null && tmpSquareDown != null) {
            // if only has tiles down
            retWord.addLetter(tile);
            Square tmpSquare;
            int crow = row+1;
            do {
                tmpSquare =  board.getSquare(crow, col );
                if(tmpSquare == null) break;
                if(tmpSquare.tile != null)
                    retWord.addLetter(tmpSquare.tile);
                crow++;
            } while (tmpSquare.tile != null);
            return retWord;
        }
          
         // is at the down border
        if(tmpSquareUp != null && tmpSquareDown == null) {
            retWord.addLetter(tile);
            Square tmpSquare;
            int crow = row-1;
            do {
                tmpSquare =  board.getSquare(crow, col);
                if(tmpSquare == null) break;
                if(tmpSquare.tile != null)
                    retWord.addLetter(tmpSquare.tile);
                crow--;
            } while (tmpSquare.tile != null);
            retWord.Reverse();
            return retWord;
        }
        return retWord;
    }

    public Word getCrossWordFromPos(int row, int col,  Tile tile) {
        Word retWord = new Word();

        Square tmpSquareRight = board.getSquare(row, col+1);
        Square tmpSquareLeft = board.getSquare(row, col-1);

        if(tmpSquareRight != null && tmpSquareLeft != null) {
            // if only has tiles down
            if(tmpSquareRight.tile != null &&  tmpSquareLeft.tile == null ) {
                retWord.addLetter(tile);
                Square tmpSquare;
                int ccol = col+1;
                do {
                    tmpSquare =  board.getSquare(row, ccol);
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        retWord.addLetter(tmpSquare.tile);
                    ccol++;
                } while (tmpSquare.tile != null);
                return retWord;
            }
            // if only has tiles up
            if(tmpSquareRight.tile == null &&  tmpSquareLeft.tile != null ) {
                retWord.addLetter(tile);
                Square tmpSquare;
                int ccol = col-1;
                do {
                    tmpSquare =  board.getSquare(row, ccol);
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        retWord.addLetter(tmpSquare.tile);
                    ccol--;
                } while (tmpSquare.tile != null);
                retWord.Reverse();
                return retWord;
            }
            // if has tiles up and down
            if(tmpSquareRight.tile != null &&  tmpSquareLeft.tile != null ) {
                // get at Up
                retWord.addLetter(tile);
                Square tmpSquare;
                int ccol = col-1;
                do {
                    tmpSquare =  board.getSquare(row, ccol);
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        retWord.addLetter(tmpSquare.tile);
                    ccol--;
                } while (tmpSquare.tile != null);
                retWord.Reverse();

                //get at down
                ccol = col+1;
                tmpSquare = null;
                do {
                    tmpSquare =  board.getSquare(row, ccol);
                    if(tmpSquare == null) break;
                    if(tmpSquare.tile != null)
                        retWord.addLetter(tmpSquare.tile);
                    ccol++;
                } while (tmpSquare.tile != null);

                return retWord;
            }
        }

        // is at the up border
        if(tmpSquareRight == null && tmpSquareLeft != null) {
            // if only has tiles down
            retWord.addLetter(tile);
            Square tmpSquare;
            int ccol = col+1;
            do {
                tmpSquare =  board.getSquare(row, ccol);
                if(tmpSquare == null) break;
                if(tmpSquare.tile != null)
                    retWord.addLetter(tmpSquare.tile);
                ccol++;
            } while (tmpSquare.tile != null);
            return retWord;
        }

         // is at the down border
        if(tmpSquareLeft != null && tmpSquareRight == null) {
            retWord.addLetter(tile);
            Square tmpSquare;
            int ccol = col-1;
            do {
                tmpSquare =  board.getSquare(row, ccol);
                if(tmpSquare == null) break;
                if(tmpSquare.tile != null)
                    retWord.addLetter(tmpSquare.tile);
                ccol--;
            } while (tmpSquare != null);
            retWord.Reverse();
            return retWord;
        }
        return retWord;
    }

    private boolean formBadDownWords(Word boardWord, Word word, Anchor anchor) {
        int steps;

        extraPoints = 0;
        // Match words - Just get the position where word starts in currentword
        //System.out.println(word.toString() + " C " + boardWord.toString());
        int indexpos = word.toString().indexOf(boardWord.toString());
        // word at first
        if(word.lenght()- indexpos == boardWord.lenght()) {
            steps = word.lenght()- boardWord.lenght();
            for(int i = 1; i <= steps; i++ ) {
                Word tmpword = getDownWordFromPos(anchor.row, anchor.col-i, new Tile(word.getLetter(steps-i)));
                // Check if the formed word exist in the lexicon
                if(tmpword.lenght()>0) 
                    if(!lexicon.findWord(tmpword.toString()))
                        return true;
              //      else
              //          extraPoints = extraPoints + countDownScoring(tmpword, tmpword, )
            }
        }
        
        // word at end
        else if(indexpos == 0) {
            steps =  word.lenght()-1;
            for(int i = 1; i <= steps; i++ ) {
                Word tmpword = getDownWordFromPos(anchor.row,(anchor.col + boardWord.lenght()) + (i - 1) ,  new Tile(word.getLetter(i)));
                // Check if the formed word exist in the lexicon
                if(tmpword.lenght()>0)
                    if(!lexicon.findWord(tmpword.toString()))
                        return true;
            }
        }
        
        // at middle
        else if(indexpos > 0 && indexpos < word.lenght()-1) {
            steps = (word.lenght() - indexpos) -  boardWord.lenght() ;
            // First rightwards
            for(int i = 1; i <= steps; i++ ) {
                Word tmpword = getDownWordFromPos(anchor.row, anchor.col + boardWord.lenght() + (i-1),  new Tile(word.getLetter(indexpos+boardWord.lenght()+(i-1))));
                // Check if the formed word exist in the lexicon
                if(tmpword.lenght()>0)
                    if(!lexicon.findWord(tmpword.toString()))
                        return true;
            }

            // second leftwards
            steps = indexpos ;
            for(int i = 1; i <= steps; i++ ) {
                Word tmpword = getDownWordFromPos(anchor.row, anchor.col - steps + (i-1),  new Tile(word.getLetter(i-1)));
                // Check if the formed word exist in the lexicon
                if(tmpword.lenght()>0)
                    if(!lexicon.findWord(tmpword.toString()))
                        return true;
            }
        }        
        return false;
    }

    
    private boolean formBadCrossWords(Word boardWord, Word word, Anchor anchor) {
        int steps;
        
        //System.out.println(word.toString() + " D " + boardWord.toString());
        // Match words - Just get the position where word starts in currentword
        int indexpos = word.toString().indexOf(boardWord.toString());
        // word at first
        if(word.lenght()- indexpos == boardWord.lenght()) {
            steps = word.lenght()- boardWord.lenght();
            for(int i = 1; i <= steps; i++ ) {
                Word tmpword = getCrossWordFromPos(anchor.row-i, anchor.col, new Tile(word.getLetter(steps-i)));
                // Check if the formed word exist in the lexicon
                if(tmpword.lenght()>0)
                    if(!lexicon.findWord(tmpword.toString()))
                        return true;
            }
        }
        
        // word at end
        else if(indexpos == 0) {
            steps =  word.lenght()-1;
            for(int i = 1; i <= steps; i++ ) {
                Word tmpword = getCrossWordFromPos((anchor.row + boardWord.lenght()) + (i - 1),anchor.col ,  new Tile(word.getLetter(i)));
                // Check if the formed word exist in the lexicon
                if(tmpword.lenght()>0)
                    if(!lexicon.findWord(tmpword.toString()))
                        return true;
            }
        }
        
        // at middle
        // I have to test just new tiles
        else if(indexpos > 0 && indexpos < word.lenght()-1) {
            steps = indexpos;
            for(int i = 1; i <= steps; i++ ) {
                Word tmpword = getCrossWordFromPos(anchor.row + boardWord.lenght() + (i-1), anchor.col ,  new Tile(word.getLetter(i-1)));
                // Check if the formed word exist in the lexicon
                if(tmpword.lenght()>0)
                    if(!lexicon.findWord(tmpword.toString()))
                        return true;
            }

            steps = (word.lenght() - indexpos)-  boardWord.lenght();
            for(int i = 1; i <= steps; i++ ) {
                Word tmpword = getCrossWordFromPos(anchor.row - steps + (i-1), anchor.col ,  new Tile(word.getLetter(indexpos+boardWord.lenght()+(i-1))));
                // Check if the formed word exist in the lexicon
                if(tmpword.lenght()>0)
                    if(!lexicon.findWord(tmpword.toString()))
                        return true;
            }
        }
         
        
        return false;
    }
    
   public int countCrossScoring (Word boardWord, Word word, Anchor anchor) {
        int start = 0;
        int points = 0;
        boolean doubleword = false;
        boolean tripleword = false;

        // Match words - Just get the position where word starts in currentword
        int indexpos = word.toString().indexOf(boardWord.toString());

        // word at first
        if(word.lenght()- indexpos == boardWord.lenght())
            start = anchor.col - indexpos;

        // word at end
        if(indexpos == 0)
            start = anchor.col;

        // at middle
        if(indexpos > 0 && indexpos < word.lenght()-1)
            start = anchor.col - indexpos;

        for(int i = 0; i < word.lenght(); i++ ) {
            Tile tmp = word.getTile(i);
            SquareType st = board.getSquare(anchor.row, start+i).type;
            if( st == SquareType.doubleletter)
                points = points + tmp.getValue() * 2;
            else if( st == SquareType.tripleletter)
                points = points + tmp.getValue() * 3;
            else if( st == SquareType.doubleword)
                doubleword  = true;
            else if( st == SquareType.tripleword)
                tripleword  = true;
            else
                points = points + tmp.getValue();
        }
        if(doubleword)
            points = points * 2;
        if(tripleword)
            points = points * 3;

        return points;

    }

   public int crossScoring (Word boardWord, Word word, Anchor anchor) {
        int points = 0;
        
        points = countCrossScoring(boardWord, word, anchor);

        if(points+extraPoints>maxPoints) {
            maxPoints = points+extraPoints;
            maxAnchor = anchor;
            maxBoardWord = boardWord;
            maxType = "Cross";
            maxWord = word;
        }
        return points;
            
    }
        
    private int countDownScoring (Word boardWord, Word word, Anchor anchor) {
        int start = 0;
        int points = 0;
        boolean doubleword = false;
        boolean tripleword = false;


        // Match words - Just get the position where word starts in currentword
        int indexpos = word.toString().indexOf(boardWord.toString());

        // word at first
        if(word.lenght()- indexpos == boardWord.lenght())
            start = anchor.row - indexpos;

        // word at end
        if(indexpos == 0)
            start = anchor.row;

        // at middle
        if(indexpos > 0 && indexpos < word.lenght()-1)
            start = anchor.row - indexpos;

        for(int i = 0; i < word.lenght(); i++ ) {
            Tile tmp = word.getTile(i);
            SquareType st = board.getSquare(start+i, anchor.col).type;
            if( st == SquareType.doubleletter)
                points = points + tmp.getValue() * 2;
            else if( st == SquareType.tripleletter)
                points = points + tmp.getValue() * 3;
            else if( st == SquareType.doubleword)
                doubleword  = true;
            else if( st == SquareType.tripleword)
                tripleword  = true;
            else
                points = points + tmp.getValue();
        }
        if(doubleword)
            points = points * 2;
        if(tripleword)
            points = points * 3;

        return points;
    }

    public int downScoring (Word boardWord, Word word, Anchor anchor) {
        int points = 0;
        points = countDownScoring(boardWord, word, anchor);
                
        if(points+extraPoints>maxPoints) {
            maxPoints = points+extraPoints;
            maxAnchor = anchor;
            maxBoardWord = boardWord;
            maxType = "Down";
            maxWord = word;
        }

        return points;
              
    }
        
    public void onlyLegalMoving(ArrayList<Anchor> anchors) {
        Anchor currentAnchor = null;
        Word currentWord = null;
        WordSet currentWordSet = null;
        
        maxPoints = 0;
        maxAnchor = null;
        maxType = "";
        maxWord = null;
        maxBoardWord = null;
        
        for(int i =0 ;i<anchors.size(); i++) {
            currentAnchor = anchors.get(i);
            for(int j = 0; j<currentAnchor.wordSetList.size(); j++) {
                currentWordSet = currentAnchor.wordSetList.get(j);
                if(currentWordSet.ID.equalsIgnoreCase("Cross")) {
                    for(int k=0; k<currentWordSet.size(); k++) {
                        currentWord = currentWordSet.words.get(k);
                        Word word;
                        // Is there rightwards word?
                        if(currentAnchor.freeLeft > 2 && currentAnchor.freeRight==0) {
                            // Get across word (rightward word)
                            word = board.getWordfromAnchor(currentAnchor, WordPos.Rightwards);
                         }
                        // leftwards
                        else if(currentAnchor.freeLeft==0 && currentAnchor.freeRight>2) {
                            // Get across word (rightward word)
                            word = board.getWordfromAnchor(currentAnchor, WordPos.Leftwards);                           
                        }
                        else if(currentAnchor.freeLeft>2 && currentAnchor.freeRight>2) {
                            // if there is just the tile the word is the own currentword
                            word = new Word(board.getSquare(currentAnchor.row, currentAnchor.col).tile);
                        }
                        else
                            word = null;
                        
                       if(word ==null)
                           currentWordSet.words.remove(k);
                       else
                           if(crossBorder(word, currentWord, currentAnchor)) // discard the word
                                currentWordSet.words.remove(k);
                           else
                                if(crossTouchAnotherTile(word, currentWord, currentAnchor))
                                    currentWordSet.words.remove(k);
                                else    
                                    if(formBadDownWords(word, currentWord, currentAnchor))
                                        currentWordSet.words.remove(k);
                                    else
                                        crossScoring(word, currentWord, currentAnchor);
                        
                       word = null; // Force memory releasing
                    }
                } else {// down
                    for(int k=0; k<currentWordSet.size(); k++) {
                        currentWord = currentWordSet.words.get(k);
                        Word word;
                        // Is there rightwards word?
                        if(currentAnchor.freeUp > 2 && currentAnchor.freeDown==0) {
                            // Get across word (rightward word)
                            word = board.getWordfromAnchor(currentAnchor, WordPos.Downwards);
                         }
                        // leftwards
                        else if(currentAnchor.freeUp==0 && currentAnchor.freeDown>2) {
                            // Get across word (rightward word)
                            word = board.getWordfromAnchor(currentAnchor, WordPos.Upwards);                           
                        }
                        else if(currentAnchor.freeUp>2 && currentAnchor.freeDown>2) {
                            // if there is just the tile the word is the own currentword
                            word = new Word(board.getSquare(currentAnchor.row, currentAnchor.col).tile);
                        }
                        else
                            word = null;
                        
                       if(word ==null)
                           currentWordSet.words.remove(k);
                       else
                            if(downBorder(word, currentWord, currentAnchor)) // discard the word
                                currentWordSet.words.remove(k);
                            else
                                if(downTouchAnotherTile(word, currentWord, currentAnchor))
                                    currentWordSet.words.remove(k);
                                else    
                                    if(formBadCrossWords(word, currentWord, currentAnchor))
                                        currentWordSet.words.remove(k);       
                                    else
                                        downScoring(word, currentWord, currentAnchor);
                    }
                }
            }       
        }
    }
       
    private void crossPlayMoving() {
        int steps =0;
        // Match words - Just get the position where word starts in currentword
        int indexpos = maxWord.toString().indexOf(maxBoardWord.toString());
        
        // word at first
        if(maxWord.lenght()- indexpos == maxBoardWord.lenght())  {
            steps = maxWord.lenght()- maxBoardWord.lenght();
            for(int i = 1; i <= steps; i++ ) {
                Tile tmp = maxWord.getTile(i-1);
                int indexRack = rack.getTile(tmp.getLetter());
                if(indexRack > -1 )
                    board.getSquare(maxAnchor.row, maxAnchor.col-steps+(i-1)).tile = rack.removeTile(indexRack);
            }
        }

        // word at end
        else if(indexpos == 0) {
            steps =  maxWord.lenght()-maxBoardWord.lenght();
            for(int i = 1; i <= steps; i++ ) {
                Tile tmp = maxWord.getTile(maxBoardWord.lenght() + (i-1));
                int indexRack = rack.getTile(tmp.getLetter());
                if(indexRack > -1 )
                    board.getSquare(maxAnchor.row, maxAnchor.col +  maxBoardWord.lenght()+(i-1)).tile = rack.removeTile(indexRack);
            }
        }

        // at middle
        else if(indexpos > 0 && indexpos < maxWord.lenght()-1) {
            steps = indexpos  ;
            // First leftwards
            for(int i = 1; i <= steps; i++ ) {
                Tile tmp = maxWord.getTile(i-1);
                int indexRack = rack.getTile(tmp.getLetter());
                if(indexRack > -1 )
                    board.getSquare(maxAnchor.row, maxAnchor.col-steps+(i-1)).tile = rack.removeTile(indexRack);
            }

            // second righwards
            steps = maxWord.lenght() - (indexpos + maxBoardWord.lenght());
            for(int i = 1; i <= steps; i++ ) {
                Tile tmp = maxWord.getTile(maxWord.lenght()-i);
                int indexRack = rack.getTile(tmp.getLetter());
                if(indexRack > -1 )
                    board.getSquare(maxAnchor.row, maxAnchor.col +  maxBoardWord.lenght()+ steps - i).tile = rack.removeTile(indexRack);
            }
        }
    }
        
    private void downPlayMoving() {
        int steps =0;
        // Match words - Just get the position where word starts in currentword
        int indexpos = maxWord.toString().indexOf(maxBoardWord.toString());

        // word at first
        if(maxWord.lenght()- indexpos == maxBoardWord.lenght())  {
            steps = maxWord.lenght()- maxBoardWord.lenght();
            for(int i = 1; i <= steps; i++ ) {
                Tile tmp = maxWord.getTile(i-1);
                int indexRack = rack.getTile(tmp.getLetter());
                if(indexRack > -1 )
                    board.getSquare(maxAnchor.row-steps+(i-1), maxAnchor.col).tile = rack.removeTile(indexRack);
            }
        }

        // word at end
        else if(indexpos == 0) {
            steps =  maxWord.lenght()-maxBoardWord.lenght();
            for(int i = 1; i <= steps; i++ ) {
                Tile tmp = maxWord.getTile(maxBoardWord.lenght() + (i-1));
                int indexRack = rack.getTile(tmp.getLetter());
                if(indexRack > -1 )
                    board.getSquare(maxAnchor.row+  maxBoardWord.lenght()+(i-1), maxAnchor.col ).tile = rack.removeTile(indexRack);
            }
        }

        // at middle
        else if(indexpos > 0 && indexpos < maxWord.lenght()-1) {
            steps = indexpos  ;
            // First leftwards
            for(int i = 1; i <= steps; i++ ) {
                Tile tmp = maxWord.getTile(i-1);
                int indexRack = rack.getTile(tmp.getLetter());
                if(indexRack > -1 )
                    board.getSquare(maxAnchor.row-steps+(i-1), maxAnchor.col).tile = rack.removeTile(indexRack);
            }

            // second rightwards
            steps = maxWord.lenght() - (indexpos + maxBoardWord.lenght());
            for(int i = 1; i <= steps; i++ ) {
                Tile tmp = maxWord.getTile(maxWord.lenght()-i);
                int indexRack = rack.getTile(tmp.getLetter());
                if(indexRack > -1 )
                    board.getSquare(maxAnchor.row + maxBoardWord.lenght()+ steps - i, maxAnchor.col ).tile = rack.removeTile(indexRack);
            }
        }
    }

    public void playMoving() {

        if(maxType.equalsIgnoreCase("Cross"))
            crossPlayMoving();
        else
            downPlayMoving();

        // Update score
        this.Score = this.Score + maxPoints;
    }
    
    Word getBestOne(WordSet wsTmp)    {
        Word bestWord = null;
        
        for(int i =0;i<wsTmp.size();i++) {
            if(bestWord == null) 
                bestWord = wsTmp.getWord(i);
            else
                if(wsTmp.getWord(i).getScore() > bestWord.getScore()) 
                    bestWord = wsTmp.getWord(i);
        }
        return bestWord;
    }
    
   public void putTiles(int row, int col, String direction, Word word) {
   
       if (direction.equalsIgnoreCase("cross")) {
          for(int i = 0;i<word.lenght();i++)  {  
            Tile tmp = word.getTile(i);
            int indexRack = rack.getTile(tmp.getLetter());
            if(indexRack > -1 ) 
                board.getSquare(row, col+i ).tile = rack.removeTile(indexRack);        
          }              
       } else {
          for(int i = 0;i<word.lenght();i++)  {  
            Tile tmp = word.getTile(i);
            int indexRack = rack.getTile(tmp.getLetter());
            if(indexRack > -1 ) 
                board.getSquare(row+i, col ).tile = rack.removeTile(indexRack);        
          }              
           
       }
   }

}

