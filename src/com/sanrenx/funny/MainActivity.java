package com.sanrenx.funny;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.baidu.frontia.Frontia;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sanrenx.funny.adapter.TagGridAdapter;
import com.sanrenx.funny.db.TagHelper;
import com.sanrenx.funny.entity.TagEntity;
import com.sanrenx.funny.utils.AutoLoadListener;
import com.sanrenx.funny.utils.Conf;
import com.sanrenx.funny.utils.AutoLoadListener.AutoLoadCallBack;
import com.sanrenx.funny.utils.HttpClientUtils;
import com.sanrenx.funny.widget.RoundButton;
import com.sanrenx.funny.widget.SlideHolder;
import com.sanrenx.funny.widget.SlideHolder.OnSlideListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;


public class MainActivity extends Activity implements OnSlideListener,
		OnClickListener, OnRefreshListener{

	private SlideHolder mSlideHolder;
	private RoundButton btnJoke, btnProfile, btnDiscovery, btnSettings;
	private ImageView btnMenu, btnBrush;
	private SwipeRefreshLayout mSwipeLayout;
	private GridView mGridView;
	private TagGridAdapter mAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Frontia.init(getApplicationContext(), Conf.APIKEY);
		Frontia.getPush().start();
		setUpViews();
		setUpEvents();
	}

	protected void setUpViews() {
		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);
		btnJoke = (RoundButton) findViewById(R.id.rb_joke);
		btnDiscovery = (RoundButton) findViewById(R.id.rb_discovery);
		btnProfile = (RoundButton) findViewById(R.id.rb_profile);
		btnSettings = (RoundButton) findViewById(R.id.rb_settings);
		btnMenu = (ImageView) findViewById(R.id.iv_menu);
		btnBrush = (ImageView) findViewById(R.id.iv_brush);
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.srl_container);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mGridView = (GridView) findViewById(R.id.gv_tag);
		TagHelper dbHelper=new TagHelper(getApplicationContext());
		List<TagEntity> lists=dbHelper.getTagListFromCache();
		dbHelper.close();
		mAdapter=new TagGridAdapter(getApplicationContext(), lists);
		mGridView.setAdapter(mAdapter);
	}

	public void setUpEvents() {
		btnJoke.setOnClickListener(this);
		btnProfile.setOnClickListener(this);
		btnDiscovery.setOnClickListener(this);
		btnSettings.setOnClickListener(this);
		btnMenu.setOnClickListener(this);
		btnBrush.setOnClickListener(this);
		mSlideHolder.setOnSlideListener(this);
		mSwipeLayout.setOnRefreshListener(this);
		AutoLoadListener autoLoadListener = new AutoLoadListener(callBack);
		mGridView.setOnScrollListener(autoLoadListener);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this,TagJokeActivity.class);
				intent.putExtra("id", mAdapter.getData().get(position).getId());
				intent.putExtra("title", mAdapter.getData().get(position).getTitle());
				startActivity(intent);
			}
		});
	}

	@Override
	public void onRefresh() {
		if(mSwipeLayout.isRefreshing()){
			refreshLoad();
		}
	}

	@Override
	public void onSlideCompleted(boolean opened) {

	}
	
	 /**
     * 回调函数
     */
    AutoLoadCallBack callBack = new AutoLoadCallBack() {

        public void execute() {
        	if(!mSwipeLayout.isRefreshing()){
        		autoLoad();
        	}
        }

    };

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.rb_joke:
			startActivity(new Intent(MainActivity.this, JokeActivity.class));
			mSlideHolder.toggle();
			break;
		case R.id.rb_discovery:
			break;
		case R.id.rb_profile:
			break;
		case R.id.rb_settings:
			startActivity(new Intent(MainActivity.this, SettingActivity.class));
			mSlideHolder.toggle();
			break;
		case R.id.iv_menu:
			mSlideHolder.toggle();
			break;
		case R.id.iv_brush:

			break;

		default:
			break;
		}

	}
	
	public void refreshLoad(){
		mSwipeLayout.setRefreshing(true);
		HttpClientUtils.post(Conf.TAG_URL, null,refreshLoadHandler);
	}
	public void autoLoad(){
		mSwipeLayout.setRefreshing(true);
		RequestParams params = new RequestParams();
		params.put("key", "value");
		params.put("more", "data");
		HttpClientUtils.post(Conf.TAG_URL, params,autoLoadHandler);
	}
	
	
	
	
	private JsonHttpResponseHandler refreshLoadHandler=new JsonHttpResponseHandler(){
		@Override
		public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
			
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
			mSwipeLayout.setRefreshing(false);
			mAdapter.addRefreshData(josnToEntityList(response));
		}
	};
	
	
	private JsonHttpResponseHandler  autoLoadHandler=new JsonHttpResponseHandler(){
		@Override
		public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
			
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
			mSwipeLayout.setRefreshing(false);
			mAdapter = new TagGridAdapter(getApplicationContext(), josnToEntityList(response));
		}
	};
	
	
	private List<TagEntity> josnToEntityList(JSONObject response){
		List<TagEntity> entityList = new ArrayList<TagEntity>();
		try {
			JSONArray jsonArray = response.isNull("data") ? null : response.getJSONArray("data");
			if(jsonArray !=null){
				for (int i = 0; i < jsonArray.length(); i++) {
					TagEntity Entity = new TagEntity();
					JSONObject jObj = (JSONObject) jsonArray.opt(i);
					Entity.setId(jObj.isNull("id") ? null : jObj.getString("id"));
					Entity.setTitle(jObj.isNull("title") ? null : jObj.getString("title"));
					Entity.setDisp(jObj.isNull("disp") ? null : jObj.getString("disp"));
					Entity.setPath(jObj.isNull("path") ? null : jObj.getString("path"));									
					Entity.setHot(jObj.isNull("hot") ? 0 : jObj.getInt("hot"));
					Entity.setSort(jObj.isNull("sort") ? null : jObj.getString("sort"));
					entityList.add(Entity);
				}
				TagHelper dbHelper=new TagHelper(getApplicationContext());
				dbHelper.insertTagListToCache(entityList);
				dbHelper.close();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entityList;
	}
	

}
