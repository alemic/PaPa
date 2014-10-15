package com.sanrenx.funny;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sanrenx.funny.adapter.JokeListAdapter;
import com.sanrenx.funny.db.JokeHelper;
import com.sanrenx.funny.entity.JokeEntity;
import com.sanrenx.funny.entity.TagEntity;
import com.sanrenx.funny.entity.UserEntity;
import com.sanrenx.funny.utils.Conf;
import com.sanrenx.funny.utils.HttpClientUtils;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TagJokeActivity extends Activity implements OnRefreshListener{

	private ImageView backBtn;
	private TextView titleTxt;
	private SwipeRefreshLayout mSwipeLayout;
	private ListView mListView;
	private JokeListAdapter mAdapter;
	private String tagID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		tagID=getIntent().getStringExtra("id");
		setUpViews();
		
	}
	
	public void setUpViews(){
		backBtn=(ImageView)findViewById(R.id.iv_back);
		backBtn.setOnClickListener(backClickListener);
		titleTxt=(TextView)findViewById(R.id.tv_title);
		titleTxt.setText(getIntent().getStringExtra("title")+"");
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.srl_container);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeLayout.setOnRefreshListener(this);
		mListView=(ListView)findViewById(R.id.lv_joke);
		JokeHelper dbHelper=new JokeHelper(getApplicationContext());
		List<JokeEntity> lists=dbHelper.getJokeListFromCache(tagID);
		dbHelper.close();
		mAdapter=new JokeListAdapter(getApplicationContext(), lists);
		mListView.setAdapter(mAdapter);  
	}
	
	OnClickListener backClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		}
	};
	@Override
	public void onRefresh() {
		if(mSwipeLayout.isRefreshing()){
			refreshLoad();
		}
	}
	
	public void refreshLoad(){
		mSwipeLayout.setRefreshing(true);
		RequestParams params = new RequestParams();
		params.put("tagId", tagID);
		HttpClientUtils.post(Conf.TAG_JOKE_URL, params,refreshLoadHandler);
	}
	public void autoLoad(){
		mSwipeLayout.setRefreshing(true);
		RequestParams params = new RequestParams();
		params.put("tagId", tagID);
		HttpClientUtils.post(Conf.TAG_JOKE_URL, params,autoLoadHandler);
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
			mAdapter.addMoreData(josnToEntityList(response));
		}
	};
	
	
	private List<JokeEntity> josnToEntityList(JSONObject response){
		List<JokeEntity> entityList = new ArrayList<JokeEntity>();
		try {
			JSONArray jsonArray = response.isNull("data") ? null : response.getJSONArray("data");
			if(jsonArray !=null){
				for (int i = 0; i < jsonArray.length(); i++) {
					JokeEntity Entity = new JokeEntity();
					JSONObject jObj = (JSONObject) jsonArray.opt(i);
					Entity.setId(jObj.isNull("id") ? null : jObj.getString("id"));
					Entity.setUid(jObj.isNull("user") ? null : jObj.getString("user"));
					Entity.setNickname(jObj.isNull("nickname") ? null : jObj.getString("nickname"));
					Entity.setAvatar(jObj.isNull("avatar") ? null : jObj.getString("avatar"));
					Entity.setTitle(jObj.isNull("title") ? null : jObj.getString("title"));
					Entity.setContent(jObj.isNull("content") ? null : jObj.getString("content"));
					Entity.setCover(jObj.isNull("cover") ? null : jObj.getString("cover"));
					Entity.setPath(jObj.isNull("path") ? null : jObj.getString("path"));									
					Entity.setTags(jObj.isNull("tag") ? null : jObj.getString("tag"));
					Entity.setType(jObj.isNull("type") ? 1 : jObj.getInt("type"));
					Entity.setChoice(jObj.isNull("choice") ? 0 : jObj.getInt("choice"));
					Entity.setTagLists(tagJosnToEntityList(jObj));
					Entity.setUserLists(userJosnToEntityList(jObj));
					entityList.add(Entity);
				}
				JokeHelper dbHelper=new JokeHelper(getApplicationContext());
				dbHelper.insertJokeListToCache(entityList);
				dbHelper.close();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entityList;
	}
	
	private List<TagEntity> tagJosnToEntityList(JSONObject response){
		List<TagEntity> entityList = new ArrayList<TagEntity>();
		try {
			JSONArray jsonArray = response.isNull("tagList") ? null : response.getJSONArray("tagList");
			if(jsonArray !=null){
				for (int i = 0; i < jsonArray.length(); i++) {
					TagEntity Entity = new TagEntity();
					JSONObject jObj = (JSONObject) jsonArray.opt(i);
					Entity.setId(jObj.isNull("id") ? null : jObj.getString("id"));
					Entity.setTitle(jObj.isNull("title") ? null : jObj.getString("title"));
					entityList.add(Entity);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entityList;
	}

	private List<UserEntity> userJosnToEntityList(JSONObject response){
		List<UserEntity> entityList = new ArrayList<UserEntity>();
		try {
			JSONArray jsonArray = response.isNull("rewardList") ? null : response.getJSONArray("rewardList");
			if(jsonArray !=null){
				for (int i = 0; i < jsonArray.length(); i++) {
					UserEntity Entity = new UserEntity();
					JSONObject jObj = (JSONObject) jsonArray.opt(i);
					Entity.setId(jObj.isNull("id") ? null : jObj.getString("id"));
					Entity.setNickname(jObj.isNull("nickName") ? null : jObj.getString("nickName"));
					Entity.setAvatar(jObj.isNull("avatar") ? null : jObj.getString("avatar"));
					entityList.add(Entity);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entityList;
	}
}
