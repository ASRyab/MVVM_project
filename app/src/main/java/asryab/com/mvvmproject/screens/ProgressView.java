package asryab.com.mvvmproject.screens;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import asryab.com.mvvmproject.R;

public final class ProgressView extends View {

    private static final int    DEFAULT_ANGLE_CIRCLE = 210;
    private int                 angleOffset = 0;
    private boolean             animateWhenAttaching = false;

    private Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG), circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ObjectAnimator animatorInsideCircle;

    public ProgressView(Context context) {
        super(context);
        readAttrs(null, 0);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttrs(attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(attrs, defStyleAttr);
    }

    private void readAttrs(final AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            final TypedArray         a = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressView, defStyleAttr, 0);
            setBgType               (a.getInt(R.styleable.ProgressView_progressBgCircleType,                 0));
            setBgColor              (a.getColor(R.styleable.ProgressView_progressBgColor,                    getResources().getColor(R.color.app_background_default_color_light)));
            setBgStrokeWidth        (a.getDimension(R.styleable.ProgressView_progressBgCircleStrokeWidth,    0f));
            setCircleColor          (a.getColor(R.styleable.ProgressView_progressCircleColor,                getResources().getColor(R.color.base_red)));
            animateWhenAttaching =   a.getBoolean(R.styleable.ProgressView_progressAnimateWhenAttach,        false);
            a.recycle();
        } else {
            setBgType       (0);
            setBgColor      (getResources().getColor(R.color.app_background_default_color));
            setBgStrokeWidth(0f);
            setCircleColor  (getResources().getColor(R.color.base_red));
            animateWhenAttaching = false;
        }
    }

    public void setBgType(final int type) {
        bgPaint.setStyle(type == 0 ? Paint.Style.FILL_AND_STROKE : Paint.Style.STROKE);
    }

    public void setBgColor(final int color) {
        bgPaint.setColor(color);
    }

    public void setBgStrokeWidth(final float size) {
        bgPaint.setStrokeWidth(size);
    }

    public void setCircleColor(final int color) {
        circlePaint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final float radius      = Math.min(getWidth() / 2 - bgPaint.getStrokeWidth(), getHeight() / 2 - bgPaint.getStrokeWidth());
        final float centerX     = getWidth() / 2;
        final float centerY     = getHeight() / 2;
        canvas.drawCircle(centerX, centerY, radius, bgPaint);

        final float smallRadius = (2 * radius / 5);
        final float coeffRadius = (float) (angleOffset < 180 ? 180 - angleOffset : angleOffset - 180) / 180;
        final float offsetX     = (float) ((radius - smallRadius * coeffRadius - smallRadius / 4 - bgPaint.getStrokeWidth()) * Math.cos(Math.toRadians(DEFAULT_ANGLE_CIRCLE - angleOffset)));
        final float offsetY     = (float) ((radius - smallRadius * coeffRadius - smallRadius / 4 - bgPaint.getStrokeWidth()) * Math.sin(Math.toRadians(DEFAULT_ANGLE_CIRCLE - angleOffset)));
        canvas.drawCircle(centerX + offsetX, centerY - offsetY, smallRadius * coeffRadius, circlePaint);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        clearAnimation();

        if (visibility == VISIBLE)
            startAnimating();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (animateWhenAttaching)
            setVisibility(VISIBLE);
    }

    public void setAngleOffset(int angleOffset) {
        this.angleOffset = angleOffset;
        invalidate();
    }

    private void startAnimating() {
        if (animatorInsideCircle != null)
            animatorInsideCircle.cancel();
        animatorInsideCircle = ObjectAnimator.ofInt(this, "angleOffset", 0, 360);
        animatorInsideCircle.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorInsideCircle.setDuration(1800);
        animatorInsideCircle.setRepeatMode(ValueAnimator.RESTART);
        animatorInsideCircle.setRepeatCount(ValueAnimator.INFINITE);
        animatorInsideCircle.start();
    }
}
