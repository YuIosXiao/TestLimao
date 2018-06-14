package com.first.saccelerator.http;


import com.first.saccelerator.model.ApiResponse;
import com.first.saccelerator.model.AutoRenewResponse;
import com.first.saccelerator.model.BindPromoCodeResponse;
import com.first.saccelerator.model.ChangePassWordResponse;
import com.first.saccelerator.model.CheckinResponse;
import com.first.saccelerator.model.ClientLogResponse;
import com.first.saccelerator.model.CommonProblemResponse;
import com.first.saccelerator.model.CommonsResponse;
import com.first.saccelerator.model.DynamicServerFailedResponse;
import com.first.saccelerator.model.DynamicServersResponse;
import com.first.saccelerator.model.GetFeedbacksDetailResponse;
import com.first.saccelerator.model.GetFeedbacksResponse;
import com.first.saccelerator.model.GetFeedbacksResponseV2;
import com.first.saccelerator.model.ModifyAccountResponse;
import com.first.saccelerator.model.MyExpensesRecordResponse;
import com.first.saccelerator.model.NodesResponse;
import com.first.saccelerator.model.NoticesResponse;
import com.first.saccelerator.model.OpenScreenAdResponse;
import com.first.saccelerator.model.OrderResponse;
import com.first.saccelerator.model.PlansResponse;
import com.first.saccelerator.model.Plansv2Response;
import com.first.saccelerator.model.PopUpAdResponse;
import com.first.saccelerator.model.ProfileResponse;
import com.first.saccelerator.model.ProxyIpResponse;
import com.first.saccelerator.model.RegisteredResponse;
import com.first.saccelerator.model.RenewResponse;
import com.first.saccelerator.model.RoutesResponse;
import com.first.saccelerator.model.ServerConnectResponse;
import com.first.saccelerator.model.ServerConnectSecondResponse;
import com.first.saccelerator.model.ServerConnectSecondResponsev2;
import com.first.saccelerator.model.SigninResponse;
import com.first.saccelerator.model.SigninResponseV2;
import com.first.saccelerator.model.SystemEnumsResponse;
import com.first.saccelerator.model.TransactionStatusResponse;
import com.first.saccelerator.model.VerifyIapResponse;
import com.first.saccelerator.model.WebsitesResponse;

import java.lang.reflect.Member;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * RESTful 客户端接口服务
 * baseUrl : https://xiaoguoqi.com/
 * 具体调用及参数说明参考 com.first.saccelerator.NetworkTest
 * <p/>
 * Created by ZhengSheng on 2017/3/21.
 */

public interface ApiService {

    /**
     * 获取手机短信验证码
     * 此接口会向客户端指定的手机发送短信验证码
     *
     * @return
     */
    @POST("/api/client/v1/send_verification_code")
    Observable<ApiResponse> sendVerificationCode(@QueryMap Map<String, String> options);

    /**
     * 用户登录
     * 请求参数中如不提供 telephone 和 password 则视为游客登录，此时会根据设备uuid来查找或创建用户。
     * 否则视为注册用户登录。
     *
     * @param options
     * @return
     */
    @POST("/api/client/v1/signin")
    Observable<ApiResponse<SigninResponse>> signin(@QueryMap Map<String, String> options);

    /**
     * 注册用户找回或修改密码
     * 此接口在调用前需调用获取短信验证码接口。
     *
     * @param options
     * @return
     */
    @POST("/api/client/v1/reset_password")
    Observable<ApiResponse> resetPassword(@QueryMap Map<String, String> options);

    /**
     * 完善资料绑定社交帐号(认证)
     * 此接口必须指定至少一个绑定帐号。
     * 在所有帐号绑定完成后，后端将升级用户组信息，并返回用户相关信息(在未绑定所有帐号时，返回信息中不包含用户信息)。
     *
     * @param options
     * @return
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/api/client/v1/users/update_profile")
    Observable<ApiResponse> updateProfile(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 提交操作日志(认证)
     * 本接口用于记录用户的界面操作。
     *
     * @param options
     * @return
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/api/client/v1/users/operation_log")
    Observable<ApiResponse> operationLog(@QueryMap Map<String, String> options);

    /**
     * 获得所有服务器节点数据(认证)
     *
     * @return
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("/api/client/v1/nodes")
    Observable<ApiResponse<NodesResponse>> nodes(@Header("Authorization") String token);

    /**
     * 获得公告数据(认证)
     * 获得最新的一条公告内容，客户端可根据公告ID来记录在本地判断以后是否显示此公告。 如果没有公告，notice将为null
     *
     * @return
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("/api/client/v1/notices")
    Observable<NoticesResponse> notices(@Header("Authorization") String token);

    /**
     * 提交反馈(认证)
     *
     * @return
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/api/client/v1/feedbacks")
    Observable<ApiResponse> feedbacks(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 提交反馈(认证)
     *
     * @return
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("/api/client/v1/feedbacks")
    Observable<ApiResponse<GetFeedbacksResponse>> getFeedbacks(@Header("Authorization") String token, @QueryMap Map<String, Integer> options);

    /**
     * 获取版本渠道信息
     *
     * @param type 0: 渠道, 1: 版本
     * @return
     */
    @GET("/api/client/v1/system_enums")
    Observable<SystemEnumsResponse> systemEnums(@Query("type") int type);


    /**
     * 注册用户修改用户名为手机号(认证)
     *
     * @param options
     * @return
     */
    @POST("/api/client/v1/users/update_username")
    Observable<ApiResponse<ModifyAccountResponse>> updateusername(@Header("Authorization") String token, @QueryMap Map<String, String> options);


    /**
     * 修改密码
     */
    @POST("/api/client/v1/users/update_password")
    Observable<ApiResponse<ChangePassWordResponse>> updatepassword(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 连接指定服务器线路(认证)
     */
    @POST("/api/client/v1/nodes/connect")
    Observable<ApiResponse<ServerConnectResponse>> connect(@Header("Authorization") String token, @Query("node_type_id") int node_id);

    /**
     * 用户签到(认证)
     */
    @POST("/api/client/v1/users/checkin")
    Observable<ApiResponse<CheckinResponse>> checkin(@Header("Authorization") String token);

    /**
     * 获得当前用户封号状态(认证)
     */
    @GET("/api/client/v1/commons")
    Observable<ApiResponse<CommonsResponse>> commons(@Header("Authorization") String token);

    /**
     * 用户找回密码
     */
    @POST("/api/client/v1/reset_password")
    Observable<ApiResponse<ChangePassWordResponse>> resetpassword(@QueryMap Map<String, String> options);

    /**
     * 提交登录失败信息
     */
    @POST("/api/client/v1/failed_logs/signin_failed")
    Observable<ApiResponse> signinfailed(@QueryMap Map<String, String> options);

    /**
     * 提交未操作信息
     */
    @POST("/api/client/v1/failed_logs/no_operation")
    Observable<ApiResponse> nooperation(@QueryMap Map<String, String> options);

    /**
     * 提交连接失败信息
     */
    @POST("/api/client/v1/failed_logs/connection_failed")
    Observable<ApiResponse> connectionfailed(@QueryMap Map<String, String> options);


    /**
     * 获得套餐数据(认证)
     */
    @GET("/api/client/v1/plans")
    Observable<ApiResponse<PlansResponse>> plans(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * Comsunny支付创建订单(认证)
     */
    @POST("/api/client/v1/plans/comsunny_order")
    Observable<ApiResponse<VerifyIapResponse>> verifyIap(@Header("Authorization") String token, @Query("plan_id") int plan_id);

    /**
     * 获取动态服务器信息
     */
    @GET("/api/client/v1/dynamic_servers")
    Observable<ApiResponse<DynamicServersResponse>> dynamicservers();

    /**
     * 根据订单号获取订单状态(认证)
     */
    @GET("/api/client/v1/plans/transaction_status")
    Observable<ApiResponse<TransactionStatusResponse>> transactionStatus(@Header("Authorization") String token, @Query("order_number") String orderNumber);

    /**
     * 获得当前用户最新信息(认证)
     */
    @GET("/api/client/v1/users/profile")
    Observable<ApiResponse<ProfileResponse>> profile(@Header("Authorization") String token);

    /**
     * 设置用户离线
     */
    @POST("/api/client/v1/users/offline")
    Observable<ApiResponse> offline(@Query("user_id") String user_id);

    /**
     * 按指定服务器类型连接(认证)
     */
    @POST("/api/client/v1/nodes/connect")
    Observable<ApiResponse<ServerConnectSecondResponse>> connecttype(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 获得客户端路由配置表
     */
    @GET("/api/client/v1/commons/routes?platform=android")
    Observable<ApiResponse<RoutesResponse>> routes();

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);


    /**
     * 提交动态服务器连接失败信息
     */
    @POST("/api/client/v1/failed_logs/dynamic_server_failed")
    Observable<ApiResponse<DynamicServerFailedResponse>> dynamicserverfailed(@QueryMap Map<String, String> options);

    /**
     * 提交代理服务器解析日志
     */
    @POST("/api/client/v1/users/proxy_connection_logs")
    Observable<ApiResponse> proxyconnectionlogs(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 提交服务器连接日志(认证)
     */
    @POST("/api/client/v1/nodes/connection_log")
    Observable<ApiResponse> connectionlog(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 提交用户分享日志(认证)
     */
    @POST("/api/client/v1/users/share")
    Observable<ApiResponse> share(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 自动为用户服务续期(认证)
     */
    @POST("/api/client/v1/users/renew")
    Observable<ApiResponse<RenewResponse>> renew(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 获得帮助手册列表(认证)
     */
    @GET("/api/client/v1/help_manuals")
    Observable<ApiResponse<CommonProblemResponse>> helpmanuals(@Header("Authorization") String token, @QueryMap Map<String, String> options);


    /**
     * 用户登录
     * v2
     */
    @POST("/api/client/v2/signin")
    Observable<ApiResponse<SigninResponseV2>> signinv2(@QueryMap Map<String, String> options);

    /**
     * Comsunny支付创建订单(认证)
     * v2
     */
    @POST("/api/client/v2/plans/comsunny_order")
    Observable<ApiResponse<VerifyIapResponse>> verifyIapv2(@Header("Authorization") String token, @QueryMap Map<String, String> options);


    /**
     * 获得所有服务器节点数据(认证)
     * v2
     *
     * @return
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("/api/client/v2/nodes")
    Observable<ApiResponse<NodesResponse>> nodesv2(@Header("Authorization") String token);

    /**
     * 客户端去向日志提交
     * v1
     *
     * @return
     */
    @POST("/api/client/v1/logs/client_connection_logs")
    Observable<ApiResponse<ProxyIpResponse>> clientconnectionlogs(@QueryMap Map<String, String> options);

    /**
     * 代理服务器连接失败信息提交
     * v1
     *
     * @return
     */
    @POST("/api/client/v1/failed_logs/proxy_connection_failed")
    Observable<ApiResponse> proxyconnectionfailed(@QueryMap Map<String, String> options);

    /**
     * 用户登录
     * v3
     */
    @POST("/api/client/v3/signin")
    Observable<ApiResponse<SigninResponseV2>> signinv3(@QueryMap Map<String, String> options);

    /**
     * 用户注册
     */
    @POST("/api/client/v1/signup")
    Observable<ApiResponse<RegisteredResponse>> signup(@QueryMap Map<String, String> options);

    /**
     * 按指定服务器类型连接(认证)
     * v2
     */
    @POST("/api/client/v2/nodes/connect")
    Observable<ApiResponse<ServerConnectSecondResponsev2>> connectv2(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 绑定推广码(认证)
     */
    @POST("/api/client/v1/users/bind_promo_code")
    Observable<ApiResponse<BindPromoCodeResponse>> bindpromocode(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 获得网站导航列表
     *
     * @return
     */
    @GET("/api/client/v1/websites")
    Observable<ApiResponse<WebsitesResponse>> getWebsites(@Header("Authorization") String token, @QueryMap Map<String, Integer> options);


    /**
     * 获得客户端路由配置表
     * V2
     */
    @GET("/api/client/v2/commons/routes?platform=android")
    Observable<ApiResponse<RoutesResponse>> routesv2();

    /**
     * 获取反馈列表V2(认证)
     */
    @GET("/api/client/v2/feedbacks")
    Observable<ApiResponse<GetFeedbacksResponseV2>> getFeedbacksv2(@Header("Authorization") String token, @QueryMap Map<String, Integer> options);

    /**
     * 获得指定反馈的回复列表(认证)
     */
    @GET("/api/client/v1/feedbacks/replies")
    Observable<ApiResponse<GetFeedbacksDetailResponse>> getFeedbacksDetail(@Header("Authorization") String token, @QueryMap Map<String, Integer> options);

    /**
     * 设置是否自动扣钻石续期(认证)
     */
    @POST("/api/client/v1/users/set_auto_renew")
    Observable<ApiResponse<AutoRenewResponse>> setAutoRenew(@Header("Authorization") String token, @QueryMap Map<String, String> options);


    /**
     * 用户登录
     * v4
     */
    @POST("/api/client/v4/signin")
    Observable<ApiResponse<SigninResponseV2>> signinv4(@QueryMap Map<String, String> options);

    /**
     * 用户注册
     * v2
     */
    @POST("/api/client/v2/signup")
    Observable<ApiResponse<RegisteredResponse>> signupv2(@QueryMap Map<String, String> options);

    /**
     * 发送回复到指定的反馈(认证)
     */
    @POST("/api/client/v1/feedbacks/reply")
    Observable<ApiResponse> commitReply(@Header("Authorization") String token, @QueryMap Map<String, String> options);


    /**
     * 获取当前用户的订单记录(认证)
     */
    @GET("/api/client/v1/plans/transaction_logs")
    Observable<ApiResponse<OrderResponse>> getOrder(@Header("Authorization") String token, @QueryMap Map<String, Integer> options);

    /**
     * 获取当前用户的消费记录(认证)
     */
    @GET("/api/client/v1/plans/consumption_logs")
    Observable<ApiResponse<MyExpensesRecordResponse>> consumptionlogs(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 判断客户端是否是在国内
     */
    @GET
    Call<ResponseBody> location(@Url String fileUrl);

    /**
     * 获得客户端路由配置表
     * V3
     */
    @GET("/api/client/v3/commons/routes?platform=android")
    Observable<ApiResponse<RoutesResponse>> routesv3();

    /**
     * Comsunny支付创建订单(认证)
     * v3
     */
    @POST("/api/client/v3/plans/comsunny_order")
    Observable<ApiResponse<VerifyIapResponse>> verifyIapv3(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 获得客户端日志提交相关信息(认证)
     */
    @GET("/api/client/v1/commons/client_log")
    Observable<ApiResponse<ClientLogResponse>> clientlog(@Header("Authorization") String token);

    /**
     * 获得开屏广告信息(认证)
     */
    @GET("/api/client/v1/ads/splash_screen")
    Observable<ApiResponse<OpenScreenAdResponse>> splashscreen(@QueryMap Map<String, String> options);


    /**
     * 提交开屏广告展示成功日志
     */
    @POST("/api/client/v1/ads/splash_screen_log")
    Observable<ApiResponse> splashscreenlog(@QueryMap Map<String, String> options);

    /**
     * 获得弹出广告信息
     */
    @GET("/api/client/v1/ads/popup")
    Observable<ApiResponse<PopUpAdResponse>> popup(@QueryMap Map<String, String> options);

    /**
     * 提交弹出广告展示成功日志
     */
    @POST("/api/client/v1/ads/popup_log")
    Observable<ApiResponse> popuplog(@QueryMap Map<String, String> options);


    /**
     * 测试RetrofitService重连
     */
    @GET("/TemplateProgramWeb/testByAction")
    Observable<ApiResponse<Member>> testByAction(@QueryMap Map<String, String> options);

    /**
     * 1.12 获得所有服务器线路数据(认证) v3接口
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("/api/client/v3/nodes")
    Observable<ApiResponse<NodesResponse>> nodesv3(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 1.16 获得套餐数据(认证) V2接口
     */
    @GET("/api/client/v2/plans")
    Observable<ApiResponse<Plansv2Response>> plansv2(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    /**
     * 1.19 创建Comsunny支付订单(H5页面)(认证)创建Comsunny支付订单(H5页面)(认证) V4 接口
     */
    @POST("/api/client/v4/plans/comsunny_order")
    Observable<ApiResponse<VerifyIapResponse>> verifyIapv4(@Header("Authorization") String token, @QueryMap Map<String, String> options);


    /**
     * 1.59 提交开屏广告点击日志
     * 此接口只在开屏广告被点击后才提交
     */
    @POST("/api/client/v1/ads/splash_screen_click_log")
    Observable<ApiResponse> splashscreenclicklog(@QueryMap Map<String, String> options);

    /**
     * 1.60 提交弹出广告点击日志
     * 接口只在弹出广告被点击后才提交
     */
    @POST("/api/client/v1/ads/popup_click_log")
    Observable<ApiResponse> popupclicklog(@QueryMap Map<String, String> options);

}