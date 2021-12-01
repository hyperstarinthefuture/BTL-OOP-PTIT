/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Lenovo
 */
public class HighScore extends JPanel {
    private JLabel bg;
    private GUI gui;
    private WrapperPanel wp;
    private JLabel button;

    public HighScore(WrapperPanel wp) {
//        setBackground(Color.WHITE);
        this.wp = wp;
        this.gui = wp.getGUI();
        setLayout(null);
        initTopPlayer();
        initButton();
        initBg();
    }

    private void initBg() {
        bg = new JLabel();
        bg.setBounds(0, -30, gui.getWidth(), gui.getHeight());
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/back_high.png"));
        bg.setIcon(icon);
        add(bg);

    }

    private void initButton() {
        button = new JLabel();
        ImageIcon imageBackButton = new ImageIcon(this.getClass().getResource("/Images/button_back.png"));
        button.setBounds(gui.getWidth() / 2 - 100, gui.getHeight() - 120, imageBackButton.getIconWidth(), imageBackButton.getIconHeight());
        button.setIcon(imageBackButton);
        add(button);
        button.addMouseListener(mouseEvent);
    }

    public void initTopPlayer() {
        int bot = 10;
        try {
            File file = new File("src/resource/highscore.txt");
            Scanner sc = new Scanner(file);
            JLabel columnPlayer = new JLabel("", JLabel.LEFT);
            columnPlayer.setBackground(null);
            columnPlayer.setBounds(100, 10, 300, 60);
            columnPlayer.setForeground(Color.black);
            columnPlayer.setFont(new Font("NewellsHand", Font.BOLD, 25));
            columnPlayer.setText("Player");
            add(columnPlayer);
            JLabel columnScore = new JLabel("", JLabel.LEFT);
            columnScore.setBackground(null);
            columnScore.setBounds(400, 10, 300, 60);
            columnScore.setForeground(Color.black);
            columnScore.setFont(new Font("NewellsHand", Font.BOLD, 25));
            columnScore.setText("Score");
            add(columnScore);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String content[] = line.split("\\s+");
                bot += 30;
                JLabel player = new JLabel("", JLabel.LEFT);
                player.setBackground(null);
                player.setBounds(100, bot, 300, 60);
                player.setForeground(Color.black);
                player.setFont(new Font("NewellsHand", Font.BOLD, 25));
                player.setText(content[0]);
                add(player);
                JLabel score = new JLabel("", JLabel.LEFT);
                score.setBackground(null);
                score.setBounds(400, bot, 300, 60);
                score.setForeground(Color.black);
                score.setFont(new Font("NewellsHand", Font.BOLD, 25));
                score.setText(content[1]);
                add(score);


            }
        } catch (Exception e) {
            System.out.println("File error in highscore\n");
            System.out.println(e);
        }
    }

    MouseAdapter mouseEvent = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource().equals(button)) {
                wp.displayMenuGame();
            }
        }

        public void mouseEntered(MouseEvent e) {
            if (e.getSource().equals(button)) {
                ImageIcon icon1 = new ImageIcon(this.getClass().getResource("/Images/button_back2.png"));
                button.setIcon(icon1);
            }
        }

        public void mouseExited(MouseEvent e) {
            if (e.getSource().equals(button)) {
                ImageIcon icon1 = new ImageIcon(this.getClass().getResource("/Images/button_back.png"));
                button.setIcon(icon1);
            }
        }
    };
}
