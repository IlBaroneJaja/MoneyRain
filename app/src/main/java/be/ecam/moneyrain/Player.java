package be.ecam.moneyrain;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by aurel on 13/04/2016.
 */
public class Player extends Movable {
    private boolean pushing;

    public Player(Point screenSize, Point position, Point speed){
        super(screenSize, position, speed);
        imageSize = new Point(79, 94);
        this.position.x = screenSize.x/2;
        this.position.y = screenSize.y-imageSize.y;
        pushing = false;
    }

    @Override
    protected void setImage() {
        image = BitmapFactory.decodeResource(GameView.res,R.drawable.persosmall);
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

        if( itemPos.y+itemSize.y > position.y && itemPos.x+itemSize.x > position.x && itemPos.x < (position.x+imageSize.x) )
            return true;
        else
            return false;
    }
}
