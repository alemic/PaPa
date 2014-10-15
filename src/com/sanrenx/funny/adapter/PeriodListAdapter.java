package com.sanrenx.funny.adapter;

import java.util.List;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.sanrenx.funny.R;
import com.sanrenx.funny.entity.PeriodEntity;
import com.sanrenx.funny.utils.TextUtils;
import com.sanrenx.funny.widget.SquareProgressView;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class PeriodListAdapter extends BaseAdapter {
	private List<PeriodEntity> mLists;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	public PeriodListAdapter(Context _context, List<PeriodEntity> _lists) {
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.default_photo)
		.showImageForEmptyUri(R.drawable.default_photo)
		.showImageOnFail(R.drawable.default_photo)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new RoundedBitmapDisplayer(5))
		.build();
		mInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLists = _lists;
	}

	@Override
	public int getCount() {
		int size = mLists != null ? mLists.size() : 0;
		return size;
	}
	
	public List<PeriodEntity> getData() {
		return mLists;
	}

	@Override
	public PeriodEntity getItem(int position) {
		PeriodEntity item = mLists != null ? mLists.get(position) : null;
		return item;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PeriodEntity entity = getItem(position);
		final ViewHolder viewHolder;
		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_period, null);
			viewHolder.coverLayout = (FrameLayout) convertView.findViewById(R.id.fl_cover);
			viewHolder.cover = (ImageView) convertView.findViewById(R.id.iv_cover);
			viewHolder.progress = (SquareProgressView) convertView.findViewById(R.id.spv_progress);
			viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.disp = (TextView) convertView.findViewById(R.id.tv_disp);
			convertView.setTag(viewHolder);
		}
	
		//String path = AppConfig.IMAGE_FILE + jokeEntity.getPath();
		String fontCover =entity.getCover();
		if(TextUtils.isEmpty(fontCover)){
			viewHolder.coverLayout.setVisibility(View.GONE);
		}else{
			viewHolder.coverLayout.setVisibility(View.VISIBLE);
			viewHolder.progress.setClearOnHundred(true);
			imageLoader.displayImage(fontCover, viewHolder.cover, options, null,
					new ImageLoadingProgressListener() {

						@Override
						public void onProgressUpdate(String imageUri, View view,int current, int total) {
							viewHolder.progress.setProgress(Math.round(100.0f* current / total));
						}
					});
		}
		viewHolder.title.setText(entity.getTitle());
		viewHolder.disp.setText(entity.getDisp());
		return convertView;
	}

	public class ViewHolder {
		SquareProgressView progress;
		TextView title;
		TextView disp;
		FrameLayout coverLayout;
		ImageView cover;

	}
}
