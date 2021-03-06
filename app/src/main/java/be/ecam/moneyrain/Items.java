package be.ecam.moneyrain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Iterator;
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
    private long bombPatternNanoTime;
    private boolean bombPattern;

    public Items(Canvas canvas){
        list = new ArrayList<>();
        this.screenSize = new Point(canvas.getWidth(), canvas.getHeight());
        newItemNanoTime = System.nanoTime();
        bombPatternNanoTime = System.nanoTime();
        bombPattern = false;
    }

    public void update(Player player){
        addItem();
        for (Iterator<Item> it = list.iterator(); it.hasNext(); ) {
            Item item = it.next();
            if (!player.itemCaught(item)) {
                if (item.checkCollision() != "bottom")
                    item.move();
                else
                    it.remove();
            } else {
                setScore(player.getScore());
                it.remove();
            }
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
                    new PointF(randomPos.nextInt(screenSize.x - image.getWidth()), 0),
                    new PointF(0, getDifficulty("itemSpeed")),
                    imageID));
        }
    }

    private void addBombPattern(){
        Bitmap image = BitmapFactory.decodeResource(GameView.res,  R.drawable.bombesmall);
        int offset = (screenSize.x%image.getWidth())/2;
        int elementsNumber = (screenSize.x) / image.getWidth();
        ArrayList holesPos = new ArrayList();
        Random randomGen = new Random();
        int randomPos = randomGen.nextInt(elementsNumber-2);
        holesPos.add(randomPos);
        holesPos.add(randomPos+1);
        holesPos.add(randomPos+2);

        for(int i=0; i<elementsNumber;i++) {
            if(!holesPos.contains(i)){
                list.add(new Item(new Point(screenSize.x, screenSize.y),
                        new PointF(i*image.getWidth()+offset, 0),
                        new PointF(0, getDifficulty("itemSpeed")),
                        R.drawable.bombesmall));
            }
        }
    }

    private int getRandomItem(){
        Random randomItem = new Random();
        int itemID = randomItem.nextInt((int) (3*getLevelRatio()));
        switch (itemID){
            case 0:
                return R.drawable.piecesmall;
            case 1:
                return R.drawable.billetsmall;
            default:
                return R.drawable.bombesmall;
        }
    }

    private boolean newItem(){
        long currentNanoTime = System.nanoTime();
        if(bombPattern || (currentNanoTime-bombPatternNanoTime)/1000000 > 10000){
            if((currentNanoTime-bombPatternNanoTime)/1000000 > 12000) {
                addBombPattern();
                bombPattern = true;
                bombPatternNanoTime = currentNanoTime;
            }
            else if((currentNanoTime-bombPatternNanoTime)/1000000 > 2000 && bombPattern) {
                bombPattern = false;
                bombPatternNanoTime = currentNanoTime;
            }
            return false;
        }
        else if( (currentNanoTime-newItemNanoTime)/1000000 > getDifficulty("spawnInterval")){
            newItemNanoTime = currentNanoTime;
            return true;
        }
        else
            return false;
    }

    private float getDifficulty(String type){
        switch(type)
        {
            case "spawnInterval":
                return 1000/getLevelRatio();
            case "itemSpeed":
                return 6*getLevelRatio();
            default:
                return 0;
        }
    }

    private float getLevelRatio(){
        switch(level)
        {
            case "BEGGAR":
                return  1f;
            case "CASHIER":
                return 1.5f;
            case "TRADER":
                return 2f;
            case "ILLUMINATI":
                return 2.5f;
            default:
                return 1f;
        }
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void setLevel(String level) {
        this.level = level;
    }
}
