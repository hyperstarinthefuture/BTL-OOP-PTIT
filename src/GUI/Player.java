/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * @author Lenovo
 */
public class Player {
    private int x, y;
    private int speed;
    private Image img;
    private int LEFT = 0, RIGHT = 1, TOP = 2, BOTTOM = 3;
    private int vector;
    private long time;
    private Image imgLeft, imgRight, imgTop, imgBottom, imgDeath;
    private ArrayList<Boom> arrBoom;
    private boolean IS_DEATH = false;
    private int COUNT_HEART, COUNT_BOOM, BOOMSIZE;

    public Player(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        vector = 1;
        this.speed = speed;
        this.COUNT_HEART = 3;
        this.BOOMSIZE = 1;
        this.COUNT_BOOM = 1;
        loadImage();
        time = System.currentTimeMillis();
        arrBoom = new ArrayList<>();
    }

    public void set_IS_DEATH(boolean b) {
        IS_DEATH = b;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCOUNT_HEART() {
        if (COUNT_HEART < 3)
            this.COUNT_HEART++;
    }

    public void SET_HEART() {
        this.COUNT_HEART--;
    }

    public int getCOUNT_HEART() {
        return COUNT_HEART;
    }

    public boolean isIS_DEATH() {
        return IS_DEATH;
    }

    public void setCOUNT_BOOM() {
        if (this.COUNT_BOOM < 3)
            this.COUNT_BOOM++;
    }

    public void setBOOMSIZE() {
        if (this.BOOMSIZE < 3)
            this.BOOMSIZE++;
    }

    public boolean isLive(int matrix[][]) {
        int d_x = (int) (Math.floor(x / 45));
        int d_y = (int) (Math.floor(y / 45));
        if (matrix[d_y + 1][d_x + 1] == 4)
            return false;
        d_x = (int) (Math.floor((x + 44) / 45));
        d_y = (int) (Math.floor(y / 45));
        if (matrix[d_y + 1][d_x + 1] == 4)
            return false;
        d_x = (int) (Math.floor((x + 44) / 45));
        d_y = (int) (Math.floor((y + 44) / 45));
        if (matrix[d_y + 1][d_x + 1] == 4)
            return false;
        d_x = (int) (Math.floor((x) / 45));
        d_y = (int) (Math.floor((y + 44) / 45));
        if (matrix[d_y + 1][d_x + 1] == 4)
            return false;
        return true;
    }

    public int getX() {
        return x;
    }

    public void setSpeed() {
        if (speed > 6)
            this.speed--;
    }

    public int getY() {
        return y;
    }

    public void setVector(int v) {
        this.vector = v;
    }

    public void resetX(int x) {
        this.x = x;
    }

    public void resetY(int y) {
        this.y = y;
    }

    public void setX(int a) {
        x = x + a;
    }

    private void loadImage() {
        try {
            imgLeft = ImageIO.read(getClass().getResource("/Images/bomber_left.png"));
            imgRight = ImageIO.read(getClass().getResource("/Images/bomber_right.png"));
            imgTop = ImageIO.read(getClass().getResource("/Images/bomber_up.png"));
            imgBottom = ImageIO.read(getClass().getResource("/Images/bomber_down.png"));
            imgDeath = ImageIO.read(getClass().getResource("/Images/bomber_dead.png"));
        } catch (Exception ex) {
            System.out.println("Not found image");
        }
    }

    public void move(int v) {
        if (System.currentTimeMillis() - time > speed) {
            if (vector == LEFT) {
                x -= 1;
            } else if (vector == RIGHT) {
                x += 1;
            } else if (vector == TOP) {
                y -= 1;
            } else if (vector == BOTTOM) {
                y += 1;
            }
            time = System.currentTimeMillis();
        }
    }

    public void putBoom(int matrix[][]) {
        if (arrBoom.size() < COUNT_BOOM) {
            Boom boom = new Boom(x, y, BOOMSIZE);
            arrBoom.add(boom);
        }
    }

    public int getSpeed() {
        return speed;
    }

    public boolean canMove(int v, int matrix[][]) {
        if (v == 0) {
            int x = (int) (Math.floor((this.getX() - 1) / 45.0));
            int y_first = (this.getY() + 20) / 45;
            if (matrix[y_first + 1][x + 1] == 0 || matrix[y_first + 1][x + 1] == 2 || matrix[y_first + 1][x + 1] == 3)
                return false;
            int y_last = (int) (Math.floor((this.getY() + 44) / 45.0));
            if (matrix[y_last + 1][x + 1] == 0 || matrix[y_last + 1][x + 1] == 2 || matrix[y_last + 1][x + 1] == 3)
                return false;
        }
        if (v == 1) {
            int x = (int) (Math.ceil((this.getX() + 1) / 45.0));
            int y_first = (this.getY() + 20) / 45;
            if (matrix[y_first + 1][x + 1] == 0 || matrix[y_first + 1][x + 1] == 2 || matrix[y_first + 1][x + 1] == 3)
                return false;
            int y_last = (int) (Math.floor((this.getY() + 44) / 45.0));
            if (matrix[y_last + 1][x + 1] == 0 || matrix[y_last + 1][x + 1] == 2 || matrix[y_last + 1][x + 1] == 3)
                return false;
        }
        if (v == 2) {
            int y = (int) (Math.floor((this.getY() - 1) / 45.0));
            int x_first = this.getX() / 45;
            if (matrix[y + 1][x_first + 1] == 0 || matrix[y + 1][x_first + 1] == 2 || matrix[y + 1][x_first + 1] == 3)
                return false;
            int x_last = (int) (Math.floor((this.getX() + 44) / 45.0));
            if (matrix[y + 1][x_last + 1] == 0 || matrix[y + 1][x_last + 1] == 2 || matrix[y + 1][x_last + 1] == 3)
                return false;
        }
        if (v == 3) {
            int y = (int) (Math.ceil((this.getY() + 1) / 45.0));
            int x_first = this.getX() / 45;
            if (matrix[y + 1][x_first + 1] == 0 || matrix[y + 1][x_first + 1] == 2 || matrix[y + 1][x_first + 1] == 3)
                return false;
            int x_last = (int) (Math.floor((this.getX() + 44) / 45.0));
            if (matrix[y + 1][x_last + 1] == 0 || matrix[y + 1][x_last + 1] == 2 || matrix[y + 1][x_last + 1] == 3)
                return false;
        }
        return true;
    }

    public void updateMap(int matrix[][]) {
        int d_x1 = x / 45;
        int d_y1 = y / 45;
        int d_x2 = (x + 44) / 45;
        int d_y2 = (y + 44) / 45;
        ArrayList<Boom> arrBang = new ArrayList<>();
        for (Boom b : arrBoom) {
//            Boom bang by boom
            if (matrix[b.getY() + 1][b.getX() + 1] == 4) {
                b.bang(matrix);
                b.afterBomBang(matrix);
            } else {
//            Player move on boom
                if ((b.getX() == d_x1 && b.getY() == d_y1) || (b.getX() == d_x2 && b.getY() == d_y2)) {
                    matrix[b.getY() + 1][b.getX() + 1] = 1;
                } else {
                    matrix[b.getY() + 1][b.getX() + 1] = 3;
                }
//            BoomBang
                b.setStatus(matrix);
                b.afterBomBang(matrix);
            }

            if (b.isIS_HIDDEN() == true) {
                arrBang.add(b);
            }
        }
        for (Boom b : arrBang) {
            arrBoom.remove(b);
        }

    }

    public void autoMove(int v, int matrix[][]) {
        if (v == 2 || v == 3) {
//         Left
            int x = (int) (Math.round(this.getX() / 45.0 - 0.2));
            int x_equal = (int) (Math.floor((this.getX() - 1) / 45.0) + 1);
            if (x == x_equal) {
                this.setX(1);
            }
//          Right
            x = (int) (Math.round(this.getX() / 45.0 + 0.2));
            x_equal = (int) (Math.floor((this.getX() - 1) / 45.0));
            if (x == x_equal) {
                this.setX(-1);
            }
        }
        if (v == 0 || v == 1) {

        }
    }

    public void paintPlayer(Graphics2D g2d) {
//        Draw bomb
        for (Boom b : arrBoom) {
            b.paintBomb(g2d);
        }
//        Draw Image
        if (vector == LEFT) {
            img = imgLeft;
        } else if (vector == RIGHT) {
            img = imgRight;
        } else if (vector == TOP) {
            img = imgTop;
        } else if (vector == BOTTOM) {
            img = imgBottom;
        }
        if (IS_DEATH == false)
            g2d.drawImage(img, x, y, null);
        else {
            g2d.drawImage(imgDeath, x, y, null);
        }
    }

}
