package be.ecam.moneyrain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

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
        try{
            for (Iterator<Item> it = list.iterator(); it.hasNext(); ) {
                Item item = it.next();
                if (!player.itemCaught(item)) {
                    if (!(pathFinished(item)))
                        item.move();
                } else {
                    setScore(player.getScore());
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
                    new Point(0, getDifficulty("itemSpeed")),
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
                        new Point(i*image.getWidth()+offset, 0),
                        new Point(0, (int)(1.5*getDifficulty("itemSpeed"))),
                        R.drawable.bombesmall));
            }
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
        if(bombPattern || (currentNanoTime-bombPatternNanoTime)/1000000 > 10000){
            if((currentNanoTime-bombPatternNanoTime)/1000000 > 12000) {
                addBombPattern();
                bombPattern = true;
                bombPatternNanoTime = currentNanoTime;
            }
            else if((currentNanoTime-bombPatternNanoTime)/1000000 > 2000 && bombPattern == true) {
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

    private int getDifficulty(String type){
        switch(type)
        {
            case "spawnInterval":
                return (int) (1500/getLevelRatio());
            case "itemSpeed":
                return  (int)(5*getLevelRatio());
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
                return 1.2f;
            case "TRADER":
                return 1.5f;
            case "ILLUMINATI":
                return 2f;
            default:
                return 1f;
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
    public void setLevel(String level) {
        this.level = level;
    }
}
