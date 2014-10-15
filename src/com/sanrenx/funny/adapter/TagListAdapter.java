package com.sanrenx.funny.adapter;

import java.util.List;
import com.sanrenx.funny.R;
import com.sanrenx.funny.entity.TagEntity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TagListAdapter extends BaseAdapter {

	private List<TagEntity> mLists;
	private LayoutInflater mInflater;

	public TagListAdapter(Context _context, List<TagEntity> _lists) {
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
			convertView = mInflater.inflate(R.layout.item_string, null);
			viewHolder.tag = (TextView) convertView.findViewById(R.id.tag);
			convertView.setTag(viewHolder);
		}
		viewHolder.tag.setText(tagEntity.getTitle());
		return convertView;
	}

	public class ViewHolder {
		TextView tag;
	}

}
