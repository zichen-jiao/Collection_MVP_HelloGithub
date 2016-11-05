package com.boredream.designrescollection.ui.home;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boredream.bdcodehelper.adapter.LoadMoreAdapter;
import com.boredream.bdcodehelper.present.ImageBannerPresent;
import com.boredream.bdcodehelper.utils.TitleBuilder;
import com.boredream.designrescollection.R;
import com.boredream.designrescollection.adapter.DesignResAdapter;
import com.boredream.designrescollection.base.BaseFragment;
import com.boredream.designrescollection.constants.CommonConstants;
import com.boredream.designrescollection.entity.GetBannerListResponse;
import com.boredream.designrescollection.entity.SearchInvestListResponse;
import com.boredream.designrescollection.entity.SearchInvestListResponse.ElementInvestList;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private View view;
    private SwipeRefreshLayout srl;
    private RecyclerView rv;
    private ImageBannerPresent imageBannerPresent;
    private HomeContract.Presenter presenter;
    private int curPage = 1;
    private ArrayList<ElementInvestList> datas = new ArrayList<>();
    private LoadMoreAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.frag_home, null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        presenter = new HomePresenter(this);
        imageBannerPresent = new ImageBannerPresent(getContext(),view);
        new TitleBuilder(view).setTitleText(getString(R.string.tab1));
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.pullToLoadList();
                presenter.loadBanner();
            }
        });

        rv = (RecyclerView) view.findViewById(R.id.rv);
//        rv.addItemDecoration(new GridSpacingDecorator(DisplayUtils.dp2px(activity, 2)));
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(activity);
        rv.setLayoutManager(gridLayoutManager);

        adapter = new LoadMoreAdapter(rv,
                new DesignResAdapter(activity, datas),
                new LoadMoreAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        presenter.loadList(curPage + 1);
                    }
                });
        rv.setAdapter(adapter);
    }

    private void initData() {
        presenter.loadList(1);
        presenter.loadBanner();
    }

    @Override
    public void loadListSuccess(int page, SearchInvestListResponse datas) {
        curPage = page;
        if (curPage == 1) {
            this.datas.clear();
        }
        this.datas.addAll(datas.getInvestList());

        // 设置是否已加载完全部数据状态
        adapter.setStatus(datas.getInvestList().size() == CommonConstants.COUNT_OF_PAGE
                ? LoadMoreAdapter.STATUS_HAVE_MORE : LoadMoreAdapter.STATUS_LOADED_ALL);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showProgress() {
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(true);
            }
        });
    }

    @Override
    public void dismissProgress() {
        srl.setRefreshing(false);
    }

    @Override
    public void showTip(String message) {
        showToast(message);
    }

    @Override
    public void loadBannerSuccess(GetBannerListResponse datas) {
        imageBannerPresent.load(datas.getBannerList());
        imageBannerPresent.disableClick();
    }
}
