
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Scrabble extends JFrame {

    BoardCanvas boardcanvas;
    DisplayCanvas displaycanvas;

    boolean  computerPassed = false;
    boolean  humanPassed = false;


    private Image star;
    private Image gray;
    private Image dl;
    private Image tw;
    private Image dw;
    private Image tl;
    private Image tile;

    Lexicon lexicon;
    Board board;
    Clothbag cb;
    Game game;

    PlayerTwo computer;

    JButton btnNewGame;
    JButton btnDone;
    JButton btnPass;

    int turn;
    boolean firstTurn = true;

    Tile selectedTile = null;
    Cursor curCursor;

    public Scrabble()
    {
        super();
        initComponents();

        setBackground(Color.WHITE);
        boardcanvas = new BoardCanvas();
        displaycanvas = new DisplayCanvas();

        displaycanvas.addMouseMotionListener(new DisplayMotionAdapter(this));
        displaycanvas.addMouseListener(new DisplayAdapter(this));

        btnNewGame = new JButton("New Game");
        btnDone = new JButton("My Moving is Done");
        btnPass = new JButton("I Pass!");

        btnDone.setEnabled(false);
        btnPass.setEnabled(false);

        Toolkit tk = Toolkit.getDefaultToolkit();
        star = tk.getImage("star.png");
        gray = tk.getImage("gray.png");
        dl = tk.getImage("dl.png");
        tw = tk.getImage("tw.png");
        dw = tk.getImage("dw.png");
        tl = tk.getImage("tl.png");   
        tile = tk.getImage("tile.png");

       setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

       btnNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewGameActionPerformed(evt);
            }
        });

       btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });

       btnPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(boardcanvas);
        boardcanvas.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 426, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(displaycanvas);
        displaycanvas.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 118, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 244, Short.MAX_VALUE)
        );


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boardcanvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnNewGame, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(btnDone, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(btnPass, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1,1,1)
                        .addComponent(displaycanvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(boardcanvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(displaycanvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDone, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))))
        );

        pack();

    }

    private void btnNewGameActionPerformed(java.awt.event.ActionEvent evt) {
        board = new Board();
        cb = new Clothbag();
        cb.Shuffle();
        game = new Game(board);
        computer = new PlayerTwo(lexicon, board);

        firstTurn = true;
        // decides who begins
        do {
            Tile t1 = cb.drawOneTile();
            Tile t2 = cb.drawOneTile();

            turn = game.whoStarts(t1, t2);

            if(turn == 1) {
                JOptionPane.showMessageDialog(this,"I begin!");
            }
            else if (turn == 2){
                JOptionPane.showMessageDialog(this,"You begin!");
            }

            cb.insertTile(t1);
            cb.insertTile(t1);

        } while (turn == 0);

        // Get tiles to the rack
        for(int i = 0;i<7;i++)
            computer.rack.addTile(cb.drawOneTile());

        for(int i = 0;i<7;i++)
            game.humanRack.addTile(cb.drawOneTile());

        btnDone.setEnabled(true);
        btnPass.setEnabled(true);

        // computer === 1
        // human === -1
        if (turn==2)
            turn = -1;

       game.clearBuffer();
       if(turn==1) { 
           computerPlays();

       }

        computerPassed = false;
        humanPassed = false;

        boardcanvas.repaint();
        displaycanvas.repaint();
        JOptionPane.showMessageDialog(this,"Your Turn! Drag and Drog the Tiles to the Board and then Press Done.");

    }

    public void computerPlays() {
        if(firstTurn) {
            WordSet wsTmp = lexicon.getWords(computer.rack);
            Word bestword = computer.getBestOne(wsTmp);
            computer.putTiles(7-bestword.lenght()/2, 7, "Down", bestword);
            computer.Score = computer.downScoring (bestword, bestword, new Anchor(7-bestword.lenght()/2, 7, 0,0,0,0));
            firstTurn = false;
        }
        else {  // regular turn
            // Get all achors of the boards
            ArrayList<Anchor> currentAnchors = board.getAchors();
            computer.formWordsByAnchors(currentAnchors);
            computer.onlyLegalMoving(currentAnchors);
            if(computer.maxWord==null) {
               JOptionPane.showMessageDialog(this,"I Pass!");
               computerPassed = true;
               if(humanPassed)
                     checkWinner();
            }
            else {
                computer.playMoving();
                computerPassed = false;
                // Get new tiles
                int n = 7 - computer.rack.numberTiles();
                for(int i = 0;i<n; i++)
                computer.rack.addTile(cb.drawOneTile());
            }
        }
        turn = turn * -1;
    }

    private void checkWinner() {
        if(computer.Score > game.humanScore)
            JOptionPane.showMessageDialog(this,"I won");
        else if (computer.Score < game.humanScore)
            JOptionPane.showMessageDialog(this,"You won");
        else
            JOptionPane.showMessageDialog(this,"This is a ties up");


        btnDone.setEnabled(false);
        btnPass.setEnabled(false);

        turn = 0;
    }

    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {

        String typeMoving = "";

        // Check if there are any moving
        if(game.bufMoving.size() < 1) {
            JOptionPane.showMessageDialog(this,"You didn't do anything yet!");
            return;
        }

        if(firstTurn) {
            boolean found = false;
            for(int i = 0; i < game.bufMoving.size(); i++ )
                if(game.bufMoving.get(i).col == 7 && game.bufMoving.get(i).row == 7)
                    found = true;
            if(!found) {
                JOptionPane.showMessageDialog(this,"You have to start at the start!");
                // get back the tiles to rack
                game.takeOutHumanMovingTiles();
                // repaint
                this.repaint();
                // clear human moving buffer
                game.clearBuffer();
                return;
            }

        }

        // if is one tile moving
        if(game.bufMoving.size() == 1) {
            Word tmpWord;
            tmpWord = computer.getDownWordFromPos(game.bufMoving.get(0).row, game.bufMoving.get(0).col, game.bufMoving.get(0).tile);
            if(tmpWord.lenght()==0) {
                tmpWord = computer.getCrossWordFromPos(game.bufMoving.get(0).row, game.bufMoving.get(0).col, game.bufMoving.get(0).tile);
                if(tmpWord.lenght()==0) {
                    JOptionPane.showMessageDialog(this,"Invalid Moving. You are not forming a word!");
                    // get back the tiles to rack
                    game.takeOutHumanMovingTiles();
                    // repaint
                    this.repaint();
                    // clear human moving buffer
                    game.clearBuffer();
                    return;
                }
                else
                    typeMoving = "cross";
            } else
                typeMoving = "down";
        }

        // check if the moving has diagonal tiles
        if(typeMoving.length() <= 0)
            typeMoving = game.checkHumanMoving();
        
        if(typeMoving.equalsIgnoreCase("diag")) {
            JOptionPane.showMessageDialog(this,"You must only use either to cross or to down towards");
            // get back the tiles to rack
            game.takeOutHumanMovingTiles();
            // repaint
            this.repaint();
            // clear human moving buffer
            game.clearBuffer();
            return;            
        }
        // sort list from the smallest to largest
        game.sortHumanMoving(typeMoving);
        // get the complete word from the first tile
        Word tmpWord;
        if(typeMoving.equalsIgnoreCase("down"))
            tmpWord = computer.getDownWordFromPos(game.bufMoving.get(0).row, game.bufMoving.get(0).col, game.bufMoving.get(0).tile);
        else
            tmpWord = computer.getCrossWordFromPos(game.bufMoving.get(0).row, game.bufMoving.get(0).col, game.bufMoving.get(0).tile);
        // check if the word exist
        if (! lexicon.findWord(tmpWord.toString())) {
          JOptionPane.showMessageDialog(this,"I don't know word " + tmpWord.toString() + ". Sorry!");
          // get back the tiles to rack
          game.takeOutHumanMovingTiles();
          // repaint
          this.repaint();
          // clear human moving buffer
          game.clearBuffer();
          return;
        }

        // check if that word forms bad words
        if(typeMoving.equalsIgnoreCase("down")) {
            for(int i = 0; i < game.bufMoving.size(); i++ ) {
                Word tmpword = computer.getCrossWordFromPos(game.bufMoving.get(i).row, game.bufMoving.get(i).col, game.bufMoving.get(i).tile);
                // Check if the formed word exist in the lexicon
                if(tmpword.lenght()>0)
                    if(!lexicon.findWord(tmpword.toString())) {
                      JOptionPane.showMessageDialog(this,"Your word is forming bad words on the board!");
                      // get back the tiles to rack
                      game.takeOutHumanMovingTiles();
                      // repaint
                      this.repaint();
                      // clear human moving buffer
                      game.clearBuffer();
                      return;
                   }
            }
            // scoring
            game.humanScore = game.humanScore + game.countDownScoring(game.bufMoving.get(0).row, game.bufMoving.get(0).col);
        }
        if(typeMoving.equalsIgnoreCase("cross")) {
            for(int i = 0; i < game.bufMoving.size(); i++ ) {
                Word tmpword = computer.getDownWordFromPos(game.bufMoving.get(i).row, game.bufMoving.get(i).col, game.bufMoving.get(i).tile);
                // Check if the formed word exist in the lexicon
                if(tmpword.lenght()>0)
                    if(!lexicon.findWord(tmpword.toString())) {
                      JOptionPane.showMessageDialog(this,"Your word is forming bad words on the board!");
                      // get back the tiles to rack
                      game.takeOutHumanMovingTiles();
                      // repaint
                      this.repaint();
                      // clear human moving buffer
                      game.clearBuffer();
                      return;
                   }
            }
            // scoring
            game.humanScore = game.humanScore + game.countCrossScoring(game.bufMoving.get(0).row, game.bufMoving.get(0).col);
        }

        if(firstTurn)
            firstTurn = false;
        // get new tiles
        int n = 7 - game.humanRack.numberTiles();
        for(int i = 0;i<n; i++)
        game.humanRack.addTile(cb.drawOneTile());

        // repaint
        boardcanvas.repaint();
        displaycanvas.repaint();
        // change the turn
        turn = turn * -1;
        // clear human moving buffer
        game.clearBuffer();

        // computer plays
        computerPlays();
        
        return;
    }

    private void btnPassActionPerformed(java.awt.event.ActionEvent evt) {

        if(computerPassed)
            checkWinner();
        else {
            game.takeOutHumanMovingTiles();
            // repaint
            this.repaint();
            // clear human moving buffer
            game.clearBuffer();
            turn = turn * -1;
            humanPassed = true;

            computerPlays();
        }
    }

    public void initComponents()
    {
        lexicon = new Lexicon();
        lexicon.loadDictionary();
        board = new Board();
        computer = new PlayerTwo(lexicon, board);
        game = new Game(board);

        turn = -2; // no new game
    }

    class BoardCanvas extends JPanel {

        public BoardCanvas() {
            setBackground(Color.white);
        }

        public void paint(Graphics g) {
            // draw the board
            Font tileFont = new Font("Times", Font.BOLD, 14);
            Font valueFont = new Font("Times", Font.PLAIN, 9);

            //for(int k=0;k<15;k++)
            //    g.drawString(String.valueOf(k+1),10+(k*40),10);
            for(int i=0;i<15;i++) {
                //g.drawString(String.valueOf(i+1),10,10+(i*40));
                for(int j=0;j<15;j++) {
                    if(board.getSquare(i,j).tile != null) {
                        g.setFont(tileFont);
                        g.drawImage(tile, 5+(j*40), 5+(i*40), this);
                        g.drawString(board.getSquare(i,j).tile.toString().toUpperCase(),5+(j*40)+ 15, 5+(i*40)+ 21);
                        g.setFont(valueFont);
                        g.drawString(String.valueOf(board.getSquare(i,j).tile.getValue()),5+(j*40)+ 28,5+(i*40)+ 10);
                    }
                    else {
                        if(i==7 && j==7)
                            g.drawImage(star, 5+(j*40), 5+(i*40), this);
                        else
                            if(board.getSquareType(i,j) == SquareType.doubleletter)
                                g.drawImage(dl, 5+(j*40), 5+(i*40), this);
                            else if(board.getSquareType(i,j) == SquareType.doubleword)
                                g.drawImage(dw, 5+(j*40), 5+(i*40), this);
                            else if(board.getSquareType(i,j) == SquareType.tripleletter)
                                g.drawImage(tl, 5+(j*40), 5+(i*40), this);
                            else if(board.getSquareType(i,j) == SquareType.tripleword)
                                g.drawImage(tw, 5+(j*40), 5+(i*40), this);
                            else
                                g.drawImage(gray, 5+(j*40), 5+(i*40), this);
                    }
                }
            }

        }

    }

    class DisplayCanvas extends JPanel {

        public DisplayCanvas() {

            setBackground(Color.white);

        }

        public void paint(Graphics g) {
            // draw the board
            Font tileFont = new Font("Times", Font.BOLD, 14);
            Font playerNameFont = new Font("Arial", Font.BOLD, 20);
            Font scoreFont = new Font("Courrier New", Font.BOLD, 26);
            Font valueFont = new Font("Times", Font.PLAIN, 10);
            g.setFont(tileFont);

            // Draw Computer panel
            g.setFont(playerNameFont);
            g.drawString("Computer",0,25);
            g.setColor(Color.BLACK);
            g.fillRect(0, 35, 150, 32);

            // Draw Computer score
            g.setColor(Color.GREEN);
            g.setFont(scoreFont);
            g.drawString(String.valueOf(computer.Score),5,60);

            // Draw computer rack
            g.setColor(Color.BLACK);
            g.setFont(playerNameFont);
            for(int i = 0;i<computer.rack.numberTiles();i++){
                g.setFont(playerNameFont);
                g.drawImage(tile,(i*40), 75, this);
                g.drawString(computer.rack.getTile(i+1).toString().toUpperCase(), (i*40)+15, 99);
                g.setFont(valueFont);
                g.drawString(String.valueOf(computer.rack.getTile(i+1).getValue()),(i*40)+29,84);
            }

            int y = 150;
            // Draw Human panel
            g.setFont(playerNameFont);
            g.drawString("You",0,y);
            g.setColor(Color.BLACK);
            g.fillRect(0, y+10, 150, 32);

            // Draw Computer score
            g.setColor(Color.GREEN);
            g.setFont(scoreFont);
            g.drawString(String.valueOf(game.humanScore),5,y+35);

            // Draw computer rack
            g.setColor(Color.BLACK);
            for(int i = 0;i<game.humanRack.numberTiles();i++){
                g.drawImage(tile,(i*40), y+55, this);
                g.setFont(playerNameFont);
                g.drawString(game.humanRack.getTile(i+1).toString().toUpperCase(), (i*40)+14, y+80);
                g.setFont(valueFont);
                g.drawString(String.valueOf(game.humanRack.getTile(i+1).getValue()),(i*40)+29,y+65);
            }

        }
      }


    class DisplayAdapter extends MouseAdapter {
        Scrabble canvas;

        public  DisplayAdapter(Scrabble canvas) {
            this.canvas = canvas;
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            // check if the human turn
            if(turn==-1) {
                // check if is on the board
                if(me.getXOnScreen() >= 5 && me.getXOnScreen()<=620 && me.getYOnScreen()>=5 && me.getYOnScreen()<=640 && selectedTile != null ) {
                    // find the position on board ( row, col )
                    int boardCol = ((me.getXOnScreen()-20) / 40);
                    int boardRow = ((me.getYOnScreen()-40) / 40);
                    // check if the possition is already with a tile
                    if(board.getSquare(boardRow, boardCol).tile != null) {
                    JOptionPane.showMessageDialog(canvas,"Invalid Position");
                    } else {
                        // insert tile to the buffer human game
                        game.insertHumanMoving(selectedTile, boardRow, boardCol);
                        board.getSquare(boardRow, boardCol ).tile = selectedTile;
                        game.humanRack.removeTile(selectedTile);
                        // repaint
                        canvas.repaint();
                    }
                }
                selectedTile = null;
                // change the cursor
                setCursor(curCursor);

            }
        }

      }

      class DisplayMotionAdapter extends MouseMotionAdapter {
            Scrabble canvas;

            public  DisplayMotionAdapter(Scrabble canvas) {
                this.canvas = canvas;
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                // check if the human turn
                if(turn==-1) {
                    // Check if the mouse is on the rack
                    if(me.getX()>=0 && me.getX() <= (game.humanRack.numberTiles()-1)*40 && me.getY() >=205 && me.getY() <= 260 && selectedTile == null) {
                        // which tile
                        int iTile = (me.getX() + 10)/ 40;
                        // select the tile
                        selectedTile = game.humanRack.getTile(iTile+1);
                        // change the cursor
                        curCursor = Cursor.getDefaultCursor();
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
              }
         }
   }

   public static void main(String args[])  {


        JFrame frame = new Scrabble();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setResizable(false);
	frame.getContentPane();
	frame.setSize(930, 700);
	frame.setTitle("Scrabble Game");
	frame.setVisible(true);
    }
}
