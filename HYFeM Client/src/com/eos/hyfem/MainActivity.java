package com.eos.hyfem;

import java.util.ArrayList;
import java.util.Collections;

import com.eos.hyfem.slidingmenu.SlidingMenuActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends SlidingMenuActivity {

	ViewPager viewPager;
	private static Context sContext;
	public Thread ads;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sContext = getApplicationContext();

		this.initSildeMenu();
		this.testMenu();
		
		startAD();
	}
	
	public static Context getContext() {
		return sContext;
	}

	private void startAD() {
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		ImagePagerAdapter adapter = new ImagePagerAdapter();
		viewPager.setAdapter(adapter);
		ads = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(Integer.parseInt(getString(R.string.ads_animation_speed)));

						runOnUiThread(new Runnable() {
							public void run() {
								if (viewPager.getChildCount() > viewPager.getCurrentItem())
									viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
								else
									viewPager.setCurrentItem(0, true);
							}
						});
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		ads.start();
	}
}

class ImagePagerAdapter extends PagerAdapter {
	private int[] ads = new int[] { 0, 0, 0 };
	private ArrayList<Integer> randomADs = new ArrayList<Integer>();

	public ImagePagerAdapter() {
		randomADs.add(R.drawable.ad1);
		randomADs.add(R.drawable.ad2);
		randomADs.add(R.drawable.ad3);
		Collections.shuffle(randomADs);

		for (int i = 0; i < ads.length; i++)
			ads[i] = randomADs.get(i);
	}

	@Override
	public int getCount() {
		return ads.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((ImageView) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Context context = MainActivity.getContext();
		ImageView imageView = new ImageView(context);
		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		imageView.setImageResource(ads[position]);
		((ViewPager) container).addView(imageView, 0);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((ImageView) object);
	}
}