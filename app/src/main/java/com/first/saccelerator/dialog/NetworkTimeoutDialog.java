package com.first.saccelerator.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.first.saccelerator.R;
import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.database.DBHelper;
import com.first.saccelerator.database.DynamicServersDataSource;
import com.first.saccelerator.model.DynamicServersResponse;
import com.first.saccelerator.utils.SPUtils;
import com.first.saccelerator.utils.StaticStateUtils;
import com.first.saccelerator.utils.StringUtils;

import java.util.List;

/**
 * Created by Xq on 2017/11/1.
 * 网络超时 显示弹框
 */
public class NetworkTimeoutDialog extends Dialog {

    private Context mContext;

    public static TextView tv_loadingmsg;
    private ImageView iv_animation;
    private ImageView iv_animation2;
    private AnimationDrawable mAnimation;//动画1
    private AnimationDrawable mAnimation2;//动画2

    public NetworkTimeoutDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        setContentView(R.layout.dialog_networktimeout);
        // 宽度全屏
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth();
        lp.height = display.getHeight();
//        lp.x = SizeUtils.px2dp(100);
//        lp.y = SizeUtils.px2dp(100);
//        lp.x = SizeUtils.dp2px(100);
//        lp.y = SizeUtils.dp2px(55);
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        tv_loadingmsg = (TextView) this.findViewById(R.id.tv_loadingmsg);
        iv_animation = (ImageView) this.findViewById(R.id.iv_animation);
        iv_animation2 = (ImageView) this.findViewById(R.id.iv_animation2);


        SPUtils spUtils = new SPUtils(SPConstants.SP_DEFAULT);
        String defaultippositiontype = spUtils.getString(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "1");
        int defaultipposition = spUtils.getInt(SPConstants.DEFAULT.DEFAULTIPPOSITION, 0);
        int rescuefileipposition = spUtils.getInt(SPConstants.DEFAULT.RESCUEFILEIPPOSITION, 0);


        DynamicServersDataSource dataSource = new DynamicServersDataSource(new DBHelper(StaticStateUtils.basisActivity));
        List<DynamicServersResponse.ServersBean> list = dataSource.findAll();
        dataSource.close();

        String msg = "正在优化网络,请稍后";
        if (defaultippositiontype.equals("1")) {
            msg = msg + "(#" + "1" + (defaultipposition - (list.size() - StaticStateUtils.ipArray.length) + 1) + ")";
        } else if (defaultippositiontype.equals("2")) {
            msg = msg + "(#" + "2" + (defaultipposition + 1) + ")";
        } else if (defaultippositiontype.equals("3")) {
            msg = msg + "(#" + "3" + rescuefileipposition + ")";
        }
        tv_loadingmsg.setText(msg);

        startAnimation();
    }


    /**
     * 开始动画
     * 创建者 XQ 20171110
     */
    public void startAnimation() {
        iv_animation.setImageDrawable(mContext.getResources().getDrawable(R.drawable.networktimeout_limaoanimation));
        mAnimation = (AnimationDrawable) iv_animation.getDrawable();
        mAnimation.start();


        iv_animation2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.networktimeout_limaoanimation_two));
        mAnimation2 = (AnimationDrawable) iv_animation2.getDrawable();
        mAnimation2.start();

    }

    /**
     * 结束动画
     * 创建者 XQ 20171110
     */
    public void endAnimation() {
        if (!StringUtils.isBlank(mAnimation) && mAnimation.isRunning()) {
            mAnimation.stop();
        }
        if (!StringUtils.isBlank(mAnimation2) && mAnimation2.isRunning()) {
            mAnimation2.stop();
        }
    }
}
