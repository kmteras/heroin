package com.example.heroin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


public class RenderView extends View {

    public enum AnimationState {
        IDLE,
        SLEEP_TRANSITION,
        SLEEP
    }

    // 429 x 300
    // Idle: 264 - 305

    public static RenderView renderView;

    private final static int IDLE_FRAMES = 155;
    private final static int SLEEP_TRANSITION_FRAME = 265;
    private final static int SLEEP_FRAMES = 305;
    private final static int X_FRAMES = 15;
    private final static int Y_FRAMES = 11;

    private final int SPRITE_WIDTH;
    private final int SPRITE_HEIGHT;

    private final Paint paint;
    private final Bitmap idleSpritemap;
    private final Bitmap sleepSpritemap;
    private final Rect src;
    private final Rect dest;

    private int frame = 0;
    private int frameIndex = 0;

    public AnimationState currentState = AnimationState.SLEEP;
    private int[] idleFrames = {0, 1, 2, 3, 4, 5};
    private int[] transitionFrames = {6, 7, 8, 9, 10, 11, 12, 13};
    private int[] sleepFrames = {14, 15, 16, 17, 18, 19, 20};

    public RenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(false);
        paint.setFilterBitmap(false);
        paint.setDither(false);
        idleSpritemap = BitmapFactory.decodeResource(getResources(), R.drawable.new_idle);
        sleepSpritemap = BitmapFactory.decodeResource(getResources(), R.drawable.sleep);
        SPRITE_WIDTH = idleSpritemap.getWidth() / X_FRAMES;
        SPRITE_HEIGHT = idleSpritemap.getHeight() / Y_FRAMES;
        src = new Rect(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT);
        dest = new Rect(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT);
        renderView = this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int curFrame = frame;

        if (frame >= IDLE_FRAMES) {
            curFrame = IDLE_FRAMES * 2 - frame;
        }

        src.left = (curFrame % X_FRAMES) * SPRITE_WIDTH;
        src.right = (curFrame % X_FRAMES) * SPRITE_WIDTH + SPRITE_WIDTH;
        src.top = (curFrame / X_FRAMES) * SPRITE_HEIGHT;
        src.bottom = (curFrame / X_FRAMES) * SPRITE_HEIGHT + SPRITE_HEIGHT;

        if (currentState == AnimationState.IDLE) {
            canvas.drawBitmap(idleSpritemap, src, dest, paint);
        } else {

        }

        switch (currentState) {
            case IDLE:
                frame = (frame + 1) % (IDLE_FRAMES * 2 - 1);
                break;
            case SLEEP_TRANSITION:
                frameIndex = (frameIndex + 1) % transitionFrames.length;
                frame = transitionFrames[frameIndex];
                break;
            case SLEEP:
                frameIndex = (frameIndex + 1) % sleepFrames.length;
                frame = sleepFrames[frameIndex];
                break;
        }
    }
}
