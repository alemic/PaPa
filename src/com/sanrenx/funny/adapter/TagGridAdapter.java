package com.sanrenx.funny.adapter;

import java.util.List;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.sanrenx.funny.R;
import com.sanrenx.funny.entity.TagEntity;
import com.sanrenx.funny.widget.SquareProgressView;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class TagGridAdapter extends BaseAdapter {

	private List<TagEntity> mLists;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public TagGridAdapter(Context _context, List<TagEntity> _lists) {

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		mInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLists = _lists;
	}

	@Override
	public int getCount() {
		int size = mLists != null ? mLists.size() : 0;
		return size;
	}
	
	public List<TagEntity> getData() {
		return mLists;
	}
	
	public void  addRefreshData(List<TagEntity> list){
		mLists.clear();
		mLists.addAll(list);
		notifyDataSetChanged();
	}
	
	public void addMoreData(List<TagEntity> list) {
		mLists.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public TagEntity getItem(int position) {
		TagEntity item = mLists != null ? mLists.get(position) : null;
		return item;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TagEntity tagEntity = getItem(position);
		final ViewHolder viewHolder;
		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_tag, null);
			viewHolder.ImageTag = (ImageView) convertView.findViewById(R.id.imgTag);
			viewHolder.Progress = (SquareProgressView) convertView.findViewById(R.id.progress);
			viewHolder.TxtTag = (TextView) convertView.findViewById(R.id.txtTag);
			convertView.setTag(viewHolder);
		}
		LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,(parent.getWidth()-70)/3);
		viewHolder.ImageTag.setLayoutParams(param);
		//String path = AppConfig.IMAGE_FILE + tagEntity.getPath();
		String path =tagEntity.getPath();
		viewHolder.Progress.setClearOnHundred(true);
		imageLoader.displayImage(path, viewHolder.ImageTag, options, null,
				new ImageLoadingProgressListener() {

					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {
						viewHolder.Progress.setProgress(Math.round(100.0f* current / total));
					}
				});
		viewHolder.TxtTag.setText(tagEntity.getTitle());
		return convertView;
	}

	public class ViewHolder {
		SquareProgressView Progress;
		ImageView ImageTag;
		TextView TxtTag;
	}

}
