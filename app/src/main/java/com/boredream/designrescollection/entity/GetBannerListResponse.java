package com.boredream.designrescollection.entity;

import com.boredream.bdcodehelper.entity.ImageUrlInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取banner.服务端响应
 *
 * @author 代码生成器v1.0
 */
public class GetBannerListResponse extends ResponseSupport {

    private ArrayList<ElementBannerList> bannerList;

    public GetBannerListResponse() {
        super();
        setMessageId("getBannerList");
    }

    /**
     * @return banner列表
     */
    public ArrayList<ElementBannerList> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<ElementBannerList> bannerList) {
        this.bannerList = (ArrayList<ElementBannerList>) bannerList;
    }

    public static class ElementBannerList implements ImageUrlInterface {

        private String bannerurl;

        /**
         * @return banner地址
         */
        public String getBannerurl() {
            return bannerurl;
        }

        public void setBannerurl(String bannerurl) {
            this.bannerurl = bannerurl;
        }

        @Override
        public String getImageUrl() {
            return "http://photocdn.sohu.com/20151003/mp34194651_1443837188811_3.jpeg";
        }

        @Override
        public String getImageTitle() {
            return "";
        }

        @Override
        public String getImageLink() {
            return "";
        }
    }
}