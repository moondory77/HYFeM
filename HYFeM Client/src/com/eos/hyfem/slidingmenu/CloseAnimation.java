/*  Created by Edward Akoto on 12/31/12.
 *  Email akotoe@aua.ac.ke
 * 	Free for modification and distribution
 */

package com.eos.hyfem.slidingmenu;

import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;

public class CloseAnimation extends TranslateAnimation implements
		TranslateAnimation.AnimationListener {

	private LinearLayout mainLayout;
	int panelWidth;

	public CloseAnimation(LinearLayout layout, int width, int fromXType,
			float fromXValue, int toXType, float toXValue, int fromYType,
			float fromYValue, int toYType, float toYValue, int deviceWidth,
			int menuSpeed) {

		super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue,
				toYType, toYValue);

		// Initialize
		mainLayout = layout;
		panelWidth = width;
		setDuration(menuSpeed);
		setFillAfter(false);
		setInterpolator(new AccelerateDecelerateInterpolator());
		setAnimationListener(this);

		// Clear left and right margins
		LayoutParams params = (LayoutParams) mainLayout.getLayoutParams();
		params.width = params.width = ViewGroup.LayoutParams.FILL_PARENT;
		params.rightMargin = 0;
		params.leftMargin = 0;
		mainLayout.setLayoutParams(params);
		mainLayout.requestLayout();
		mainLayout.startAnimation(this);

	}

	public void onAnimationEnd(Animation animation) {

	}

	public void onAnimationRepeat(Animation animation) {

	}

	public void onAnimationStart(Animation animation) {

	}

}
