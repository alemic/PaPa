package com.sanrenx.funny.adapter;

import java.util.List;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.sanrenx.funny.R;
import com.sanrenx.funny.entity.UserEntity;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class RewardListAdapter extends BaseAdapter {

	private List<UserEntity> mLists;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public RewardListAdapter(Context _context, List<UserEntity> _lists) {

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new RoundedBitmapDisplayer(50))
				.build();
		mInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLists = _lists;
	}

	@Override
	public int getCount() {
		int size = mLists != null ? mLists.size() : 0;
		return size;
	}
	
	public List<UserEntity> getData() {
		return mLists;
	}
	
	public void addData(UserEntity entity) {
		for (int i = 0, len = mLists.size(); i < len; i++) {
			if (mLists.get(i).getId() == entity.getId()) {
				mLists.remove(i);
				notifyDataSetChanged();
				break;
			}
		}
		mLists.add(0, entity);
		notifyDataSetChanged();
	}

	@Override
	public UserEntity getItem(int position) {
		UserEntity item = mLists != null ? mLists.get(position) : null;
		return item;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UserEntity userEntity = getItem(position);
		final ViewHolder viewHolder;
		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_avatar, null);
			viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
			convertView.setTag(viewHolder);
		}
		//String avatar = AppConfig.IMAGE_FILE + tagEntity.getPath();
		String avatar =userEntity.getAvatar();
		imageLoader.displayImage(avatar, viewHolder.avatar, options);
		return convertView;
	}

	public class ViewHolder {
		ImageView avatar;
	}

}
