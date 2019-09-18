package cn.enjoytoday.base

/**
 * Created by Android Studio.
 * User: caihaifei
 * Date: 2019/8/12
 * Time: 14:37
 * WebBlog:http://www.enjoytoday.cn
 *
 * 状态码返回
 */
object NetState {

    const val TOKEN_OVER_DATE = 400013 //token过期
    const val REFRESH_TOKEN_OVER_DATE =40014 //refresh_token过期
    const val RESULT_OK = 200 //请求成功
    const val PAY_OUT_DATE_CODE = 30021 //订单已过期
    const val PAY_OUT_SPREE_CODE = 30059 //大礼包已过期
    const val ORDER_STATUS_CHANGE = 30024 //订单状态改变，需要重新刷新

    const val JSON_ERROR = 100000001  // json解析异常，不需要提示

    const val CART_GOODS_IS_TOO_MUCH = 30060 // 购物车添加商品数量达到上限

    const val NO_GOODS_INFO = 30014 // 没有商品详情

    //未知错误
    const val UNKNOWN_ERROR = Int.MIN_VALUE

    //网络异常
    const val NET_ERROR = 100000002
    //请求出错
    const val REQUEST_ERROR = 100000003
}