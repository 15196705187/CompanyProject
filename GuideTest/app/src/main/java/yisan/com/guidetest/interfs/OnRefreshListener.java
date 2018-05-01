package yisan.com.guidetest.interfs;

/**
 * Created by asus on 2018/4/24.
 */

public interface OnRefreshListener {
    /**
     * 下拉刷新
     */
    void onDownPullRefresh();

    /**
     * 上拉加载更多
     */
    void onLoadingMore();
}
