package be.ecam.moneyrain;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.widget.TextView;

/**
 * Created by aurel on 13/04/2016.
 */
public class Player extends Movable  {
    private boolean pushing = false;
    private int lives;
    private int score;

    public Player(Point screenSize, Point position, Point speed){
        super(screenSize, position, speed);
        setImage(R.drawable.persosmall);
        this.position.x = screenSize.x/2;
        this.position.y = screenSize.y-imageSize.y;
    }

    public void move(){
        if (pushing)
            moveRight();
        else
            moveLeft();
    }

    public void moveRight(){
        if(checkCollision() != "right")
            position.x += speed.x;
    }

    public void moveLeft(){
        if(checkCollision() != "left")
            position.x += -speed.x;
    }

    public void setPushing(){
        if(pushing == true)
            pushing = false;
        else
            pushing = true;
    }

    public boolean itemCaught(Item item){
        Point itemPos = new Point(item.getPosition());
        Point itemSize = new Point(item.getImageSize());

        if( itemPos.y+itemSize.y > position.y && itemPos.x+itemSize.x > position.x && itemPos.x < (position.x+imageSize.x) ) {
            switch(item.getImageID())
            {
                case R.drawable.bombesmall:
                    setLives(getLives()-1);
                    GameActivity.playBomb();
                    break;
                case R.drawable.piecesmall:
                    incrementScore(10);
                    GameActivity.playCoin();
                    break;
                case R.drawable.billetsmall:
                    incrementScore(100);
                    GameActivity.playCoin();
            }
            return true;
        }
        else
            return false;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int score) {
        this.score += score;
    }
}
