package be.ecam.moneyrain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lur on 11-04-16.
 */
public class GameView extends View {

    private Paint mPaint;
    private Ball mBall;
    private int score;
    private int highScore;

    public GameView(Context context, AttributeSet aSet) {
        super(context, aSet);

        mPaint = new Paint();
        mBall = new Ball(20, 20, 10, 10, 10);
    }

    public void next() {
        if(mBall.x+mBall.radius > getWidth() || mBall.x-mBall.radius < 0)
            mBall.verticalHit();
        if(mBall.y+mBall.radius > getHeight() || mBall.y-mBall.radius < 0)
            mBall.horizontalHit();
        mBall.next();
        invalidate();
    }

    @Override
    synchronized public void onDraw(Canvas canvas) {
        canvas.drawCircle(mBall.x, mBall.y, mBall.radius, mPaint);

    }

//   /* TODO
//    public void onExit() // le joueur souhaite revenir au menu principal
//    {
//        setHighScore();
//        super.onDestroy();
//    }*/
//
//    public int computeScore()
//    {
//        int score = getScore();
//        /* TODO
//        if (objet touche personnage) // implémenter un listener à chaque collision d'objet
//        {
//            if (objet instanceof R.drawable.piece)
//                score += 10;
//            if (objet instanceof R.drawable.billet)
//                score += 100;
//
//        }
//        */
//        setScore(score);
//        return score;
//    }
//    public int getScore()
//    {
//        return score;
//    }
//
//    public void setScore(int score)
//    {
//        this.score += score;
//    }
//
//    public void setHighScore(int score)
//    {
//        this.highScore = score;
//    }

}
