package com.anykey.library.progressbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by dogo on 7/17/15.
 */
public class ProgressButton extends RelativeLayout {

    private ProgressBar mSpinnerView;
    private TextView mText;
    private boolean mAutoLoading = true;

    public ProgressButton(@NonNull Context context) {
        this(context, null);
    }

    public ProgressButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFromAttributes(context, attrs, defStyleAttr);
    }

    private void initFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        initView(context);
        readAttributes(context, attrs, defStyleAttr);
        this.setClickable(true);
    }

    private void initView(@NonNull Context context) {
        inflate(context, R.layout.view_progress_button, this);

        mText = (TextView) findViewById(R.id.pb_text);
        mSpinnerView = (ProgressBar) findViewById(R.id.progress_loader);
    }

    /**
     * Reads the attributes associated with this view, setting any values found
     *
     * @param context      The context to retrieve the styled attributes with
     * @param attrs        The desired attributes {@link AttributeSet} to be retrieved.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies
     *                     defaults values for the TypedArray. Can be
     *                     0 to not look for defaults.
     */
    private void readAttributes(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        if (attrs == null || isInEditMode()) {
            return;
        }

        final TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton, R.attr.ProgressButtonStyle, R.style.ProgressButton_attrs);

        if (attr == null) return;

        try {

            if (attr.hasValue(R.styleable.ProgressButton_pb_backgroundColor)) {
                setBackgroundColor(attr.getInteger(R.styleable.ProgressButton_pb_backgroundColor, Color.WHITE));
            } else {
                setBackgroundCompat(getBackground());
            }

            if (attr.hasValue(R.styleable.ProgressButton_pb_text)) {
                mText.setText(attr.getString(R.styleable.ProgressButton_pb_text));
            }

            if (attr.hasValue(R.styleable.ProgressButton_pb_textColor)) {
                mText.setTextColor(attr.getInteger(R.styleable.ProgressButton_pb_textColor, Color.BLACK));
            }

            if (attr.hasValue(R.styleable.ProgressButton_pb_textSize)) {
                mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, attr.getDimension(R.styleable.ProgressButton_pb_textSize, 16));
            }

            if (attr.hasValue(R.styleable.ProgressButton_pb_textAllCaps)) {
                mText.setAllCaps(attr.getBoolean(R.styleable.ProgressButton_pb_textAllCaps, false));
            }

            if (attr.hasValue(R.styleable.ProgressButton_pb_autoLoading)) {
                mAutoLoading = attr.getBoolean(R.styleable.ProgressButton_pb_autoLoading, true);
            }

            if (attr.hasValue(R.styleable.ProgressButton_pb_drawableLeft)) {
                mText.setCompoundDrawablesWithIntrinsicBounds(attr.getDrawable(R.styleable.ProgressButton_pb_drawableLeft), null, null, null);
            }

            if (attr.hasValue(R.styleable.ProgressButton_pb_drawableTop)) {
                mText.setCompoundDrawablesWithIntrinsicBounds(null, attr.getDrawable(R.styleable.ProgressButton_pb_drawableTop), null, null);
            }

            if (attr.hasValue(R.styleable.ProgressButton_pb_drawableRight)) {
                mText.setCompoundDrawablesWithIntrinsicBounds(null, null, attr.getDrawable(R.styleable.ProgressButton_pb_drawableRight), null);
            }

            if (attr.hasValue(R.styleable.ProgressButton_pb_drawableBottom)) {
                mText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, attr.getDrawable(R.styleable.ProgressButton_pb_drawableBottom));
            }

            if (attr.hasValue(R.styleable.ProgressButton_pb_drawablePadding)) {
                int drawablePadding = (int) attr.getDimension(R.styleable.ProgressButton_pb_drawablePadding, 0.0f);
                mText.setCompoundDrawablePadding(drawablePadding);
            }

            if (attr.hasValue(R.styleable.ProgressButton_pb_textAppearance)) {
                Typeface accentTypeface = getCustomTypeface(attr.getString(R.styleable.ProgressButton_pb_textAppearance));
                mText.setTypeface(accentTypeface);
            }
        } finally {
            attr.recycle();
        }
    }

    private Typeface getCustomTypeface(@NonNull String fontPath) {
        return Typeface.createFromAsset(getContext().getAssets(), fontPath);
    }

    /**
     * Ensures that the loading animation will be displayed when the user clicks the button.
     */
    @Override
    public boolean performClick() {
        boolean isClicked = super.performClick();
        if (isClicked && mAutoLoading) {
            startLoadingAnimation();
        }
        return isClicked;
    }

    /**
     * Display a loading animation if the user has clicked the button.
     */
    @SuppressWarnings("WeakerAccess")
    public void startLoadingAnimation() {
        mText.setVisibility(INVISIBLE);
        mSpinnerView.setVisibility(VISIBLE);
        this.setClickable(false);
        this.invalidate();
    }

    /**
     * Hide the loading animation.
     */
    @SuppressWarnings("WeakerAccess")
    public void stopLoadingAnimation() {
        mText.setVisibility(VISIBLE);
        mSpinnerView.setVisibility(INVISIBLE);
        this.setClickable(true);
        this.invalidate();
    }

    /**
     * Set the View's background. Masks the API changes made in Jelly Bean.
     *
     * @param drawable set {@link Drawable} using correct API and padding
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void setBackgroundCompat(Drawable drawable) {
        int pL = getPaddingLeft();
        int pT = getPaddingTop();
        int pR = getPaddingRight();
        int pB = getPaddingBottom();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
        setPadding(pL, pT, pR, pB);
    }
}
