package com.sanrenx.funny;

import java.util.ArrayList;
import java.util.List;
import com.sanrenx.funny.adapter.PeriodListAdapter;
import com.sanrenx.funny.entity.PeriodEntity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LaunchUIFragment extends Fragment implements OnRefreshListener{

	private SwipeRefreshLayout mSwipeLayout;
	private ListView mListView;
	private PeriodListAdapter mAdapter;
	private List<PeriodEntity> mLists;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_selection_launch,container, false);
		mSwipeLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.srl_container);
		mListView=(ListView)rootView.findViewById(R.id.lv_periodical);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeLayout.setOnRefreshListener(this);
		mLists=new ArrayList<PeriodEntity>();
		PeriodEntity p1=new PeriodEntity("1", "葩葩期刊第0618期", "放开矜持，开心一笑，请原谅我一生放荡不羁笑点低。", "http://99touxiang.com/public/upload/nvsheng/112/31-035220_512.jpg");
		PeriodEntity p2=new PeriodEntity("2", "葩葩期刊第0615期", "一入葩葩深似海，从此节操是路人。天王盖地虎，小鸡炖蘑菇。", "http://img1.touxiang.cn/uploads/20120509/09-070344_491.jpg");
		PeriodEntity p3=new PeriodEntity("3", "葩葩期刊第0610期", "屌丝变色狼，色狼变流氓。宅男寻腐女，腐女已有郎。想富卖切糕，木耳爱香......", null);
		mLists.add(p1);mLists.add(p2);mLists.add(p3);
		mAdapter=new PeriodListAdapter(getActivity(), mLists);
		mListView.setAdapter(mAdapter);  
	}

	@Override
	public void onRefresh() {
		
		
	}
	

}
