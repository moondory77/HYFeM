package com.eos.hyfem.slidingmenu;

import com.eos.hyfem.R;
import com.eos.hyfem.slidingmenu.CloseAnimation;
import com.eos.hyfem.slidingmenu.OpenAnimation;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SlidingMenuActivity extends Activity implements OnClickListener {
	private LinearLayout mainLayout, menuLayout;
	private FrameLayout.LayoutParams menuLayoutPrams;
	private int menuWidth, menuSpeed, deviceWidth;
	private static boolean menuExpanded;
	private Button btn1, btn2, btn3, btn4;
	private ImageButton btn_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.menu:
				slideMenuAnimationToggle();
				break;
			case R.id.btn1:
				Toast.makeText(getApplicationContext(), "Test Menu 1",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.btn2:
				Toast.makeText(getApplicationContext(), "Test Menu 2",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.btn3:
				Toast.makeText(getApplicationContext(), "Test Menu 3",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.btn4:
				Toast.makeText(getApplicationContext(), "Test Menu 4",
					Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	protected void initSildeMenu() {
		// init left menu width and device width
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		menuWidth = (int) ((metrics.widthPixels) * 0.80); // SlideMenu가 화면의 80%를 차지한다.
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		deviceWidth = (int) metrics.widthPixels;

		// init main view
		mainLayout = (LinearLayout) findViewById(R.id.mainlayout);

		// init left menu
		menuLayout = (LinearLayout) findViewById(R.id.menuLayout);
		menuLayoutPrams = (FrameLayout.LayoutParams) menuLayout
				.getLayoutParams();
		menuLayoutPrams.width = menuWidth;
		menuLayout.setLayoutParams(menuLayoutPrams);
		menuSpeed = Integer.parseInt(getString(R.string.menu_animation_speed));
	}

	protected void testMenu() {
		// init ui
		btn_menu = (ImageButton) findViewById(R.id.menu);
		btn_menu.setOnClickListener(this);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
	}

	/**
	 * left menu toggle
	 */
	public void slideMenuAnimationToggle() {
		if (!menuExpanded) {
			// Expand
			new OpenAnimation(mainLayout, menuWidth, Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f, deviceWidth, menuSpeed);

			// disable all of main view
			FrameLayout viewGroup = (FrameLayout) findViewById(R.id.fragment).getParent();
			enableDisableViewGroup(viewGroup, false);

			// enable empty view
			((LinearLayout) findViewById(R.id.empty)).setVisibility(View.VISIBLE);
			findViewById(R.id.empty).setEnabled(true);
			findViewById(R.id.empty).setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						slideMenuAnimationToggle();
						return true;
					}
				});
			menuLayout.setVisibility(View.VISIBLE);
		} else {
			// Close
			new CloseAnimation(mainLayout, menuWidth, TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f, deviceWidth, menuSpeed);

			// enable all of main view
			FrameLayout viewGroup = (FrameLayout) findViewById(R.id.fragment).getParent();
			enableDisableViewGroup(viewGroup, true);

			// disable empty view
			((LinearLayout) findViewById(R.id.empty)).setVisibility(View.GONE);
			findViewById(R.id.empty).setEnabled(false);
			
			// Hide menuLayout
			Handler waitHandler = new Handler();
			waitHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					menuLayout.setVisibility(View.GONE);
				}
			}, menuSpeed);
		}
		menuExpanded = !menuExpanded;
	}

	/**
	 * @param viewGroup
	 * @param enabled
	 */
	public static void enableDisableViewGroup(ViewGroup viewGroup,
			boolean enabled) {
		int childCount = viewGroup.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = viewGroup.getChildAt(i);
			if (view.getId() != R.id.menu) {
				view.setEnabled(enabled);
				if (view instanceof ViewGroup) {
					enableDisableViewGroup((ViewGroup) view, enabled);
				}
			}
		}
	}
}
