package be.ecam.moneyrain;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by aurel on 02/06/2016.
 */
public class ImagesContainer {

    private static boolean load = false;
    
    static Bitmap coin;
    static Bitmap paper;
    static Bitmap bomb;
    static Bitmap playerR;
    static Bitmap playerL;

    static Bitmap bg1A;
    static Bitmap bg1B;
    static Bitmap bg1C;

    static Bitmap bg2A;
    static Bitmap bg2B;
    static Bitmap bg2C;

    static Bitmap bg3A;
    static Bitmap bg3B;
    static Bitmap bg3C;

    static Bitmap bg4A;
    static Bitmap bg4B;
    static Bitmap bg4C;

    static void initImagesSize(Point screenSize, String level){
        switch (level) {
            case "BEGGAR":
                bg1A = Bitmap.createScaledBitmap(bg1A, screenSize.x, screenSize.y, true);
                bg1B = Bitmap.createScaledBitmap(bg1B, screenSize.x, screenSize.y, true);
                bg1C = Bitmap.createScaledBitmap(bg1C, screenSize.x, screenSize.y, true);
                break;
            case "CASHIER":
                bg2A = Bitmap.createScaledBitmap(bg2A, screenSize.x, screenSize.y, true);
                bg2B = Bitmap.createScaledBitmap(bg2B, screenSize.x, screenSize.y, true);
                bg2C = Bitmap.createScaledBitmap(bg2C, screenSize.x, screenSize.y, true);
                break;
            case "TRADER":
                bg3A = Bitmap.createScaledBitmap(bg3A, screenSize.x, screenSize.y, true);
                bg3B = Bitmap.createScaledBitmap(bg3B, screenSize.x, screenSize.y, true);
                bg3C = Bitmap.createScaledBitmap(bg3C, screenSize.x, screenSize.y, true);
                break;
            case "ILLUMINATI":
                bg4A = Bitmap.createScaledBitmap(bg4A, screenSize.x, screenSize.y, true);
                bg4B = Bitmap.createScaledBitmap(bg4B, screenSize.x, screenSize.y, true);
                bg4C = Bitmap.createScaledBitmap(bg4C, screenSize.x, screenSize.y, true);
                break;
        }
    }

    static void initImages(Resources res) {
        if(load)
            return;
        
        coin = BitmapFactory.decodeResource(res,R.drawable.piecesmall);
        paper = BitmapFactory.decodeResource(res,R.drawable.billetsmall);
        bomb = BitmapFactory.decodeResource(res,R.drawable.bombesmall);
        playerR = BitmapFactory.decodeResource(res,R.drawable.persosmall);
        playerL = BitmapFactory.decodeResource(res,R.drawable.persosmallreverse);

        bg1A = BitmapFactory.decodeResource(res,R.drawable.background1_a);
        bg1B = BitmapFactory.decodeResource(res,R.drawable.background1_b);
        bg1C = BitmapFactory.decodeResource(res,R.drawable.background1_c);

        bg2A = BitmapFactory.decodeResource(res,R.drawable.background2_a);
        bg2B = BitmapFactory.decodeResource(res,R.drawable.background2_b);
        bg2C = BitmapFactory.decodeResource(res,R.drawable.background2_c);

        bg3A = BitmapFactory.decodeResource(res,R.drawable.background3_a);
        bg3B = BitmapFactory.decodeResource(res,R.drawable.background3_b);
        bg3C = BitmapFactory.decodeResource(res,R.drawable.background3_c);

        bg4A = BitmapFactory.decodeResource(res,R.drawable.background4_a);
        bg4B = BitmapFactory.decodeResource(res,R.drawable.background4_b);
        bg4C = BitmapFactory.decodeResource(res,R.drawable.background4_c);

        load = true;
    }

    static Bitmap getImage(int id){
        switch (id){
            case R.drawable.piecesmall:
                return coin;
            case R.drawable.billetsmall:
                return paper;
            case R.drawable.bombesmall:
                return bomb;
            case R.drawable.persosmall:
                return playerR;
            case R.drawable.persosmallreverse:
                return playerL;
            case R.drawable.background1_a:
                return bg1A;
            case R.drawable.background1_b:
                return bg1B;
            case R.drawable.background1_c:
                return bg1C;
            case R.drawable.background2_a:
                return bg2A;
            case R.drawable.background2_b:
                return bg2B;
            case R.drawable.background2_c:
                return bg2C;
            case R.drawable.background3_a:
                return bg3A;
            case R.drawable.background3_b:
                return bg3B;
            case R.drawable.background3_c:
                return bg3C;
            case R.drawable.background4_a:
                return bg4A;
            case R.drawable.background4_b:
                return bg4B;
            case R.drawable.background4_c:
                return bg4C;
            default:
                return null;
        }
    }
}
