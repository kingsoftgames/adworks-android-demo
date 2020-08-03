package com.sukhavati.gotoplaying.bubble.BubbleShooter.mint.mi;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.kingsoft.shiyou.adworks.AdWorks;
import com.kingsoft.shiyou.adworks.IAdLoadListener;
import com.kingsoft.shiyou.adworks.IAdworksInitializeCallback;

public class MainActivity extends AppCompatActivity {
    private static final String TEST_ADMOB_BANNER_ID = "ca-app-pub-3940256099942544/6300978111";
    private static final String TEST_ADMOB_INTERSTITICAL_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String TEST_ADMOB_REWARD_ID = "ca-app-pub-3940256099942544/5224354917";

    private static final String TEST_IRONSOURCE_BANNER_ID = "main_banner";
    private static final String TEST_IRONSOURCE_INTERSTITICAL_ID = "main_interstitial";
    private static final String TEST_IRONSOURCE_REWARD_ID = "main_reward";

    private static final String ADMOB_PLATFORM_APPID = "test10001";
    private static final String IRONSOURCE_PLATFORM_APPID = "test10002";

    private String activePlatform = ADMOB_PLATFORM_APPID;
    private String activePlatformBannerId = TEST_ADMOB_BANNER_ID;
    private String activePlatformInterstiticalId = TEST_ADMOB_INTERSTITICAL_ID;
    private String activePlatformRewardId = TEST_ADMOB_REWARD_ID;

    private TextView tvTestCrash;
    private Button mInterstiticalButton, mRewardButton, mBannerButton, mInitAdmobButton, mInitIronSourceButton;
    private TextView mActivePlatformTextView;

    private AdWorks adWorks = AdWorks.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInitAdmobButton = findViewById(R.id.btnAdmob);
        mInitAdmobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAdworks(ADMOB_PLATFORM_APPID);
            }
        });
        mInitIronSourceButton = findViewById(R.id.btnIronSource);
        mInitIronSourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAdworks(IRONSOURCE_PLATFORM_APPID);
            }
        });
        mActivePlatformTextView = findViewById(R.id.activePlatform);
        mInterstiticalButton = findViewById(R.id.btn_in);
        mInterstiticalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testInterstitial();
            }
        });
        mRewardButton = findViewById(R.id.btn_re);
        mRewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testReward();
            }
        });
        mBannerButton = findViewById(R.id.btn_banner);
        mBannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testBanner();
            }
        });
    }

    private void initAdworks(String appId) {
        activePlatform = appId;
        AdWorks.getInstance().initAdWorkds(MainActivity.this, appId, new IAdworksInitializeCallback() {
            @Override
            public void onInitializeCallback(boolean hasInitialized) {
                toastUtil("hasInitialized: " + hasInitialized);
            }
        });
        showSelectedPaltFormName();
        changeBtnClickState();
        setAdKeyForPlatform();
    }

    private void showSelectedPaltFormName() {
        if (activePlatform.equals(IRONSOURCE_PLATFORM_APPID)) {
            mActivePlatformTextView.setText("当前初始化广告平台为IronSource");
        }
        if (activePlatform.equals(ADMOB_PLATFORM_APPID)) {
            mActivePlatformTextView.setText("当前初始化广告平台为Admob");
        }
    }

    private void changeBtnClickState() {
        mInterstiticalButton.setEnabled(true);
        mRewardButton.setEnabled(true);
        mBannerButton.setEnabled(true);
    }

    private void setAdKeyForPlatform() {
        if (activePlatform.equals(IRONSOURCE_PLATFORM_APPID)) {
            activePlatformBannerId = TEST_IRONSOURCE_BANNER_ID;
            activePlatformInterstiticalId = TEST_IRONSOURCE_INTERSTITICAL_ID;
            activePlatformRewardId = TEST_IRONSOURCE_REWARD_ID;
        }
        if (activePlatform.equals(ADMOB_PLATFORM_APPID)) {
            activePlatformBannerId = TEST_ADMOB_BANNER_ID;
            activePlatformInterstiticalId = TEST_ADMOB_INTERSTITICAL_ID;
            activePlatformRewardId = TEST_ADMOB_REWARD_ID;
        }
    }

    private void testInterstitial() {
        if (adWorks.isAdReady(this, activePlatformInterstiticalId)) {
            adWorks.showInterstitialAd(this, activePlatformInterstiticalId);
        }

        adWorks.loadInterstitialAd(this, activePlatformInterstiticalId, new IAdLoadListener() {
            @Override
            public void onAdClosed() {
                toastUtil(getString(R.string.string_interstitial_close));
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                toastUtil(getString(R.string.string_interstitial_load_fail));
            }

            @Override
            public void onAdFailedToShow(int errorCode) {
            }

            @Override
            public void onAdLeaveApplication() {
                toastUtil(getString(R.string.string_interstitial_left_app));
            }

            @Override
            public void onAdOpened() {
                toastUtil(getString(R.string.string_interstitial_open));
            }

            @Override
            public void onAdLoaded() {
                toastUtil(getString(R.string.string_interstitial_load_success));
            }

            @Override
            public void onAdClicked() {
                toastUtil(getString(R.string.string_interstitial_click));
            }

            @Override
            public void onAdImpression() {
            }

            @Override
            public void onUserEarnedReward() {
            }
        });
    }

    private void testReward() {
        if (AdWorks.getInstance().isAdReady(this, activePlatformRewardId)) {
            adWorks.showRewardAd(this, activePlatformRewardId);
        }

        AdWorks.getInstance().loadRewardAd(MainActivity.this, activePlatformRewardId, new IAdLoadListener() {
            @Override
            public void onAdClosed() {

                toastUtil("激励关闭");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                toastUtil(getString(R.string.string_reward_video_load_fail));

            }

            @Override
            public void onAdFailedToShow(int errorCode) {

            }

            @Override
            public void onAdLeaveApplication() {
                toastUtil(getString(R.string.string_reward_video_left_app));
            }


            @Override
            public void onAdOpened() {
                toastUtil(getString(R.string.string_reward_video_open));

            }

            @Override
            public void onAdLoaded() {
                toastUtil(getString(R.string.string_reward_video_loaded));
            }

            @Override
            public void onAdClicked() {
                toastUtil(getString(R.string.string_reward_video_click));
            }

            @Override
            public void onAdImpression() {

            }

            @Override
            public void onUserEarnedReward() {
                toastUtil(getString(R.string.string_reward_video_complete));
            }
        });


    }

    private void showReward() {
        AdWorks.getInstance().showRewardAd(MainActivity.this, activePlatformRewardId);
    }

    private void testBanner() {
        if (AdWorks.getInstance().isAdReady(MainActivity.this, activePlatformBannerId)) {
            AdWorks.getInstance().showBannerAd(MainActivity.this, activePlatformBannerId, Gravity.CENTER, null);
        }
        AdWorks.getInstance().loadBannerAd(this, activePlatformBannerId, new IAdLoadListener() {
            @Override
            public void onAdClosed() {
                toastUtil(getString(R.string.string_banner_close));
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                toastUtil(getString(R.string.string_banner_load_fail));
            }

            @Override
            public void onAdFailedToShow(int errorCode) {

            }

            @Override
            public void onAdLeaveApplication() {

            }


            @Override
            public void onAdOpened() {
                toastUtil(getString(R.string.string_banner_open));
            }

            @Override
            public void onAdLoaded() {
                toastUtil(getString(R.string.string_banner_loaded));
                AdWorks.getInstance().showBannerAd(MainActivity.this, activePlatformBannerId, Gravity.CENTER, null);
            }

            @Override
            public void onAdClicked() {
                toastUtil(getString(R.string.string_banner_click));
            }

            @Override
            public void onAdImpression() {

            }

            @Override
            public void onUserEarnedReward() {

            }
        });
    }

    private void toastUtil(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}