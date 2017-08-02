2017-03-02 16:10 ������ h t t p : / / git.oschina.net/vigiles/HeaderRecycleView <br/>

 ![github](gif.gif "www.gaohaiyan.com")

�������header��Ҳ���Բ���ӡ�
����ˣ���������ˢ��header��

## ������Adapter
``` xml

  www.gaohaiyan.com <br/>
  ���˵���������header��recycleview��������<br/>
  ��������ˢ��header��<br/>
  �鿴TODO���޸ı�������ݣ�������<br/>
  <li>1.���캯��ָ�� VG��Ctx����ͨitem�����ݼ���
  <li>2.��ʼ��header����
  <li>3.ָ����ͨitem�Ĳ��֣�����ʼ����Ӧ�� VIEW HOLDER
  <li>4.����ͨitem�Ĳ��ֵ�HOLDER
  <li>5.������ͨitem������
  <li>6.setHeaderView���������header��װ�����ݻ�header����
 
```
������룺
``` java
public class HeaderRcyvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	public static final int TYPE_HEADER = 0;  //˵���Ǵ���Header��
	public static final int TYPE_NORMAL = 1;  //˵���ǲ�����header��

	private List<NormalBean> mDatas;
	private View headerView;

	/**
	 * TODO 1.����RecycleView�������ġ���ͨitem�����ݼ�
	 * @param list
	 */
	public HeaderRcyvAdapter(Context ctx, List<NormalBean> list) {
		this.mDatas = list;
	}

	/**
	 * ÿ��ˢ�����ݶ�����<br/>
	 * TODO 6.setHeaderView���������header��װ�����ݻ�header����
	 * @param bean header���ݷ�װ��
	 * */
	public void setHeaderView(ViewGroup parent, HeaderBean bean){
		// TODO 2.��ʼ��header�Ĳ���
		if(null == headerView)
			headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ghy_hrcyv_header, parent, false);

		((TextView)headerView.findViewById(R.id.title)).setText(bean.title);
		((TextView)headerView.findViewById(R.id.note)).setText(bean.note);

		notifyItemInserted(0);
	}

	/**
	 * ��д�������������Ҫ���Ǽ���Header ����ͨ���ж�item�����ͣ��Ӷ��󶨲�ͬ��view
	 */
	@Override
	public int getItemViewType(int position) {
		if (headerView == null) {
			return TYPE_NORMAL;
		}

		if (position == 0) { // ��Ȼִ�е������ô������header�ġ�������һ��item����Header
			return TYPE_HEADER;
		}

		return TYPE_NORMAL;
	}

	//����View�������HeaderViewֱ����Holder�з���
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (headerView != null && viewType == TYPE_HEADER) {
			return new ViewNormalHolder(headerView);
		}

		// TODO 3.ָ����ͨitem�Ĳ��֣�����ʼ����Ӧ�� VIEW HOLDER
		View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.ghy_hrcyv_item, parent, false);
		return new ViewNormalHolder(layout);
	}

	// ���ݷ��ص����position�����ͽ��а󶨣�HeaderView���ð�
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (getItemViewType(position) == TYPE_NORMAL) {
			if (holder instanceof ViewNormalHolder) {
				int index;
				if(null == headerView){
					index = position;
				} else {
					index = position - 1;
				}

				// TODO 5.������ͨitem������
				((ViewNormalHolder) holder).tv.setText(mDatas.get(index).text);
				((ViewNormalHolder) holder).fg.setText(mDatas.get(index).flag);

				return;
			}
			return;
		} else if (getItemViewType(position) == TYPE_HEADER) {
			return;
		} else {
			return;
		}
	}

	// �б���item����Ӧ����ListView��Item�ĸ�������HeaderView
	@Override
	public int getItemCount() {
		if (headerView == null ) {
			return mDatas.size();
		} else if (headerView != null ) {
			return mDatas.size() + 1;
		} else {
			return mDatas.size();
		}
	}

	/** ��ͨitem�Ŀ�����
	 * */
	class ViewNormalHolder extends RecyclerView.ViewHolder {

		public TextView tv;
		public TextView fg;

		public ViewNormalHolder(View itemView) {
			super(itemView);

			if (itemView == headerView) { //�����headerview
				return;
			}

			// TODO 4.����ͨitem�Ĳ��ֵ�HOLDER
			tv = (TextView) itemView.findViewById(R.id.item);
			fg = (TextView) itemView.findViewById(R.id.flag);
		}
	}
}
```

## ʹ��
``` java
		// 1.׼������
		initData();

		// 2.����������
		mHeaderRcyvAdapter = new HeaderRcyvAdapter(this, mNormalList);

		// 3.�����б�ؼ���ָ��������
		mRecyclerView = (RecyclerView)findViewById(R.id.mRecyclerView);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(mHeaderRcyvAdapter);

		// 4.ָ��header TODO �������Ҫheader����ر���һ�䣬������ˢ�µ�ͬһ���
		mHeaderRcyvAdapter.setHeaderView(mRecyclerView, mHeaderBean);

		mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefresh);
		mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				initData();

				mSwipeRefresh.setRefreshing(false);
				mHeaderRcyvAdapter.notifyDataSetChanged(); // ˢ����ͨitem

				// 5.���ݸı��ˢ��header TODO �������Ҫheader����ر���һ��
				mHeaderRcyvAdapter.setHeaderView(mRecyclerView, mHeaderBean);
			}
		});
```
<hr/>

[http://www.gaohaiyan.com](http://www.gaohaiyan.com) <br/>
