package cuiweiyou.headerrecycleview;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cuiweiyou.headerrecycleview.adapter.HeaderRcyvAdapter;
import cuiweiyou.headerrecycleview.bean.HeaderBean;
import cuiweiyou.headerrecycleview.bean.NormalBean;

/**
 * www.gaohaiyan.com
 */
public class MainActivity extends AppCompatActivity {
	private List<NormalBean> mNormalList = new ArrayList<NormalBean>(); // 普通item的数据集
	private HeaderBean mHeaderBean;                                     // header的数据

	private SwipeRefreshLayout mSwipeRefresh;
	private RecyclerView mRecyclerView;

	private HeaderRcyvAdapter mHeaderRcyvAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 1.准备数据
		initData();

		// 2.定义适配器
		mHeaderRcyvAdapter = new HeaderRcyvAdapter(this, mNormalList);

		// 3.定义列表控件并指定适配器
		mRecyclerView = (RecyclerView)findViewById(R.id.mRecyclerView);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(mHeaderRcyvAdapter);

		// 4.指定header TODO 如果不需要header，则关闭这一句，及下拉刷新的同一语句
		mHeaderRcyvAdapter.setHeaderView(mRecyclerView, mHeaderBean);

		mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefresh);
		mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				initData();

				mSwipeRefresh.setRefreshing(false);
				mHeaderRcyvAdapter.notifyDataSetChanged(); // 刷新普通item

				// 5.数据改变后，刷新header TODO 如果不需要header，则关闭这一句
				mHeaderRcyvAdapter.setHeaderView(mRecyclerView, mHeaderBean);
			}
		});
	}

	/** 初始化数据 */
	private void initData(){
		int st = mNormalList.size();
		int to = st + 10;
		for (int i = st; i < to; i++){
			mNormalList.add(new NormalBean("item" + i, i + ""));
		}

		mHeaderBean = new HeaderBean("header " + System.currentTimeMillis(), "img" + to);
	}
}
