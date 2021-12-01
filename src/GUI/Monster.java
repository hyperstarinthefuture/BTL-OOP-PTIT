/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Lenovo
 */
public class Monster {
    public int x,y;
    private int speed;
    private Image img;
    private int LEFT=0,RIGHT=1,TOP=2,BOTTOM=3;
    private int vector=2;
    private Image imgLeft,imgRight,imgTop,imgBottom;
    private boolean IS_LIVE;
    private long time;
     public Monster(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        IS_LIVE=true;
        time=System.currentTimeMillis();
        loadImage();
    }
    public void setVector(int v){
        this.vector=v;
    }

    public int getVector() {
        return vector;
    }
     
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    private void loadImage(){
        try {
            imgLeft=ImageIO.read(getClass().getResource("/Images/monster_left.png"));
            imgRight=ImageIO.read(getClass().getResource("/Images/monster_right.png"));
            imgTop=ImageIO.read(getClass().getResource("/Images/monster_up.png"));
            imgBottom=ImageIO.read(getClass().getResource("/Images/monster_down.png"));  
        } catch (Exception ex) {
            System.out.println("Not found image");
        }
    }
    public void move(){
        if(System.currentTimeMillis()-time > speed){
            if(vector==LEFT){
            x-=1;
            }
            else if(vector==RIGHT){
                x+=1;
            }
            else if(vector==TOP){
                y-=1;
            }
            else if(vector==BOTTOM){
                y+=1;
            }
            time=System.currentTimeMillis();
        } 
    }
    public void paintMonster(Graphics2D g2d){
        if(vector==LEFT){
            img=imgLeft;
        }
        else if(vector==RIGHT){
            img=imgRight;
        }
        else if(vector==TOP){
            img=imgTop;
        }
        else if(vector==BOTTOM){
            img=imgBottom;
        }
        if(IS_LIVE==true)
            g2d.drawImage(img, x, y, null);
    }
    public boolean isLive(int matrix[][]){
        int d_x=(int)(Math.floor(x/45));
        int d_y=(int)(Math.floor(y/45));
        if(matrix[d_y+1][d_x+1]==4)
            return false;
        d_x=(int)(Math.floor((x+44)/45));
        d_y=(int)(Math.floor(y/45));
        if(matrix[d_y+1][d_x+1]==4)
            return false;
        d_x=(int)(Math.floor((x+44)/45));
        d_y=(int)(Math.floor((y+44)/45));
        if(matrix[d_y+1][d_x+1]==4)
            return false;
        d_x=(int)(Math.floor((x)/45));
        d_y=(int)(Math.floor((y+44)/45));
        if(matrix[d_y+1][d_x+1]==4)
            return false;
        return true;
    }
    public void setIsLive(int matrix[][]){
        if(isLive(matrix)==false){
            IS_LIVE=false;
        }
    }
    public boolean canMove(int v,int matrix[][]){
        if(v==0){
            int x=(int)(Math.floor((this.getX()-1)/45.0));
            int y_first=(this.getY()+20)/45;
            if(matrix[y_first+1][x+1]==0 || matrix[y_first+1][x+1]==2 ||matrix[y_first+1][x+1]==3)
                return false;
            int y_last=(int)(Math.floor((this.getY()+44)/45.0));
            if(matrix[y_last+1][x+1]==0 || matrix[y_last+1][x+1]==2 || matrix[y_last+1][x+1]==3)
                return false;
        }
        if(v==1){
            int x=(int)(Math.ceil((this.getX()+1)/45.0));
            int y_first=(this.getY()+20)/45;
            if(matrix[y_first+1][x+1]==0 || matrix[y_first+1][x+1]==2 || matrix[y_first+1][x+1]==3)
                return false;
            int y_last=(int)(Math.floor((this.getY()+44)/45.0));
            if(matrix[y_last+1][x+1]==0 || matrix[y_last+1][x+1]==2 || matrix[y_last+1][x+1]==3)
                return false;
        }
        if(v==2){
            int y=(int)(Math.floor((this.getY()-1)/45.0));
            int x_first=this.getX()/45;
            if(matrix[y+1][x_first+1]==0 || matrix[y+1][x_first+1]==2 || matrix[y+1][x_first+1]==3)
                return false;
            int x_last=(int)(Math.floor((this.getX()+44)/45.0));
            if(matrix[y+1][x_last+1]==0 || matrix[y+1][x_last+1]==2 || matrix[y+1][x_last+1]==3)
                return false;
        }
        if(v==3){
            int y=(int)(Math.ceil((this.getY()+1)/45.0));
            int x_first=this.getX()/45;
            if(matrix[y+1][x_first+1]==0 || matrix[y+1][x_first+1]==2 || matrix[y+1][x_first+1]==3)
                return false;
            int x_last=(int)(Math.floor((this.getX()+44)/45.0));
            if(matrix[y+1][x_last+1]==0 || matrix[y+1][x_last+1]==2 || matrix[y+1][x_last+1]==3)
                return false;
        }
        return true;
    }
    public boolean collisionPlayer(Player player){
        int x_player=player.getX();
        int y_player=player.getY();
        if(x_player >= x && x_player < x+45 && y_player>=y && y_player<y+45)
            return true;
        if(x_player+44 >= x && x_player+44 < x+45 && y_player>=y && y_player<y+45)
            return true;
        if(x_player >= x && x_player < x+45 && y_player +65>=y && y_player+65<y+45)
            return true;
        if(x_player+44 >= x && x_player+44 < x+45 && y_player +64>=y && y_player+64<y+45)
            return true;
        return false;
    }    
}
