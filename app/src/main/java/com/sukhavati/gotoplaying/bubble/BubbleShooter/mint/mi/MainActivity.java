package com.sukhavati.gotoplaying.bubble.BubbleShooter.mint.mi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.kingsoft.shiyou.adworks.AdWorks;
import com.kingsoft.shiyou.adworks.IAdLoadListener;
import com.kingsoft.shiyou.adworks.IAdworksInitializeCallback;

public class MainActivity extends AppCompatActivity {

    private static final String ADMOB_PLATFORM_APPID = "test10001";
    private static final String IRONSOURCE_PLATFORM_APPID = "test10002";

    private String activePlatform = ADMOB_PLATFORM_APPID;
    private String activePlatformBannerId = AdKey.TEST_ADMOB_BANNER_ID;
    private String activePlatformInterstiticalId = AdKey.TEST_ADMOB_INTERSTITICAL_ID;
    private String activePlatformRewardId = AdKey.TEST_ADMOB_REWARD_ID;

    private Button mInterstiticalButton,mBannerButton, mInitAdmobButton, mInitIronSourceButton;
    private TextView mActivePlatformTextView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout=findViewById(R.id.ad_layout);
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
                Intent intent = new Intent(MainActivity.this,InterstitialActivity.class);
                intent.putExtra("interstitialkey",activePlatformInterstiticalId);
                intent.putExtra("rewardkey",activePlatformRewardId);
                startActivity(intent);
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
                ToaUtils.toastShort(MainActivity.this,"hasInitialized: " + hasInitialized);
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
        mBannerButton.setEnabled(true);
    }

    private void setAdKeyForPlatform() {
        if (activePlatform.equals(IRONSOURCE_PLATFORM_APPID)) {
            activePlatformBannerId = AdKey.TEST_IRONSOURCE_BANNER_ID;
            activePlatformInterstiticalId = AdKey.TEST_IRONSOURCE_INTERSTITICAL_ID;
            activePlatformRewardId = AdKey.TEST_IRONSOURCE_REWARD_ID;
        }
        if (activePlatform.equals(ADMOB_PLATFORM_APPID)) {
            activePlatformBannerId = AdKey.TEST_ADMOB_BANNER_ID;
            activePlatformInterstiticalId = AdKey.TEST_ADMOB_INTERSTITICAL_ID;
            activePlatformRewardId = AdKey.TEST_ADMOB_REWARD_ID;
        }
    }


    private void testBanner() {
        if (AdWorks.getInstance().isAdReady(MainActivity.this, activePlatformBannerId)) {
            AdWorks.getInstance().showBannerAd(MainActivity.this, activePlatformBannerId, Gravity.CENTER, frameLayout);
        }
        AdWorks.getInstance().loadBannerAd(this, activePlatformBannerId, new IAdLoadListener() {
            @Override
            public void onAdClosed() {
                ToaUtils.toastShort(MainActivity.this,getString(R.string.string_banner_close));
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                ToaUtils.toastShort(MainActivity.this,getString(R.string.string_banner_load_fail));
            }

            @Override
            public void onAdFailedToShow(int errorCode) {

            }

            @Override
            public void onAdLeaveApplication() {

            }


            @Override
            public void onAdOpened() {
                ToaUtils.toastShort(MainActivity.this,getString(R.string.string_banner_open));
            }

            @Override
            public void onAdLoaded() {
                ToaUtils.toastShort(MainActivity.this,getString(R.string.string_banner_loaded));
                AdWorks.getInstance().showBannerAd(MainActivity.this, activePlatformBannerId, Gravity.CENTER, null);
            }

            @Override
            public void onAdClicked() {
                ToaUtils.toastShort(MainActivity.this,getString(R.string.string_banner_click));
            }

            @Override
            public void onAdImpression() {

            }

            @Override
            public void onUserEarnedReward() {

            }
        });
    }


}