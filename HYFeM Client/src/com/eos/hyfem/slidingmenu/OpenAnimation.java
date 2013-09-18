/*  Created by Edward Akoto on 12/31/12.
 *  Email akotoe@aua.ac.ke
 * 	Free for modification and distribution
 */

package com.eos.hyfem.slidingmenu;

import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;

public class OpenAnimation extends TranslateAnimation implements
		Animation.AnimationListener {

	private LinearLayout mainLayout;
	int panelWidth, deviceWidth;

	public OpenAnimation(LinearLayout layout, int width, int fromXType,
			float fromXValue, int toXType, float toXValue, int fromYType,
			float fromYValue, int toYType, float toYValue, int deviceWidth,
			int menuSpeed) {

		super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue,
				toYType, toYValue);
		// init
		mainLayout = layout;
		panelWidth = width;
		setDuration(menuSpeed);
		setFillAfter(false);
		setInterpolator(new AccelerateDecelerateInterpolator());
		setAnimationListener(this);
		mainLayout.startAnimation(this);
		this.deviceWidth = deviceWidth;
	}

	public void onAnimationEnd(Animation arg0) {
		LayoutParams params = (LayoutParams) mainLayout.getLayoutParams();
		params.width = deviceWidth;
		params.leftMargin = panelWidth;
		params.gravity = Gravity.LEFT;
		mainLayout.clearAnimation();
		mainLayout.setLayoutParams(params);
		mainLayout.requestLayout();

	}

	public void onAnimationRepeat(Animation arg0) {

	}

	public void onAnimationStart(Animation arg0) {

	}
}
