package be.ecam.moneyrain;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by aurel on 13/04/2016.
 */
public class Player extends Movable  {
    private String move = "none";
    private int lives;
    private int score;

    public Player(Point screenSize, PointF position, PointF speed){
        super(screenSize, position, speed);
        setImage(R.drawable.persosmall);
        this.position.x = screenSize.x/2;
        this.position.y = screenSize.y-imageSize.y;
    }

    public void move(){
        if (move == "right") {
            image = BitmapFactory.decodeResource(GameView.res,R.drawable.persosmall);
            moveRight();
        }
        else if (move == "left") {
            image = BitmapFactory.decodeResource(GameView.res,R.drawable.persosmallreverse);
            moveLeft();
        }
    }

    public void moveRight(){
        if(checkCollision() != "right")
            position.x += speed.x;
    }

    public void moveLeft(){
        if(checkCollision() != "left")
            position.x += -speed.x;
    }

    public void setMove(String move){
        this.move = move;
    }

    public boolean itemCaught(Item item){
        PointF itemPos = item.getPosition();
        Point itemSize = new Point(item.getImageSize());


        if( itemPos.y+itemSize.y*0.8 > position.y && itemPos.x+itemSize.x*0.8 > position.x && itemPos.x < (position.x+imageSize.x*0.8) ) {
            switch(item.getImageID())
            {
                case R.drawable.bombesmall:
                    setLives(getLives()-1);
                    GameActivity.playBomb();
                    break;
                case R.drawable.piecesmall:
                    incrementScore(50);
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
