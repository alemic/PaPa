package com.sanrenx.funny.adapter;

import java.util.List;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.sanrenx.funny.R;
import com.sanrenx.funny.TagJokeActivity;
import com.sanrenx.funny.entity.JokeEntity;
import com.sanrenx.funny.entity.TagEntity;
import com.sanrenx.funny.entity.UserEntity;
import com.sanrenx.funny.utils.TextUtils;
import com.sanrenx.funny.widget.HorizontalListView;
import com.sanrenx.funny.widget.SquareProgressView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JokeListAdapter extends BaseAdapter {
	private Context mContext;
	private List<JokeEntity> mLists;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions option;
	private DisplayImageOptions options;
	public JokeListAdapter(Context _context, List<JokeEntity> _lists) {
		imageLoader = ImageLoader.getInstance();
		option = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisc(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new RoundedBitmapDisplayer(50))
				.build();
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.default_photo)
		.showImageForEmptyUri(R.drawable.default_photo)
		.showImageOnFail(R.drawable.default_photo)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		mInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLists = _lists;
		mContext=_context;
	}

	@Override
	public int getCount() {
		int size = mLists != null ? mLists.size() : 0;
		return size;
	}
	
	public List<JokeEntity> getData() {
		return mLists;
	}
	
	public void  addRefreshData(List<JokeEntity> list){
		mLists.clear();
		mLists.addAll(list);
		notifyDataSetChanged();
	}
	
	public void addMoreData(List<JokeEntity> list) {
		mLists.addAll(list);
		notifyDataSetChanged();
	}
	
	@Override
	public JokeEntity getItem(int position) {
		JokeEntity item = mLists != null ? mLists.get(position) : null;
		return item;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		JokeEntity jokeEntity = getItem(position);
		final ViewHolder viewHolder;
		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_joke, null);
			viewHolder.avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
			viewHolder.nikcname = (TextView) convertView.findViewById(R.id.tv_nickname);
			viewHolder.coins = (TextView) convertView.findViewById(R.id.tv_coins);
			viewHolder.contentLayout = (LinearLayout) convertView.findViewById(R.id.ll_content);
			viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content);
			viewHolder.coverLayout = (FrameLayout) convertView.findViewById(R.id.fl_cover);
			viewHolder.progress = (SquareProgressView) convertView.findViewById(R.id.spv_progress);
			viewHolder.cover = (ImageView) convertView.findViewById(R.id.iv_cover);
			viewHolder.reward = (ImageView) convertView.findViewById(R.id.iv_reward);
			viewHolder.tagList = (HorizontalListView) convertView.findViewById(R.id.hlv_tag);
			viewHolder.rewardList = (HorizontalListView) convertView.findViewById(R.id.hlv_reward);
			convertView.setTag(viewHolder);
		}
		//String avatar = AppConfig.IMAGE_FILE + jokeEntity.getAvatar();
		String avatar =jokeEntity.getAvatar();
		imageLoader.displayImage(avatar, viewHolder.avatar,option);
		viewHolder.nikcname.setText(jokeEntity.getNickname());
		String content=jokeEntity.getContent();
		if(TextUtils.isEmpty(content)){
			viewHolder.contentLayout.setVisibility(View.GONE);
		}else{
			viewHolder.contentLayout.setVisibility(View.VISIBLE);
			viewHolder.content.setText(content);
		}
		//String path = AppConfig.IMAGE_FILE + jokeEntity.getPath();
		String path =jokeEntity.getPath();
		if(TextUtils.isEmpty(path)){
			viewHolder.coverLayout.setVisibility(View.GONE);
		}else{
			viewHolder.coverLayout.setVisibility(View.VISIBLE);
			viewHolder.progress.setClearOnHundred(true);
			imageLoader.displayImage(path, viewHolder.cover, options, null,
					new ImageLoadingProgressListener() {

						@Override
						public void onProgressUpdate(String imageUri, View view,
								int current, int total) {
							viewHolder.progress.setProgress(Math.round(100.0f* current / total));
						}
					});
		}
		List<TagEntity> tagLists=jokeEntity.getTagLists();
		final TagListAdapter tagAdapter=new TagListAdapter(mContext, tagLists);
		viewHolder.tagList.setAdapter(tagAdapter);
		viewHolder.tagList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Intent intent = new Intent(mContext,TagJokeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("id", tagAdapter.getData().get(position).getId());
				intent.putExtra("title", tagAdapter.getData().get(position).getTitle());
				mContext.startActivity(intent);
			}
		});
		List<UserEntity> userLists=jokeEntity.getUserLists();
		final RewardListAdapter userAdapter=new RewardListAdapter(mContext, userLists);
		viewHolder.rewardList.setAdapter(userAdapter);
		viewHolder.rewardList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				
			}
		});
		
		
		viewHolder.reward.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserEntity entity=new UserEntity("1","http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png");
				userAdapter.addData(entity);
			}
		});
		return convertView;
	}

	public class ViewHolder {
		SquareProgressView progress;
		ImageView avatar;
		TextView nikcname;
		TextView coins;
		LinearLayout contentLayout;
		TextView content;
		FrameLayout coverLayout;
		ImageView cover;
		ImageView reward;
		HorizontalListView tagList;
		HorizontalListView rewardList;
	}
}
