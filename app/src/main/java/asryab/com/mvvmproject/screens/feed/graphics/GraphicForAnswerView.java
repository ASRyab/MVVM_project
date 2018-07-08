package asryab.com.mvvmproject.screens.feed.graphics;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import asryab.com.mvvmproject.R;
import asryab.com.mvvmproject.models.polls.PollStatistics;

public final class GraphicForAnswerView extends View {

    private static final int DEFAULT_OFFSET_BETWEEN_TEXT_AND_CANVAS = 6;
    private float           defaultOffsetBetweenTextAndCanvas, defaultTextHeight;
    private final int       sliceOnEachParts = 4;
    private float           coeffMultiplyHeight = 0f;

    private PollStatistics pollStatistics;
    private GraphicType graphicType = GraphicType.AGE;
    private int             percentageTextColor, captionsTextColor, dividersColor, fillAreaColor;
    private float           percentageTextSize, captionsTextSize;
    private final Paint     textPaint = new Paint(Paint.ANTI_ALIAS_FLAG), fillAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF           areaGraph = new RectF();

    @BindingAdapter({"pollStatisticsGraphic"})
    public static void setPollStatistics(final GraphicForAnswerView graphicForAnswerView, final PollStatistics pollStatistics) {
        graphicForAnswerView.setPollStatistics(pollStatistics);
    }

    @BindingAdapter({"typeGraphic"})
    public static void setGraphicType(final GraphicForAnswerView graphicForAnswerView, final GraphicType graphicType) {
        graphicForAnswerView.setGraphicType(graphicType);
    }

    public GraphicForAnswerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttrs(attrs, 0);
    }

    public GraphicForAnswerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(attrs, defStyleAttr);
    }

    private void readAttrs(final AttributeSet attrs, int defStyleAttr) {
        defaultOffsetBetweenTextAndCanvas   = getResources().getDisplayMetrics().density * DEFAULT_OFFSET_BETWEEN_TEXT_AND_CANVAS;
        textPaint                           .setStyle(Paint.Style.FILL_AND_STROKE);
        fillAreaPaint                       .setStyle(Paint.Style.FILL);

        final TypedArray a  = getContext().obtainStyledAttributes(attrs, R.styleable.GraphicForAnswerView, defStyleAttr, 0);
        percentageTextColor = a.getInt(R.styleable.GraphicForAnswerView_colorTextPercentage, Color.BLACK);
        captionsTextColor   = a.getInt(R.styleable.GraphicForAnswerView_colorTextCaptions, Color.BLACK);
        dividersColor       = a.getInt(R.styleable.GraphicForAnswerView_dividersColor, Color.BLACK);
        fillAreaColor       = a.getInt(R.styleable.GraphicForAnswerView_fillMaxColor, Color.BLACK);
        percentageTextSize  = a.getDimension(R.styleable.GraphicForAnswerView_sizeTextPercentage, 10 * getContext().getResources().getDisplayMetrics().density);
        captionsTextSize    = a.getDimension(R.styleable.GraphicForAnswerView_sizeTextCaptions, 14 * getContext().getResources().getDisplayMetrics().density);

        a.recycle();
    }

    public void setPollStatistics(final PollStatistics pollStatistics) {
        this.pollStatistics = pollStatistics;
        invalidate();
    }

    public void setGraphicType(final GraphicType graphicType) {
        this.graphicType = graphicType;
        invalidate();
    }

    private void prepareArea(final int width, final int height) {
        final Rect percentageTextBounds = new Rect();
        final Rect captionsTextBounds   = new Rect();

        textPaint           .setTextSize(percentageTextSize);
        textPaint           .getTextBounds("100%", 0, 4, percentageTextBounds);

        textPaint           .setTextSize(captionsTextSize);
        textPaint           .getTextBounds("50-50", 0, 5, captionsTextBounds);
        defaultTextHeight   = captionsTextBounds.height();

        areaGraph.left      = percentageTextBounds.width() + defaultOffsetBetweenTextAndCanvas;
        areaGraph.top       = percentageTextBounds.height() / 2;
        areaGraph.right     = width;
        areaGraph.bottom    = height - captionsTextBounds.width() - 2 * defaultOffsetBetweenTextAndCanvas;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimateGraphics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int width     = getWidth();
        final int height    = getHeight();

        if (width > 0 && height > 0) {
            prepareArea(width, height);
            drawCanvas(canvas);
            drawPercentage(canvas);

            if (pollStatistics != null) {
                final float offsetBetweenColumnsAge     = areaGraph.width() / 20;
                final float offsetBetweenColumnsGender  = areaGraph.width() / 14;
                drawCaptions(canvas, offsetBetweenColumnsAge, offsetBetweenColumnsGender);

                switch (graphicType) {
                    case GENDER:
//                        For testing
//                        drawColumns(canvas, offsetBetweenColumnsGender, 42, 58);
                        drawColumns(canvas, offsetBetweenColumnsGender, pollStatistics.gender.male, pollStatistics.gender.female);
                        break;
                    case AGE:
//                        For testing
//                        drawColumns(canvas, offsetBetweenColumnsAge, 26, 10, 6, 55, 3);
                        drawColumns(canvas, offsetBetweenColumnsAge,
                            pollStatistics.age.age_18_24,
                            pollStatistics.age.age_25_32,
                            pollStatistics.age.age_33_42,
                            pollStatistics.age.age_43_50,
                            pollStatistics.age.age_more_50);
                        break;
                }
            }
        }
    }

    private void drawPercentage(final Canvas canvas) {
        final int partValue     = 100 / sliceOnEachParts;
        final float partHeight  = areaGraph.height() / sliceOnEachParts;

        textPaint.setColor(percentageTextColor);
        textPaint.setTextSize(percentageTextSize);
        textPaint.setTextAlign(Paint.Align.RIGHT);

        for (int i = 0; i <= sliceOnEachParts; i++)
            canvas.drawText(String.format("%s%%", 100 - partValue * i), areaGraph.left - defaultOffsetBetweenTextAndCanvas, 2 * areaGraph.top + i * partHeight, textPaint);
    }

    private void drawCaptions(final Canvas canvas, final float offsetAge, final float offsetGender) {
        textPaint.setColor(captionsTextColor);
        textPaint.setTextSize(captionsTextSize);
        switch (graphicType) {
            case AGE:
                textPaint.setTextAlign(Paint.Align.RIGHT);
                drawAgeCaptions(canvas, offsetAge, "18-24", "25-32", "33-42", "43-50", ">50");
                break;
            case GENDER:
                textPaint.setTextAlign(Paint.Align.CENTER);
                drawGenderCaptions(canvas, offsetGender);
                break;
        }
    }

    private void drawAgeCaptions(final Canvas canvas, final float offset, final String... labels) {
        final float partWidth = (areaGraph.width() - 6 * offset) / 5;
        final float fixOffset = 4 * (getHeight() - (areaGraph.bottom + 2 * defaultOffsetBetweenTextAndCanvas)) / 9;
        for (int i = 1; i <= labels.length; i++) {
            canvas.drawTextOnPath(
                    labels[i - 1],
                    getAnglePathForText(areaGraph.left + fixOffset + i * offset + (i * 2 - 1) * partWidth / 2, areaGraph.bottom + defaultTextHeight / 2 + 2 * defaultOffsetBetweenTextAndCanvas),
                    0f,
                    0f,
                    textPaint);
        }
    }

    private Path getAnglePathForText(final float endPointX, final float endPointY) {
        final Path path = new Path();
        path.moveTo(endPointX - 3 * (getHeight() - endPointY) / 4, getHeight());
        path.lineTo(endPointX, endPointY);
        return path;
    }

    private void drawGenderCaptions(final Canvas canvas, final float offset) {
        final float partWidth = (areaGraph.width() - 3 * offset) / 2;
        canvas.drawText(
                getResources().getString(R.string.male),
                areaGraph.left + offset + partWidth / 2,
                areaGraph.bottom + defaultTextHeight + 2 * defaultOffsetBetweenTextAndCanvas,
                textPaint);
        canvas.drawText(
                getResources().getString(R.string.female),
                areaGraph.left + 2 * offset + 3 * partWidth / 2,
                areaGraph.bottom + defaultTextHeight + 2 * defaultOffsetBetweenTextAndCanvas,
                textPaint);
    }

    private void drawColumns(final Canvas canvas, final float offset, final float... values) {
        float max = 0;
        for (float value: values)
            if (max < value)
                max = value;
        if (max > 0) {
            final float widthRect = (areaGraph.width() - (values.length + 1) * offset) / values.length;
            for (int i = 1; i <= values.length; i++) {
                fillAreaPaint.setColor(ColorUtils.blendARGB(Color.WHITE, fillAreaColor, values[i - 1] / max));
                canvas.drawRect(
                        areaGraph.left + i * offset + (i - 1) * widthRect,
                        areaGraph.bottom - ((values[i - 1] / 100) * areaGraph.height()) * coeffMultiplyHeight,
                        areaGraph.left + i * offset + i * widthRect,
                        areaGraph.bottom, fillAreaPaint);
            }
        }
    }

    private void drawCanvas(final Canvas canvas) {
        final float dividerHeight = getResources().getDisplayMetrics().density;
        fillAreaPaint.setColor(Color.WHITE);
        canvas.drawRect(areaGraph, fillAreaPaint);

        fillAreaPaint.setColor(dividersColor);
        for (int i = 1; i < sliceOnEachParts; i++)
            canvas.drawRect(
                    areaGraph.left,
                    areaGraph.top + (areaGraph.height() / sliceOnEachParts) * i,
                    areaGraph.right, areaGraph.top+ (areaGraph.height() / sliceOnEachParts) * i + dividerHeight,
                    fillAreaPaint);
    }

    public void setCoeffMultiplyHeight(float coeffMultiplyHeight) {
        this.coeffMultiplyHeight = coeffMultiplyHeight;
        invalidate();
    }

    private void startAnimateGraphics() {
        clearAnimation();
        final ObjectAnimator multiplyHeightCoeffAnim = ObjectAnimator.ofFloat(this, "coeffMultiplyHeight", 0f, 1f);
        multiplyHeightCoeffAnim.setDuration(600);
        multiplyHeightCoeffAnim.setInterpolator(new DecelerateInterpolator());
        multiplyHeightCoeffAnim.start();
    }
}
