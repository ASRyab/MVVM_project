package asryab.com.mvvmproject.screens.feed.polls.statistics;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.polls.PollAnswer;

public final class DiagramPollAnswersView extends View {

    private PollAnswer[]    answers;
    private final Paint     paintPie            = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint     paintShadowPie      = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final float     defaultOffsetAngle  = 1.5f;
    private float           coeffAnimation      = 0f;

    public DiagramPollAnswersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiagramPollAnswersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        final float density = getResources().getDisplayMetrics().density;
        paintPie            .setStyle(Paint.Style.FILL_AND_STROKE);
        paintPie            .setStrokeWidth(1f);
        paintShadowPie      .setColor(Color.TRANSPARENT);
        paintShadowPie      .setShadowLayer(6 * density, 0, 0, 0x40000000);
    }

    public void setAnswers(PollAnswer[] answers) {
        this.answers = answers;
        invalidate();
    }

    @BindingAdapter({"pollAnswers"})
    public static void setPollAnswers(final DiagramPollAnswersView view, final PollAnswer[] answers) {
        view.setAnswers(answers);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int width     = getWidth();
        final int height    = getHeight();
        final int padding   = getPaddingLeft();
        final int size      = Math.min(width, height) - 2 * padding;

        if (answers != null && size > 0) {
            final RectF circleBounds        = new RectF((width - size) / 2, (height - size) / 2, (width + size) / 2, (height + size) / 2);
            final RectF miniCircleBounds    = new RectF(
                    circleBounds.centerX() - circleBounds.width() / 4.5f,
                    circleBounds.centerY() - circleBounds.height() / 4.5f,
                    circleBounds.centerX() + circleBounds.width() / 4.5f,
                    circleBounds.centerY() + circleBounds.height() / 4.5f);

            final int maxPercent            = getMaxPercent();
            float startAngle                = 0f;
            float sweepAngle                = 0f;

            if (maxPercent > 0) {
                final Path[] pathSegments   = new Path[answers.length];
                final int[] colorsSegments  = new int[answers.length];

                for (int i = 0; i < answers.length; i++) {
                    if (answers[i].percents > 0) {
                        colorsSegments[i]   = ColorUtils.blendARGB(Color.WHITE, getResources().getColor(R.color.answer_full_color), (float) answers[i].percents / maxPercent);
                        startAngle          = startAngle + sweepAngle;
                        sweepAngle          = ((float) answers[i].percents / 100) * 360 * coeffAnimation;
                        pathSegments[i]     = getDonut(circleBounds, miniCircleBounds, startAngle, sweepAngle);
                    }
                }

                if (coeffAnimation >= 1f)
                    drawShadows(canvas, pathSegments);
                drawDonuts(canvas, pathSegments, colorsSegments);
            }
        }
    }

    private void drawDonuts(final Canvas canvas, final Path[] paths, final int[] colors) {
        for (int i = 0; i < paths.length; i++) {
            if (paths[i] != null) {
                paintPie.setColor(colors[i]);
                canvas.drawPath(paths[i], paintPie);
            }
        }
    }

    private void drawShadows(final Canvas canvas, final Path[] paths) {
        for (Path path : paths)
            if (path != null)
                canvas.drawPath(path, paintShadowPie);
    }

    private int getMaxPercent() {
        int max = 0;
        for (PollAnswer answer: answers)
            if (max < answer.percents)
                max = answer.percents;
        return max;
    }

    private Path getDonut(final RectF circle, final RectF miniCircle, final float startAngle, final float sweepAngle) {
        final Path path     = new Path();
        final float offset  = sweepAngle < 360f ? defaultOffsetAngle : .00001f;

        path.arcTo(circle, startAngle + offset, sweepAngle - 2 * offset, false);
        path.arcTo(miniCircle, startAngle + sweepAngle - offset, - sweepAngle + 2 * offset, false);
        path.close();

        return path;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimating();
    }

    public void setCoeffAnimation(float coeffAnimation) {
        this.coeffAnimation = coeffAnimation;
        invalidate();
    }

    private void startAnimating() {
        clearAnimation();
        final ObjectAnimator multiplyHeightCoeffAnim = ObjectAnimator.ofFloat(this, "coeffAnimation", 0f, 1f);
        multiplyHeightCoeffAnim.setDuration(600);
        multiplyHeightCoeffAnim.setInterpolator(new DecelerateInterpolator());
        multiplyHeightCoeffAnim.start();
    }

}
