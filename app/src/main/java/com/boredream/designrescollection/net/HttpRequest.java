package com.boredream.designrescollection.net;


import com.boredream.bdcodehelper.entity.AppUpdateInfo;
import com.boredream.bdcodehelper.entity.FileUploadResponse;
import com.boredream.bdcodehelper.entity.ListResponse;
import com.boredream.designrescollection.base.BaseEntity;
import com.boredream.designrescollection.entity.FeedBack;
import com.boredream.designrescollection.entity.GetBannerListRequest;
import com.boredream.designrescollection.entity.GetBannerListResponse;
import com.boredream.designrescollection.entity.SearchInvestListRequest;
import com.boredream.designrescollection.entity.SearchInvestListResponse;
import com.boredream.designrescollection.entity.User;
import com.boredream.designrescollection.utils.UserInfoKeeper;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;
import rx.functions.Action1;

public class HttpRequest {
    // LeanCloud
    public static final String HOST = "http://121.42.165.220:10080";
    public static final String FILE_HOST = "";
    public static final String SESSION_TOKEN_KEY = "X-LC-Session";
    private static final String APP_ID_NAME = "X-LC-Id";
    private static final String API_KEY_NAME = "X-LC-Key";
    private static final String APP_ID_VALUE = "iaEH7ObIA4sPY8RSs3VCVXBg-gzGzoHsz";
    private static final String API_KEY_VALUE = "dXfhXIVyeWMN2czJkd4ehwzs";
    private static HttpRequest ourInstance = new HttpRequest();
    public ApiService service;
    private OkHttpClient httpClient;

    private HttpRequest() {
        // OkHttpClient
        httpClient = new OkHttpClient();

        // 统一添加的Header
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader(APP_ID_NAME, APP_ID_VALUE)
                        .addHeader(API_KEY_NAME, API_KEY_VALUE)
                        .addHeader(SESSION_TOKEN_KEY, UserInfoKeeper.getToken())
                        .build();
                return chain.proceed(request);
            }
        });

        // log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);

        // Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create()) // gson
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // rxjava
                .client(httpClient)
                //.callbackExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                .build();

        service = retrofit.create(ApiService.class);
    }

    public static HttpRequest getInstance() {
        return ourInstance;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * 查询设计资源
     */
    public Observable<SearchInvestListResponse> getDesignRes(int page) {
        String where = "{}";
        SearchInvestListRequest request = new SearchInvestListRequest();
        request.setSearchname("50");
        request.setSearchtype("1");
        request.setPagesize(10);
        request.setIsindex("0");
        request.setPageno(page);
        return service.getDesignRes(request);
    }

    ////////////////////////////// 业务接口方法 //////////////////////////////

    public Observable<BaseEntity> updateNickname(String userid, String nickname) {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("nickname", nickname);

        return service.updateUserById(userid, updateMap);
    }

    /**
     * 拉取Banner
     * @return
     */
    public Observable<GetBannerListResponse> getBanner(){
        GetBannerListRequest request = new GetBannerListRequest();
        return service.getBanner(request);
    }
//    /**
//     * 查询设计资源
//     *
//     * @param page
//     * @param name 搜索名称
//     */
//    public Observable<ListResponse<DesignRes>> getDesignRes(int page, String name) {
//        String whereName = "{}";
//        if (!TextUtils.isEmpty(name)) {
//            whereName = "{\"name\":{\"$regex\":\".*" + name + ".*\"}}";
//        }
//        String where = whereName;
//        return service.getDesignRes(CommonConstants.COUNT_OF_PAGE,
//                (page - 1) * CommonConstants.COUNT_OF_PAGE, where, null);
//    }

    /**
     * 登录用户
     *
     * @param username 用户名
     * @param password 密码
     */
    public Observable<User> login(String username, String password) {
        return service.login(username, password)
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        // 保存登录用户数据以及token信息
                        UserInfoKeeper.setCurrentUser(user);
                    }
                });
    }


    ////////////////////////////// 通用接口方法 //////////////////////////////

    /**
     * 上传文件
     *
     * @param bytes
     */
    public Observable<FileUploadResponse> fileUpload(byte[] bytes, String filename, MediaType type) {
        RequestBody requestBody = RequestBody.create(type, bytes);
        return service.fileUpload(filename, requestBody);
    }

    public interface ApiService {
        ////////////////////////////// 通用接口 //////////////////////////////
        // 登录用户
        @GET("/1/login")
        Observable<User> login(
                @Query("username") String username,
                @Query("password") String password);

        // 注册用户
        @POST("/1/users")
        Observable<User> register(
                @Body User user);

        // 修改用户详情(注意, 提交什么参数修改什么参数)
        @PUT("/1/users/{objectId}")
        Observable<BaseEntity> updateUserById(
                @Path("objectId") String userId,
                @Body Map<String, Object> updateInfo);

        // 上传图片接口
        @POST("/1/files/{fileName}")
        Observable<FileUploadResponse> fileUpload(
                @Path("fileName") String fileName,
                @Body RequestBody image);

        // 查询app更新信息
        @GET("/1/classes/AppUpdateInfo")
        Observable<ListResponse<AppUpdateInfo>> getAppUpdateInfo();

        // 提交意见反馈
        @POST("/1/classes/FeedBack")
        Observable<BaseEntity> addFeedBack(
                @Body FeedBack feedBack);


        ////////////////////////////// 业务接口 //////////////////////////////

        // 首页推荐标的接口
        @POST("/message/searchInvestList")
        Observable<SearchInvestListResponse> getDesignRes(
                @Body SearchInvestListRequest request);

        // Banner接口
        @POST("/message/getBannerList")
        Observable<GetBannerListResponse> getBanner(
                @Body GetBannerListRequest request);
    }

}

