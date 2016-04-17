package be.ecam.moneyrain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by aurel on 16/04/2016.
 */
public class Items {
    private ArrayList<Item> list;
    private Point screenSize;
    private int score;
    private String level;
    private long newItemNanoTime;

    public Items(Canvas canvas){
        list = new ArrayList<>();
        this.screenSize = new Point(canvas.getWidth(), canvas.getHeight());
        newItemNanoTime = System.nanoTime();
    }

    public void update(Player player){
        addItem();
        try{
            for (Iterator<Item> it = list.iterator(); it.hasNext(); ) {
                Item item = it.next();
                if (!player.itemCaught(item)) {
                    if (!(pathFinished(item)))
                        item.move();
                } else {
                    setScore(player.getScore());
//                    setLives(player.getLives());
                    it.remove();
                }
            }
        }
        catch (Exception e){
        }
    }

    public void draw(Canvas canvas){
        for (Iterator<Item> it = list.iterator(); it.hasNext();) {
            Item item = it.next();
            item.draw(canvas);
        }
    }

    private void addItem(){
        if(newItem()) {
            Random randomPos = new Random();
            Bitmap image;

            int imageID = getRandomItem();
            image = BitmapFactory.decodeResource(GameView.res, imageID);

            list.add(new Item(new Point(screenSize.x, screenSize.y),
                    new Point(randomPos.nextInt(screenSize.x - image.getWidth()), 0),
                    new Point(0, 5),
                    imageID));
        }
    }

    private int getRandomItem(){
        Random randomItem = new Random();
        int itemID = randomItem.nextInt(3);
        switch (itemID){
            case 0:
                return R.drawable.bombesmall;
            case 1:
                return R.drawable.billetsmall;
            case 2:
                return R.drawable.piecesmall;
            default:
                return R.drawable.piecesmall;
        }
    }

    private boolean newItem(){
        long currentNanoTime = System.nanoTime();

        if( (currentNanoTime-newItemNanoTime)/1000000 > getSpawnTime()){
            newItemNanoTime = currentNanoTime;
            return true;
        }
        else
            return false;
    }

    private int getSpawnTime(){
        switch(getLevel())
        {
            case "BEGGAR":
                return  1500;
            case "CASHIER":
                return 1200;
            case "TRADER":
                return 1000;
            case "ILLUMINATI":
                return 800;
            default:
                return 1500;
        }
    }

    private boolean pathFinished(Item item){
        if(item.getPosition().y > screenSize.y) {
            list.remove(item);
            return true;
        }
        else
            return false;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
