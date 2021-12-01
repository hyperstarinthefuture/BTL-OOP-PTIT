/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.CardLayout;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

/**
 *
 * @author Lenovo
 */
public final class WrapperPanel extends JPanel {
    private GUI gui;
    private CardLayout cardLayout;
    private MenuGame menu;
    private PlayGame playGame;
    private HighScore highScore;
    private OptionGame optionGame;
    private String MENU_TAG="menu",HIGHSCORE_TAG="high_score";
    private String PLAYGAME_TAG="play_gamess",OPTION_TAG="option_tagss";
    public WrapperPanel(GUI gui) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.gui=gui;
        cardLayout=new CardLayout();
        setLayout(cardLayout);
        
//        Add jpanel MenuGame
        menu=new MenuGame(this);
        add(menu,MENU_TAG);
        
//        Add jpanel Play game
        playGame=new PlayGame(this);
        add(playGame,PLAYGAME_TAG);
        
//        Add option game
        optionGame=new OptionGame(this);
        add(optionGame,OPTION_TAG);
        
//        Add jpanel High score
//        highScore=new HighScore(this);
//        add(highScore,HIGHSCORE_TAG);
//        Set default MenuGame display
        displayMenuGame();
        
    }
    public GUI getGUI(){
        return gui;
    }
//    Function show jpanel
    public void displayMenuGame(){
        cardLayout.show(WrapperPanel.this, MENU_TAG);
        menu.requestFocus();
    }
    public void displayHighScore(){
        highScore=new HighScore(this);
        add(highScore,HIGHSCORE_TAG);
        cardLayout.show(this, HIGHSCORE_TAG);
        highScore.requestFocus();
    }
    public void displayPlayGame(){
        cardLayout.show(this, PLAYGAME_TAG);
        playGame.requestFocus();
    }
    public void displayOptionGame(){
        cardLayout.show(this, OPTION_TAG);
        playGame.requestFocus();
    }
//    Close game
    public void closeGame(){
        gui.dispose();
    }
}
