/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PlayGame extends JPanel implements Runnable, ActionListener {
    private GUI gui;
    private WrapperPanel wp;
    private Player player;
    private ArrayList<Monster> arrMonster;
    private Thread thread;
    private int matrix[][] = new int[16][17];
    private BitSet traceKey = new BitSet();
    private boolean IS_RUNNING = true;
    private long timeBreak;
    private ArrayList<Item> items = new ArrayList<>();
    private Button bt_menu;
    private int score = 0;
    private boolean bgr = false;
    public final String bomb = "src/Music/newbomb.wav";
    public final String bomberDie = "src/Music/bomber_die.wav";
    public final String monsterDie = "src/Music/monster_die.wav";
    public final String win = "src/Music/win.wav";
    public final String lose = "src/Music/lose.mid";
    AudioInputStream audioStream;
    Clip clip;

    public PlayGame(WrapperPanel wp) {
        setBackground(Color.PINK);
        this.wp = wp;
        thread = new Thread(this);
        this.addKeyListener(new Handler());
        initPlayer();
        arrMonster = new ArrayList<>();
        this.gui = wp.getGUI();
        timeBreak = System.currentTimeMillis() - 2000;
        setLayout(null);
        initMatrix();
        initMonster();
        thread.start();

    }

    private void initRightPanel() {
        bt_menu = new Button("MENU");
        this.add(bt_menu);
        int w = gui.getWidth() - 685;
        bt_menu.addActionListener(this);
        bt_menu.setBounds(685 + w / 2 - 75, 580, 150, 45);

    }

    private void initMonster() {
        try {
            Scanner sc = new Scanner(new File("src/resource/monster1.txt"));
            while (sc.hasNextLine()) {
                String arr[] = sc.nextLine().split("\\s+");
                Monster monster = new Monster(Integer.parseInt(arr[0]) * 45, Integer.parseInt(arr[1]) * 45, Integer.parseInt(arr[2]));
                arrMonster.add(monster);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Fail init monster");
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initPlayer() {
        try {
            Scanner sc = new Scanner(new File("src/resource/player.txt"));
            while (sc.hasNextLine()) {
                String arr[] = sc.nextLine().split("\\s+");
                player = new Player(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Fail init player");
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int[][] getmatrix() {
        return this.matrix;
    }

    private void initMatrix() {
        try {
            Scanner sc = new Scanner(new File("src/resource/map1.txt"));
            int row = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String arr[] = line.split("\\s+");
                for (int i = 0; i < arr.length; i++) {
                    matrix[row][i] = Integer.parseInt(arr[i]);
                }
                row++;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Not found map.txt");
        }
    }

    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new java.awt.BasicStroke(2));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawBg(g2d);
        drawHeart(g2d);
        if (bgr == false) {
            initRightPanel();
            bgr = true;
        }


    }

    private void drawHeart(Graphics2D g2d) {
        try {
            Image heart = ImageIO.read(getClass().getResource("/Images/heart_1.png"));
            int margin_left = 685 + 55;
            int t = 35;
            for (int i = 1; i <= player.getCOUNT_HEART(); i++) {
                g2d.drawImage(heart, margin_left, 100, null);
                margin_left += t;
            }
        } catch (IOException ex) {
            System.out.println("Image heart fail");
        }
        JLabel sc = new JLabel();
        sc.setText("Score: " + score);
        this.add(sc);
        sc.setBounds(800, 300, 100, 50);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 26));
        g2d.drawString("Score: " + score, 685 + 55, 200);
    }

    private void drawBgRight(Graphics2D g2d) {
        Image bgRight;
        try {
            bgRight = ImageIO.read(getClass().getResource("/Images/background_Info.png"));
            int w = gui.getWidth() - 685;
            bgRight = bgRight.getScaledInstance(w, gui.getHeight(), 0);
            g2d.drawImage(bgRight, 45 * 15 + 10, 0, null);
        } catch (IOException ex) {
            System.out.println("Fail in draw bg right");
        }

    }

    private void drawBg(Graphics2D g2d) {
        try {
            Image bg = ImageIO.read(getClass().getResource("/Images/background.jpg"));
            g2d.drawImage(bg, 0, 0, null);
            Image stuggle_top = ImageIO.read(getClass().getResource("/Images/box2_top.png"));
            Image stone_top = ImageIO.read(getClass().getResource("/Images/box1_top.png"));
            Image stuggle_bottom = ImageIO.read(getClass().getResource("/Images/box2_bottom.png"));
            Image stone_bottom = ImageIO.read(getClass().getResource("/Images/box1_bottom.png"));
            Image boombang = ImageIO.read(getClass().getResource("/Images/bombang.png"));


            for (int i = 1; i <= 14; i++) {
                for (int j = 1; j <= 15; j++) {
                    if (matrix[i][j] == 2) {
                        g2d.drawImage(stuggle_bottom, (j - 1) * 45, (i - 1) * 45 + 28, null);
                    } else if (matrix[i][j] == 0) {
                        g2d.drawImage(stone_bottom, (j - 1) * 45, (i - 1) * 45 + 19, null);
                    }
                }
            }
            drawBgRight(g2d);
            for (Monster monster : arrMonster) {
                monster.paintMonster(g2d);
            }
            player.paintPlayer(g2d);
            for (int i = 1; i <= 14; i++) {
                for (int j = 1; j <= 15; j++) {
                    if (matrix[i][j] == 2) {
                        g2d.drawImage(stuggle_top, (j - 1) * 45, (i - 1) * 45 + 4, null);
                    } else if (matrix[i][j] == 0) {
                        g2d.drawImage(stone_top, (j - 1) * 45, (i - 1) * 45 - 11, null);
                    } else if (matrix[i][j] == 4 || matrix[i][j] == 10) {
                        g2d.drawImage(boombang, (j - 1) * 45, (i - 1) * 45, null);
                    } else if (matrix[i][j] == 5) {
                        Item item = new Item((j - 1) * 45, (i - 1) * 45, 5);
                        items.add(item);
                        matrix[i][j] = 1;
                    } else if (matrix[i][j] == 6) {
                        Item item = new Item((j - 1) * 45, (i - 1) * 45, 6);
                        items.add(item);
                        matrix[i][j] = 1;
                    } else if (matrix[i][j] == 7) {
                        Item item = new Item((j - 1) * 45, (i - 1) * 45, 7);
                        items.add(item);
                        matrix[i][j] = 1;
                    } else if (matrix[i][j] == 8) {
                        Item item = new Item((j - 1) * 45, (i - 1) * 45, 8);
                        items.add(item);
                        matrix[i][j] = 1;
                    }
                }
            }
            ArrayList<Item> arrItem = new ArrayList<>();
            for (Item i : items) {
                i.drawItem(g2d);
                i.countDown();
                i.eatItem(player);
            }
            for (Item i : items) {
                if (i.isHIDDEN() == true) {
                    arrItem.add(i);
                }
            }
            for (Item i : arrItem) {
                items.remove(i);
            }


        } catch (IOException ex) {
            System.out.println("Not found image");
        }
    }

    private void resetGame() {
        this.initPlayer();
        this.initMatrix();
        this.arrMonster.clear();
        initMonster();
        score = 0;
    }

    private void writeTopPlayer() {
        int l=0;
        ArrayList<String> arr = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File("src/resource/highscore.txt"));
            while (sc.hasNextLine()) {
                arr.add(sc.nextLine());
            }
            int index = 1;
            if (arr.size() >= 10) {
                index = 10;
            } else {
                index = arr.size();
            }
            l=index;
            if(arr.size()==10)
            {
                String str[] = arr.get(index - 1).split(" ");
                if (score >= Integer.parseInt(str[1])) {
                    boolean check = true;
                    while (check) {
                        try {
                            String name[] = JOptionPane.showInputDialog("Your name:").trim().split("\\s+");
                            if (name[0].equals("")) {
                                throw new Exception("Please fill your name");
                            }
                            check = false;
                            arr.add(name[0] + " " + score);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, e.getMessage());
                        }
                    }
                }
            }
            else {
                boolean check = true;
                    while (check) {
                        try {
                            String name[] = JOptionPane.showInputDialog("Your name:").trim().split("\\s+");
                            if (name[0].equals("")) {
                                throw new Exception("Please fill your name");
                            }
                            check = false;
                            arr.add(name[0] + " " + score);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, e.getMessage());
                        }
                    }
                    l++;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        Collections.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String arr1[] = o1.split(" ");
                String arr2[] = o2.split(" ");
                int n1 = Integer.parseInt(arr1[1]), n2 = Integer.parseInt(arr2[1]);
                if (n1 >= n2)
                    return -1;
                return 1;
            }

        });
        String res = "";
        for (int i = 0; i < l; i++) {
            res = res + arr.get(i) + "\n";
        }

        res=res.trim();
        try {
            FileWriter fw = new FileWriter("src/resource/highscore.txt");
            fw.write(res);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void sound(String url){
        File file = new File(url);
        try {
            audioStream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        try {
            clip.open(audioStream);
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        clip.start();
    }

    @Override
    public void run() {
        while (IS_RUNNING) {
//              MONSTER MOVE
            ArrayList<Monster> arrMonsterDeath = new ArrayList<>();
            for (Monster monster : arrMonster) {
                monster.setIsLive(matrix);
                if (monster.isLive(matrix) == false) {
                    arrMonsterDeath.add(monster);
                }
                if (monster.canMove(monster.getVector(), matrix) == false) {
                    Random rd = new Random();
                    int vt = rd.nextInt(4);
                    monster.setVector(vt);
                    monster.move();
                }
                if (monster.collisionPlayer(player) && System.currentTimeMillis() - timeBreak > 2000) {
//                    IS_RUNNING=false;
                    player.set_IS_DEATH(true);
                    sound(bomberDie);
                    timeBreak = System.currentTimeMillis();
                    System.out.println(player.getCOUNT_HEART());
//                    break;
                }
                monster.move();
            }
//             END MONSTER MOVE
            for (Monster mt : arrMonsterDeath) {
                arrMonster.remove(mt);
                sound(monsterDie);
                score += 3;
            }
            player.updateMap(matrix);
            if (player.isLive(matrix) == false && System.currentTimeMillis() - timeBreak > 2000) {
//                IS_RUNNING=false;
                player.set_IS_DEATH(true);
                sound(bomberDie);
                timeBreak = System.currentTimeMillis();
                System.out.println(player.getCOUNT_HEART());
//                break;
            }
            if (player.isIS_DEATH() == false) {
                if (traceKey.get(KeyEvent.VK_RIGHT)) {
                    player.setVector(1);
                    if (player.canMove(1, matrix)) {
                        player.move(1);
                    }
                } else if (traceKey.get(KeyEvent.VK_LEFT)) {
                    player.setVector(0);
                    if (player.canMove(0, matrix)) {
                        player.move(0);
                    }
                } else if (traceKey.get(KeyEvent.VK_UP)) {

                    player.setVector(2);
                    if (player.canMove(2, matrix)) {
                        player.move(2);
                    } else {
                        player.autoMove(2, matrix);
                    }
                } else if (traceKey.get(KeyEvent.VK_DOWN)) {

                    player.setVector(3);
                    if (player.canMove(3, matrix)) {
                        player.move(3);
                    } else {
                        player.autoMove(3, matrix);
                    }
                }
            }
            if (player.isIS_DEATH() == true && System.currentTimeMillis() - timeBreak > 1800) {
                if (player.getCOUNT_HEART() > 1) {
                    player.set_IS_DEATH(false);
                    player.SET_HEART();
                    player.resetX(315);
                    player.resetY(315);

                }
            }
            if (player.getCOUNT_HEART() == 1) {
                if (traceKey.get(KeyEvent.VK_ENTER)) {
                    writeTopPlayer();
                    resetGame();

                }
            }
            repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                System.out.println("Error in run function");
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(bt_menu)) {
            wp.displayMenuGame();
        }
    }

    private class Handler implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            traceKey.set(e.getKeyCode());
            if (e.getKeyCode() == KeyEvent.VK_SPACE && player.isIS_DEATH() == false) {
                player.putBoom(matrix);
                sound(bomb);
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            traceKey.clear(e.getKeyCode());
        }
    }
}
