package com.boredream.designrescollection.ui.home;

import com.boredream.designrescollection.base.BasePresenter;
import com.boredream.designrescollection.base.BaseView;
import com.boredream.designrescollection.entity.GetBannerListResponse;
import com.boredream.designrescollection.entity.SearchInvestListResponse;

/**
 * Created by 自辰 on 2016/11/5.
 * email：zichen615@yeah.net
 * Contract协议类
 * 这个Contract协议类不是MVP中的任何一个模块，是把所有View和Presenter
 * 的方法都提取成了接口放在这里，作为一个总的规则、协议，方便统一管理。比如下面的代码，就是项目中Home页面的Contract协议类，提供了View
 * 和Presenter的接口。其中BaseView和BasePresenter是提供了一些基础方法，比如显示进度，，showProgress
 * 等，自己可以按需添加。，
 */
public interface HomeContract {
    interface View extends BaseView<Presenter> {

        void loadListSuccess(int page, SearchInvestListResponse datas);

        void loadBannerSuccess(GetBannerListResponse datas);

    }

    interface Presenter extends BasePresenter {

        void loadList(int page);

        void pullToLoadList();

        void loadBanner();
    }
}
