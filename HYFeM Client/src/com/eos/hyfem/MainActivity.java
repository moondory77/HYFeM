package com.eos.hyfem;

import com.eos.hyfem.slidingmenu.CustomHorizontalScrollView;
import com.eos.hyfem.slidingmenu.CustomHorizontalScrollView.SizeCallback;
import com.eos.hyfem.slidingmenu.ViewUtils;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {
	int btnWidth;
	boolean menuOut = false;
	CustomHorizontalScrollView scrollView;
	View menu, app;
	ImageView menuBtn;
	Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (CustomHorizontalScrollView) inflater.inflate(
				R.layout.activity_main, null);
		setContentView(scrollView);

		menu = inflater.inflate(R.layout.menu, null);
		app = inflater.inflate(R.layout.main, null);
		ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);

		ListView listView = (ListView) menu.findViewById(R.id.list);
		ViewUtils.initListView(this, listView, "Menu ", 30,
				android.R.layout.simple_list_item_1);

		menuBtn = (ImageView) tabBar.findViewById(R.id.menu);
		menuBtn.setOnClickListener(this);

		final View[] children = new View[] { menu, app };

		int scrollToViewIdx = 1;
		scrollView.initViews(children, scrollToViewIdx,
				new SizeCallbackForMenu(menuBtn));

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int menuWidth = menu.getMeasuredWidth();
		menu.setVisibility(View.VISIBLE);
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (menuOut == true) {
				int left = menuWidth;
				scrollView.smoothScrollTo(left, 0);
				menuOut = false;
			} else {
				// 종료
				moveTaskToBack(true);
				finish();
				System.exit(0);
				android.os.Process.killProcess(android.os.Process.myPid());
				ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
				am.restartPackage(getPackageName());
			}
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.menu) {
			int menuWidth = menu.getMeasuredWidth();
			menu.setVisibility(View.VISIBLE);

			if (!menuOut) {
				int left = 0;
				scrollView.smoothScrollTo(left, 0);
			} else {
				int left = menuWidth;
				scrollView.smoothScrollTo(left, 0);
			}
			menuOut = !menuOut;
		}
	}

	static class SizeCallbackForMenu implements SizeCallback {
		int btnWidth;
		View btnSlide;

		public SizeCallbackForMenu(View btnSlide) {
			super();
			this.btnSlide = btnSlide;
		}

		@Override
		public void onGlobalLayout() {
			btnWidth = btnSlide.getMeasuredWidth();
		}

		@Override
		public void getViewSize(int idx, int w, int h, int[] dims) {
			dims[0] = w;
			dims[1] = h;
			final int menuIdx = 0;
			if (idx == menuIdx) {
				dims[0] = w - (btnWidth + 50); // 여유공간을 50만큼 더 주었습니다.
			}
		}
	}
}