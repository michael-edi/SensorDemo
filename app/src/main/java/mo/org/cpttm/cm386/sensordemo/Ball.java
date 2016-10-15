package mo.org.cpttm.cm386.sensordemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by jake on 8/10/2016.
 */
public class Ball extends View{
    public float x, y;
    int r;
    Paint mPaint = new Paint();
    static int colorLevel;
    public Ball(Context context, float x, float y, int r) {
        super(context);

        this.x = x;
        this.y = y;
        this.r = r;
        if (colorLevel==1)  mPaint.setARGB(255, 255, 255, 0); else
        mPaint.setARGB(255, 0, 255, 0);
    }
    static public void setColor(int level){
        colorLevel=level;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, mPaint);
    }
}
