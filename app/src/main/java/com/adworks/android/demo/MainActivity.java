package com.adworks.android.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kingsoft.shiyou.adworks.AdWorks;
import com.kingsoft.shiyou.adworks.IAdLoadListener;
import com.kingsoft.shiyou.adworks.IAdworksInitializeCallback;
import com.kingsoft.shiyou.adworks.bean.AdUnits;

public class MainActivity extends AppCompatActivity {
    /**
     * Adworks 详细行为日志 粗略级别 AdworksDebug 详尽级别 x-log
     */
    private static final String ADMOB_PLATFORM_APPID = "test10001";
    private static final String IRONSOURCE_PLATFORM_APPID = "test10002";
    private static final String APPLOVINMAX_PLATFORM_APPID = "Adworks100000023";

    private String activePlatform = APPLOVINMAX_PLATFORM_APPID;
    private String activePlatformBannerId = AdKey.TEST_ADMOB_BANNER_ID;
    private String activePlatformRewardId = AdKey.TEST_ADMOB_REWARD_ID;
    private String activePlatformInterstiticalId = AdKey.TEST_ADMOB_INTERSTITICAL_ID;
    private String activePlatformNativeId = "";
    private Button mInterstiticalButton, mBannerButton;
    private ViewGroup frameLayout;
    private Button mNativeButton, mRewardButton,mWebButton;
    private AdWorks adWorks = AdWorks.getInstance();
    private IAdLoadListener bannerCallback = new IAdLoadListener() {
        @Override
        public void onAdClosed() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_banner_close));
        }

        @Override
        public void onAdFailedToLoad(String errorCode) {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_banner_load_fail));
        }

        @Override
        public void onAdFailedToShow(String errorCode) {

        }

        @Override
        public void onAdLeaveApplication() {

        }


        @Override
        public void onAdOpened() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_banner_open));
        }

        @Override
        public void onAdLoaded() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_banner_loaded));
        }

        @Override
        public void onAdClicked() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_banner_click));
        }

        @Override
        public void onAdImpression() {

        }

        @Override
        public void onUserEarnedReward() {

        }
    };
    private IAdLoadListener rewardCallback = new IAdLoadListener() {
        @Override
        public void onAdClosed() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_reward_video_close));
        }

        @Override
        public void onAdFailedToLoad(String errorCode) {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_reward_video_load_fail));

        }

        @Override
        public void onAdFailedToShow(String errorCode) {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_reward_video_open_fail));
        }

        @Override
        public void onAdLeaveApplication() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_reward_video_left_app));
        }


        @Override
        public void onAdOpened() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_reward_video_open));

        }

        @Override
        public void onAdLoaded() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_reward_video_loaded));
        }

        @Override
        public void onAdClicked() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_reward_video_click));
        }

        @Override
        public void onAdImpression() {

        }

        @Override
        public void onUserEarnedReward() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_reward_video_complete));
        }
    };
    private IAdLoadListener interstitialCallback = new IAdLoadListener() {
        @Override
        public void onAdClosed() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_interstitial_close));
        }

        @Override
        public void onAdFailedToLoad(String errorCode) {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_interstitial_load_fail));
        }

        @Override
        public void onAdFailedToShow(String errorCode) {
        }

        @Override
        public void onAdLeaveApplication() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_interstitial_left_app));
        }

        @Override
        public void onAdOpened() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_interstitial_open));
        }

        @Override
        public void onAdLoaded() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_interstitial_load_success));
        }

        @Override
        public void onAdClicked() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_interstitial_click));
        }

        @Override
        public void onAdImpression() {
        }

        @Override
        public void onUserEarnedReward() {
        }
    };
    private IAdLoadListener nativeCallback = new IAdLoadListener() {
        @Override
        public void onAdClosed() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_native_close));
        }

        @Override
        public void onAdFailedToLoad(String errorCode) {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_native_load_fail) + "错误代码为" + errorCode);
        }

        @Override
        public void onAdFailedToShow(String errorCode) {

        }

        @Override
        public void onAdLeaveApplication() {

        }


        @Override
        public void onAdOpened() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_native_open));
        }

        @Override
        public void onAdLoaded() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_native_loaded));
        }

        @Override
        public void onAdClicked() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_native_click));
        }

        @Override
        public void onAdImpression() {

        }

        @Override
        public void onUserEarnedReward() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.ad_layout);
        mInterstiticalButton = findViewById(R.id.btn_in);
        mBannerButton = findViewById(R.id.btn_banner);
        mRewardButton = findViewById(R.id.btn_re);
        mWebButton = findViewById(R.id.btn_webview);
//        initAdworks(APPLOVINMAX_PLATFORM_APPID);
        mInterstiticalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testInterstitial();
            }
        });
        mBannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testBanner();
            }
        });
        mRewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testReward();
            }
        });
        mNativeButton = findViewById(R.id.btn_native);
        mNativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testNative();
            }
        });
        mWebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WebViewActivity.class));
            }
        });
    }

    private void initAdworks(String appId) {
        activePlatform = appId;
        adWorks.initAdWorks(MainActivity.this, appId, "95271afb0cca7ef6b8244d9b9b4ce5e7ab5b318c", new IAdworksInitializeCallback() {
            @Override
            public void onInitializeCallback(boolean hasInitialized) {
                ToaUtils.toastShort(MainActivity.this, "hasInitialized: " + hasInitialized);
                if (hasInitialized) {

                    //adWorks.loadBannerAd目前为注册接收各类型广告生命周期回调
                    //"main"接收广告回调的场景也可为""或者其他自定义标识信息
                    adWorks.registerListenerByAdType(AdUnits.AD_BANNER, bannerCallback, "main");
                    adWorks.registerListenerByAdType(AdUnits.AD_REWARD, rewardCallback, "main");
                    adWorks.registerListenerByAdType(AdUnits.AD_INTERSTITIAL, interstitialCallback, "main");
                    adWorks.registerListenerByAdType(AdUnits.AD_NATIVE, nativeCallback, "main");

                    changeBtnClickState();
                }
            }
        });
    }

    private void changeBtnClickState() {
        mInterstiticalButton.setEnabled(true);
        mBannerButton.setEnabled(true);
        mRewardButton.setEnabled(true);
        mNativeButton.setEnabled(true);
    }


    private void testInterstitial() {
        if (adWorks.isAdReady(this, AdUnits.AD_INTERSTITIAL, "main")) {
            adWorks.showInterstitialAd(this, "main");
        }
    }

    private void testReward() {
        if (AdWorks.getInstance().isAdReady(this, AdUnits.AD_REWARD, "main")) {
            adWorks.showRewardAd(this, "main");
        }

    }


    private void testBanner() {
        if (AdWorks.getInstance().isAdReady(MainActivity.this, AdUnits.AD_BANNER, "main")) {
            AdWorks.getInstance().showBannerAd(MainActivity.this, Gravity.CENTER, null, "main");
        }

    }

    private void testNative() {
        if (AdWorks.getInstance().isAdReady(MainActivity.this, AdUnits.AD_NATIVE, "main")) {
            AdWorks.getInstance().showNativeAd(MainActivity.this, frameLayout, "main");
        }
    }


}