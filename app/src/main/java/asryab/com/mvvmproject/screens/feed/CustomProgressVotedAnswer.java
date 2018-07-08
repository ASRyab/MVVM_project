package asryab.com.mvvmproject.screens.feed;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import asryab.com.mvvmproject.R;

public final class CustomProgressVotedAnswer extends View {

    private Paint paint         = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int progressColor   = 0;
    private int progressValue   = 0;

    public CustomProgressVotedAnswer(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttrs(attrs, 0);
    }

    public CustomProgressVotedAnswer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(attrs, defStyleAttr);
    }

    private void readAttrs(final AttributeSet attrs, int defStyleAttr) {
        paint.setStyle(Paint.Style.FILL);

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomProgressVotedAnswer, defStyleAttr, 0);
        setProgressValue(a.getInt(R.styleable.CustomProgressVotedAnswer_valueProgressPercents, 0));
        setProgressColor(a.getColor(R.styleable.CustomProgressVotedAnswer_colorProgressPercents, 0));
        a.recycle();
    }

    public int getProgressValue() {
        return progressValue;
    }

    public void setProgressValue(int progressValue) {
        this.progressValue = progressValue;
        invalidate();
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        paint.setColor(progressColor);
        invalidate();
    }

    @BindingAdapter({"valueProgressPercents"})
    public static void setValueProgressPercents(final CustomProgressVotedAnswer view, final int progress) {
        view.setProgressValue(progress);
    }

    @BindingAdapter({"colorProgressPercents"})
    public static void setColorProgressPercents(final CustomProgressVotedAnswer view, final int color) {
        view.setProgressColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, (int) (getWidth() * (float) progressValue / 100), getHeight(), paint);
    }
}
