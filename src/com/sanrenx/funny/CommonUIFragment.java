package com.sanrenx.funny;

import java.util.ArrayList;
import java.util.List;

import com.sanrenx.funny.adapter.JokeListAdapter;
import com.sanrenx.funny.entity.JokeEntity;
import com.sanrenx.funny.entity.TagEntity;
import com.sanrenx.funny.entity.UserEntity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CommonUIFragment extends Fragment implements OnRefreshListener{
	
	private SwipeRefreshLayout mSwipeLayout;
	private ListView mListView;
	private JokeListAdapter mAdapter;
	private List<JokeEntity> mLists;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_selection_common, container, false);
		mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_container);
		mListView=(ListView)rootView.findViewById(R.id.lv_joke);
		Bundle bundle = getArguments();
		String txt=bundle.getString(JokeActivity.ARGUMENTS_NAME, "");
		
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeLayout.setOnRefreshListener(this);
		mLists=new ArrayList<JokeEntity>();
		List<TagEntity> tagList1=new ArrayList<TagEntity>();
		List<TagEntity> tagList2=new ArrayList<TagEntity>();
		TagEntity tag1=new TagEntity("糗事");
		TagEntity tag2=new TagEntity("奇葩");
		TagEntity tag3=new TagEntity("搞笑");
		TagEntity tag4=new TagEntity("冷笑话");
		TagEntity tag5=new TagEntity("幽默");
		TagEntity tag6=new TagEntity("内涵");
		TagEntity tag7=new TagEntity("吐槽");
		TagEntity tag8=new TagEntity("恶搞");
		TagEntity tag9=new TagEntity("趣图");
		tagList1.add(tag1);tagList1.add(tag2);tagList1.add(tag4);tagList1.add(tag5);tagList1.add(tag8);
		tagList2.add(tag9);tagList2.add(tag7);tagList2.add(tag3);tagList2.add(tag6);
		List<UserEntity> userList1=new ArrayList<UserEntity>();
		List<UserEntity> userList2=new ArrayList<UserEntity>();
		UserEntity user1=new UserEntity("1","http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png");
		UserEntity user2=new UserEntity("2","http://t12.baidu.com/it/u=1669048607,2540123673&fm=56");
		UserEntity user3=new UserEntity("3","http://pic.qqtn.com/file/2013/2013-5/2013051515113135806.png");
		UserEntity user4=new UserEntity("4","http://www.touxiang.cn/uploads/20131115/15-033900_483.jpg");
		userList1.add(user2);userList1.add(user3);userList1.add(user4);userList1.add(user1);
		userList2.add(user3);userList2.add(user4);
		JokeEntity joke1=new JokeEntity("http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png", "小猪爱吃糠", "四只老鼠吹牛：甲：我每天都拿鼠药当糖吃；乙：我一天不踩老鼠夹脚发痒；丙：我每天不过几次大街不踏实；丁：时间不早了，回家抱猫去咯。", null, 300, tagList1, userList1);
		JokeEntity joke2=new JokeEntity("http://t11.baidu.com/it/u=3998289502,3028025941&fm=56", "多啦A不萌", "二哈童鞋，你敢不敢不这么亲密.", "http://fs0.139js.com/file/s_jpg_70a9843ejw1e0hq65gm33j.jpg", 300, tagList2, userList2);
		mLists.add(joke1);mLists.add(joke2);
		mAdapter=new JokeListAdapter(getActivity(), mLists);
		mListView.setAdapter(mAdapter);  
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	
}
