package com.adworks.android.demo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.kingsoft.shiyou.adworks.AdWorks;
import com.kingsoft.shiyou.adworks.IAdLoadListener;
import com.kingsoft.shiyou.adworks.IAdworksInitializeCallback;

public class MainActivity extends AppCompatActivity {

    private static final String ADMOB_PLATFORM_APPID = "test10001";
    private static final String IRONSOURCE_PLATFORM_APPID = "test10002";

    private String activePlatform = IRONSOURCE_PLATFORM_APPID;
    private String activePlatformBannerId = AdKey.TEST_ADMOB_BANNER_ID;
    private String activePlatformRewardId = AdKey.TEST_ADMOB_REWARD_ID;
    private String activePlatformInterstiticalId = AdKey.TEST_ADMOB_INTERSTITICAL_ID;
    private Button mInterstiticalButton, mBannerButton;
    private FrameLayout frameLayout;
    private Button mRewardButton;
    private AdWorks adWorks = AdWorks.getInstance();
    private IAdLoadListener bannerCallback=new IAdLoadListener() {
        @Override
        public void onAdClosed() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_banner_close));
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_banner_load_fail));
        }

        @Override
        public void onAdFailedToShow(int errorCode) {

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
    private IAdLoadListener rewardCallback=new IAdLoadListener() {
        @Override
        public void onAdClosed() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_reward_video_close));
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_reward_video_load_fail));

        }

        @Override
        public void onAdFailedToShow(int errorCode) {
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
    private IAdLoadListener interstitialCallback=new IAdLoadListener() {
        @Override
        public void onAdClosed() {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_interstitial_close));
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            ToaUtils.toastShort(MainActivity.this, getString(R.string.string_interstitial_load_fail));
        }

        @Override
        public void onAdFailedToShow(int errorCode) {
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
        initAdworks(IRONSOURCE_PLATFORM_APPID);
        mInterstiticalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testInterstitial();
//                Intent intent = new Intent(MainActivity.this, InterstitialActivity.class);
//                startActivity(intent);
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
    }

    private void initAdworks(String appId) {
        activePlatform = appId;
        setAdKeyForPlatform();
        adWorks.initAdWorkds(MainActivity.this, appId, new IAdworksInitializeCallback() {
            @Override
            public void onInitializeCallback(boolean hasInitialized) {
                ToaUtils.toastShort(MainActivity.this, "hasInitialized: " + hasInitialized);
                if (hasInitialized){
                    adWorks.loadBannerAd(MainActivity.this, activePlatformBannerId, bannerCallback);
                    adWorks.loadRewardAd(MainActivity.this, activePlatformRewardId, rewardCallback);
                    adWorks.loadInterstitialAd(MainActivity.this, activePlatformInterstiticalId, interstitialCallback);
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

    private void setAdKeyForPlatform() {
        if (activePlatform.equals(IRONSOURCE_PLATFORM_APPID)) {
            activePlatformBannerId = AdKey.TEST_IRONSOURCE_BANNER_ID;
            activePlatformRewardId = AdKey.TEST_IRONSOURCE_REWARD_ID;
            activePlatformInterstiticalId=AdKey.TEST_IRONSOURCE_INTERSTITICAL_ID;
        }
        if (activePlatform.equals(ADMOB_PLATFORM_APPID)) {
            activePlatformBannerId = AdKey.TEST_ADMOB_BANNER_ID;
            activePlatformRewardId = AdKey.TEST_ADMOB_REWARD_ID;
            activePlatformInterstiticalId=AdKey.TEST_ADMOB_INTERSTITICAL_ID;
        }
    }


    private void testBanner() {
        if (adWorks.isAdReady(MainActivity.this, activePlatformBannerId)) {
            adWorks.showBannerAd(MainActivity.this, activePlatformBannerId, Gravity.CENTER, frameLayout);
        }

    }

    private void testReward() {
        if (AdWorks.getInstance().isAdReady(this, activePlatformRewardId)) {
            adWorks.showRewardAd(this, activePlatformRewardId);
        }
    }
    private void testInterstitial() {
        if (adWorks.isAdReady(this, activePlatformInterstiticalId)) {
            adWorks.showInterstitialAd(this, activePlatformInterstiticalId);
        }
    }

}