/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Lenovo
 */
public class Item {
    public static int ITEM_BOOM=5,ITEM_BOOMSIZE=6,ITEM_SHOE=7,ITEM_HEART=8;
    private Image imgBoom,imgBoomsize,imgShoe,imgHeart;
    private int x,y;
    private int TYPE;
    private boolean HIDDEN=false;
    private long timeAppear;
    private long timeLine=5000;
    public Item(int x,int y,int type){
        this.TYPE=type;
        this.x=x;
        this.y=y;
        this.timeAppear=System.currentTimeMillis();
        loadImage();
    }
    public void countDown(){
        if(System.currentTimeMillis()-timeLine>timeAppear){
            HIDDEN=true;
        }
    }
    public boolean collisionPlayer(Player player){
        int x_player=player.getX();
        int y_player=player.getY();
        if(x_player >= x && x_player < x+45 && y_player>=y && y_player<y+45)
            return true;
        if(x_player+44 >= x && x_player+44 < x+45 && y_player>=y && y_player<y+45)
            return true;
        if(x_player >= x && x_player < x+45 && y_player +454>=y && y_player+44<y+45)
            return true;
        if(x_player+44 >= x && x_player+44 < x+45 && y_player +44>=y && y_player+44<y+45)
            return true;
        return false;
    }
    public void eatItem(Player player){
        if(this.collisionPlayer(player)==true){
            if(TYPE==5){
                HIDDEN=true;
                player.setCOUNT_BOOM();
            }
            else if(TYPE==6){
                HIDDEN=true;
                player.setBOOMSIZE();
            }
            else if(TYPE==7){
                HIDDEN=true;
                player.setSpeed();
            }
            else if(TYPE==8){
                HIDDEN=true;
                player.setCOUNT_HEART();
            }
        }
    }
    public boolean isHIDDEN() {
        return HIDDEN;
    }
    public void setHIDDEN(boolean HIDDEN) {
        this.HIDDEN = HIDDEN;
    }
   
    private void loadImage(){
        try {
            imgBoom=ImageIO.read(getClass().getResource("/Images/item_bomb.png"));
            imgBoomsize=ImageIO.read(getClass().getResource("/Images/item_bombsize.png"));
            imgShoe=ImageIO.read(getClass().getResource("/Images/item_shoe.png"));
            imgHeart=ImageIO.read(getClass().getResource("/Images/heart.png"));
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    public void drawItem(Graphics2D g2d){
        if(TYPE==5){
            g2d.drawImage(imgBoom, x, y, null);
        }
        else if(TYPE==6){
            g2d.drawImage(imgBoomsize, x, y, null);
        }
        else if(TYPE==7){
             g2d.drawImage(imgShoe, x, y, null);
        }
        else if(TYPE==8){
            g2d.drawImage(imgHeart, x, y, null);
        }
    }
}
