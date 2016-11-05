package com.boredream.designrescollection.ui.home;

import com.boredream.bdcodehelper.net.ObservableDecorator;
import com.boredream.bdcodehelper.utils.ErrorInfoUtils;
import com.boredream.designrescollection.entity.GetBannerListResponse;
import com.boredream.designrescollection.entity.SearchInvestListResponse;
import com.boredream.designrescollection.net.HttpRequest;

import rx.Observable;
import rx.Subscriber;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View view;
    public SearchInvestListResponse datas;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void pullToLoadList() {
        loadData(1);
    }

    @Override
    public void loadBanner() {
        Observable<GetBannerListResponse> observable = HttpRequest.getInstance().getBanner();
        ObservableDecorator.decorate(observable).subscribe(
                new Subscriber<GetBannerListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!view.isActive()) {
                            return;
                        }
                        view.dismissProgress();

                        String error = ErrorInfoUtils.parseHttpErrorInfo(e);
                        view.showTip(error);
                    }

                    @Override
                    public void onNext(GetBannerListResponse response) {
                        if (!view.isActive()) {
                            return;
                        }
                        view.dismissProgress();

                        view.loadBannerSuccess(response);
                    }
                });
    }

    @Override
    public void loadList(final int page) {
        if(page == 1) {
            view.showProgress();
        }

        loadData(page);
    }

    private void loadData(final int page) {
        Observable<SearchInvestListResponse> observable = HttpRequest.getInstance().getDesignRes(page);
        ObservableDecorator.decorate(observable).subscribe(
                new Subscriber<SearchInvestListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!view.isActive()) {
                            return;
                        }
                        view.dismissProgress();

                        String error = ErrorInfoUtils.parseHttpErrorInfo(e);
                        view.showTip(error);
                    }

                    @Override
                    public void onNext(SearchInvestListResponse response) {
                        if (!view.isActive()) {
                            return;
                        }
                        view.dismissProgress();

                        datas = response;
                        view.loadListSuccess(page, datas);
                    }
                });
    }

}
