package com.adworks.android.demo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;
import com.kingsoft.shiyou.adworks.AdWorks;
import com.kingsoft.shiyou.adworks.IAdLoadListener;
import com.kingsoft.shiyou.adworks.IAdworksInitializeCallback;

public class MainActivity extends AppCompatActivity {
    /**
     * Adworks 详细行为日志 粗略级别 AdworksDebug 详尽级别 x-log
     */
    private static final String ADMOB_PLATFORM_APPID = "test10001";
    private static final String IRONSOURCE_PLATFORM_APPID = "test10002";
    private static final String APPLOVINMAX_PLATFORM_APPID = "test10001";

    private String activePlatform = APPLOVINMAX_PLATFORM_APPID;
    private String activePlatformBannerId = AdKey.TEST_ADMOB_BANNER_ID;
    private String activePlatformRewardId = AdKey.TEST_ADMOB_REWARD_ID;
    private String activePlatformInterstiticalId = AdKey.TEST_ADMOB_INTERSTITICAL_ID;
    private Button mInterstiticalButton, mBannerButton;
    private ViewGroup frameLayout;
    private Button mRewardButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.ad_layout);
        mInterstiticalButton = findViewById(R.id.btn_in);
        mBannerButton = findViewById(R.id.btn_banner);
        mRewardButton = findViewById(R.id.btn_re);
        initAdworks(APPLOVINMAX_PLATFORM_APPID);
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
        AdView adView = new AdView(this);

        if (null == adView.getResponseInfo()) {
            ToaUtils.toastShort(MainActivity.this, "666666666666");
        }
    }

    private void initAdworks(String appId) {
        activePlatform = appId;
//        setAdKeyForPlatform();
        adWorks.initAdWorks(MainActivity.this, appId, new IAdworksInitializeCallback() {
            @Override
            public void onInitializeCallback(boolean hasInitialized) {
                ToaUtils.toastShort(MainActivity.this, "hasInitialized: " + hasInitialized);
                if (hasInitialized) {
                    //获取当前所使用的的各类型广告ID
                    activePlatformBannerId = adWorks.getAdTypeIdMap().get(AdWorks.AD_BANNER);
                    activePlatformRewardId = adWorks.getAdTypeIdMap().get(AdWorks.AD_REWARD);
                    activePlatformInterstiticalId = adWorks.getAdTypeIdMap().get(AdWorks.AD_INTERSTITIAL);
                    //adWorks.loadBannerAd目前为注册接收各类型广告生命周期回调
                    //"main"接收广告回调的场景也可为""或者其他自定义标识信息
                    adWorks.loadBannerAd(MainActivity.this, activePlatformBannerId, bannerCallback, "main");
                    adWorks.loadRewardAd(MainActivity.this, activePlatformRewardId, rewardCallback, "main");
                    adWorks.loadInterstitialAd(MainActivity.this, activePlatformInterstiticalId, interstitialCallback, "main");
                    changeBtnClickState();
                }
            }
        });

    }

    private void changeBtnClickState() {
        mInterstiticalButton.setEnabled(true);
        mBannerButton.setEnabled(true);
        mRewardButton.setEnabled(true);
    }


    private void testBanner() {
        //"main"判断广告缓存的场景也可为""或者其他自定义标识信息
        if (adWorks.isAdReady(MainActivity.this, activePlatformBannerId,"main")) {
            //"main"展示广告缓存的场景也可为""或者其他自定义标识信息
            adWorks.showBannerAd(MainActivity.this, activePlatformBannerId, Gravity.CENTER, frameLayout,"main");
        }
    }

    private void testReward() {
        if (AdWorks.getInstance().isAdReady(this, activePlatformRewardId, "main")) {
            adWorks.showRewardAd(this, activePlatformRewardId, "main");
        }
    }

    private void testInterstitial() {
        if (adWorks.isAdReady(this, activePlatformInterstiticalId, "main")) {
            adWorks.showInterstitialAd(this, activePlatformInterstiticalId, "main");
        }
    }

}