/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

/**
 * @author Lenovo
 */
public class Boom {
    private int x, y;
    private Image imgBoom;
    private int length;
    private long timeLine = 2000;
    private long timePut, timeBoomBang;
    private boolean STATUS_BANG = false;
    private boolean IS_HIDDEN = false;
    private boolean CHANGE = false;
    AudioInputStream audioStream;
    Clip clip;
    public final String bombBang = "src/Music/bomb_bang.wav";

    public Boom(int x, int y, int length) {
        this.x = (x + 22) / 45;
        this.y = (y + 22) / 45;
        this.length = length;
        this.timePut = System.currentTimeMillis();
        this.timeBoomBang = timePut + timeLine;
        loadImage();
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

    public boolean isIS_HIDDEN() {
        return IS_HIDDEN;
    }

    public void setTimePut(long timePut) {
        this.timePut = timePut;
    }

    public void setStatus(int matrix[][]) {
        if (System.currentTimeMillis() > timeBoomBang && STATUS_BANG == false && CHANGE == false) {
            STATUS_BANG = true;
            sound(bombBang);
            setMatrixBomBang(matrix);
            CHANGE = true;
        }
    }

    public void bang(int matrix[][]) {
        if (CHANGE == false && STATUS_BANG == false) {
            STATUS_BANG = true;
            timeBoomBang = System.currentTimeMillis();

            setMatrixBomBang(matrix);
            CHANGE = true;
        }
    }

    public void afterBomBang(int matrix[][]) {
        if (System.currentTimeMillis() - (timeBoomBang) > 500 && STATUS_BANG == true) {
            IS_HIDDEN = true;
            STATUS_BANG = false;
            int d_x = x + 1;
            int d_y = y + 1;
            matrix[d_y][d_x] = 1;
            for (int i = 1; i <= length; i++) {
                if (d_x + i < 16) {
                    if (matrix[d_y][d_x + i] == 4) {
                        matrix[d_y][d_x + i] = 1;
                    } else if (matrix[d_y][d_x + i] == 10) {
                        matrix[d_y][d_x + i] = this.randomItem();
                    }
                }
            }
            for (int i = 1; i <= length; i++) {
                if (d_x - i > 0) {
                    if (matrix[d_y][d_x - i] == 4) {
                        matrix[d_y][d_x - i] = 1;
                    } else if (matrix[d_y][d_x - i] == 10) {
                        matrix[d_y][d_x - i] = this.randomItem();
                    }
                }
            }
            for (int i = 1; i <= length; i++) {
                if (d_y + i < 16) {
                    if (matrix[d_y + i][d_x] == 4) {
                        matrix[d_y + i][d_x] = 1;
                    } else if (matrix[d_y + i][d_x] == 10) {
                        matrix[d_y + i][d_x] = this.randomItem();
                    }
                }
            }
            for (int i = 1; i <= length; i++) {
                if (d_y - i > 0) {
                    if (matrix[d_y - i][d_x] == 4) {
                        matrix[d_y - i][d_x] = 1;
                    } else if (matrix[d_y - i][d_x] == 10) {
                        matrix[d_y - i][d_x] = this.randomItem();
                    }
                }
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void loadImage() {
        try {
            imgBoom = ImageIO.read(getClass().getResource("/Images/bomb.png"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void paintBomb(Graphics2D g2d) {
        if (STATUS_BANG == false)
            g2d.drawImage(imgBoom, x * 45, y * 45, null);
    }

    private void setMatrixBomBang(int matrix[][]) {
        int d_x = x + 1;
        int d_y = y + 1;
        for (int i = 0; i <= length; i++) {
            if (matrix[d_y][d_x + i] != 0) {
                if (matrix[d_y][d_x + i] == 2) {
                    matrix[d_y][d_x + i] = 10;
                    break;
                } else {
                    matrix[d_y][d_x + i] = 4;
                }

            } else
                break;
        }
        for (int i = 0; i <= length; i++) {
            if (matrix[d_y][d_x - i] != 0) {
                if (matrix[d_y][d_x - i] == 2) {
                    matrix[d_y][d_x - i] = 10;
                    break;
                } else {
                    matrix[d_y][d_x - i] = 4;
                }
            } else
                break;
        }
        for (int i = 0; i <= length; i++) {
            if (matrix[d_y + i][d_x] != 0) {
                if (matrix[d_y + i][d_x] == 2) {
                    matrix[d_y + i][d_x] = 10;
                    break;
                } else {
                    matrix[d_y + i][d_x] = 4;
                }
            } else
                break;
        }
        for (int i = 0; i <= length; i++) {
            if (matrix[d_y - i][d_x] != 0) {
                if (matrix[d_y - i][d_x] == 2) {
                    matrix[d_y - i][d_x] = 10;
                    break;
                } else {
                    matrix[d_y - i][d_x] = 4;
                }
            } else
                break;
        }
    }

    void setTimeBoomBang(long t) {
        this.timeBoomBang = t;
    }

    public int randomItem() {
        Random rd = new Random();
        int n = rd.nextInt(100);
        if ((n >= 0 && n <= 20) || (n >= 25 && n <= 35) || (n > 35 && n <= 45) || (n > 60 && n < 100)) {
            return 1;
        } else if (n >= 21 && n < 25) {
            return 5;
        } else if (n > 45 && n < 50) {
            return 6;
        } else if (n >= 50 && n <= 55) {
            return 7;
        } else
            return 8;
    }

}
