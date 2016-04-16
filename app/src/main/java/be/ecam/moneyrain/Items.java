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

        long currentNanoTime = System.nanoTime();
        long effectiveSpawnTime = (currentNanoTime-newItemNanoTime)/1000000;
        long spawnTime;
        switch(getLevel() != null ? getLevel() : "BEGGAR")
        {
            case "BEGGAR":
                spawnTime = 1500;
                break;
            case "CASHIER":
                spawnTime = 1200;
                break;
            case "TRADER":
                spawnTime = 1000;
                break;
            case "ILLUMINATI":
                spawnTime = 800;
                break;
            default:
                spawnTime = 1500;
                break;
        }

        if( effectiveSpawnTime > spawnTime){
            Random randomPos = new Random();
            Random randomItem = new Random();

            Bitmap image;
            int itemID = randomItem.nextInt(3);
            int imageID;
            Point imageSize;

            switch (itemID){
                case 0:
                    imageID = R.drawable.bombesmall;
                    break;
                case 1:
                    imageID = R.drawable.billetsmall;
                    break;
                case 2:
                    imageID = R.drawable.piecesmall;
                    break;
                default:
                    imageID = R.drawable.piecesmall;
            }

            image = BitmapFactory.decodeResource(GameView.res,imageID);
            imageSize = new Point(image.getWidth(),image.getHeight());

            newItemNanoTime = currentNanoTime;
            list.add(new Item(new Point(screenSize.x, screenSize.y),
                    new Point(randomPos.nextInt(screenSize.x-imageSize.x),0),
                    new Point(0, 5),
                    imageID,
                    imageSize));
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
