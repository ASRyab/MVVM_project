package asryab.com.mvvmproject.dialogs;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import asryab.com.mvvmproject.R;

public final class IndeterminateProgressView extends View {

    private Paint paint             = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float widthStepFill     = 0f;
    private float withStepOffset    = 0f;
    private float refreshingOffset  = 0f;
    private Path[] paths;
    private ObjectAnimator indeterminateAnimationRefreshing;

    public IndeterminateProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttrs(attrs, 0);
    }

    public IndeterminateProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(attrs, defStyleAttr);
    }

    private void readAttrs(final AttributeSet attrs, int defStyleAttr) {
        paint.setStyle(Paint.Style.FILL);

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.IndeterminateProgressView, defStyleAttr, 0);
        setColorProgress(a.getColor(R.styleable.IndeterminateProgressView_colorProgress, Color.BLACK));
        setWidthStepFill(a.getDimension(R.styleable.IndeterminateProgressView_widthStepFill, 0f));
        setWithStepOffset(a.getDimension(R.styleable.IndeterminateProgressView_widthStepOffset, 0f));
        a.recycle();
        prepareTranslateAnimationRefreshing();
    }

    public void setRefreshingOffset(float refreshingOffset) {
        this.refreshingOffset = refreshingOffset;
        invalidate();
    }

    public void setColorProgress(final int colorProgress) {
        paint.setColor(colorProgress);
    }

    public void setWidthStepFill(float widthStepFill) {
        this.widthStepFill = widthStepFill;
    }

    public void setWithStepOffset(float withStepOffset) {
        this.withStepOffset = withStepOffset;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        preparePaths(getWidth(), getHeight());
    }

    private void preparePaths(final int width, final int height) {
        if (width > 0 && height > 0 && paths == null) {
            final int countRects = (int) (width / (widthStepFill + withStepOffset));
            paths = new Path[countRects + 3];

            for (int i = 0; i < countRects + 3; i++)
                paths[i] = getPath(i * (widthStepFill + withStepOffset), height);

            clearAnimation();
            indeterminateAnimationRefreshing.start();
        }
    }

    private Path getPath(final float leftTopPos, final int height) {
        final Path path = new Path();
        path.moveTo(leftTopPos, 0f);
        path.lineTo(leftTopPos + widthStepFill, 0f);
        path.lineTo(leftTopPos + widthStepFill / 2, height);
        path.lineTo(leftTopPos - widthStepFill / 2, height);
        path.close();
        return path;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (paths != null) {
            for (Path path : paths) {
                final Path drawPath = new Path(path);
                drawPath.offset(refreshingOffset, 0f);
                canvas.drawPath(drawPath, paint);
            }
        }
    }

    private void prepareTranslateAnimationRefreshing() {
        indeterminateAnimationRefreshing = ObjectAnimator.ofFloat(this, "refreshingOffset", 0f, - (widthStepFill + withStepOffset));
        indeterminateAnimationRefreshing.setInterpolator(new LinearInterpolator());
        indeterminateAnimationRefreshing.setRepeatMode(ValueAnimator.RESTART);
        indeterminateAnimationRefreshing.setRepeatCount(ValueAnimator.INFINITE);
        indeterminateAnimationRefreshing.setStartDelay(0);
        indeterminateAnimationRefreshing.setDuration(800);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        indeterminateAnimationRefreshing.cancel();
        indeterminateAnimationRefreshing = null;
    }
}
