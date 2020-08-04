package com.sukhavati.gotoplaying.bubble.BubbleShooter.mint.mi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.kingsoft.shiyou.adworks.AdWorks;
import com.kingsoft.shiyou.adworks.IAdLoadListener;

public class InterstitialActivity extends AppCompatActivity {
    private AdWorks adWorks = AdWorks.getInstance();
    private String activePlatformInterstiticalId;
    private String activePlatformRewardId;
    private Button  mRewardButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        Intent intent=getIntent();
        activePlatformInterstiticalId=intent.getStringExtra("interstitialkey");
        activePlatformRewardId=intent.getStringExtra("rewardkey");
        if (!TextUtils.isEmpty(activePlatformInterstiticalId)){
            testInterstitial();
        }
        mRewardButton = findViewById(R.id.btn_re);
        mRewardButton.setEnabled(AdWorks.getInstance().isAdReady(this, activePlatformRewardId));
        mRewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(activePlatformRewardId)){
                    testReward();
                }

            }
        });
    }
    private void testInterstitial() {
        if (adWorks.isAdReady(this, activePlatformInterstiticalId)) {
            adWorks.showInterstitialAd(this, activePlatformInterstiticalId);
        }

        adWorks.loadInterstitialAd(this, activePlatformInterstiticalId, new IAdLoadListener() {
            @Override
            public void onAdClosed() {
                ToaUtils.toastShort(InterstitialActivity.this,getString(R.string.string_interstitial_close));
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                ToaUtils.toastShort(InterstitialActivity.this,getString(R.string.string_interstitial_load_fail));
            }

            @Override
            public void onAdFailedToShow(int errorCode) {
            }

            @Override
            public void onAdLeaveApplication() {
                ToaUtils.toastShort(InterstitialActivity.this,getString(R.string.string_interstitial_left_app));
            }

            @Override
            public void onAdOpened() {
                ToaUtils.toastShort(InterstitialActivity.this,getString(R.string.string_interstitial_open));
            }

            @Override
            public void onAdLoaded() {
                ToaUtils.toastShort(InterstitialActivity.this,getString(R.string.string_interstitial_load_success));
            }

            @Override
            public void onAdClicked() {
                ToaUtils.toastShort(InterstitialActivity.this,getString(R.string.string_interstitial_click));
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
        adWorks.showRewardAd(this, activePlatformRewardId);
        AdWorks.getInstance().loadRewardAd(InterstitialActivity.this, activePlatformRewardId, new IAdLoadListener() {
            @Override
            public void onAdClosed() {

                ToaUtils.toastShort(InterstitialActivity.this,"激励关闭");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                ToaUtils.toastShort(InterstitialActivity.this,getString(R.string.string_reward_video_load_fail));

            }

            @Override
            public void onAdFailedToShow(int errorCode) {

            }

            @Override
            public void onAdLeaveApplication() {
                ToaUtils.toastShort(InterstitialActivity.this,getString(R.string.string_reward_video_left_app));
            }


            @Override
            public void onAdOpened() {
                ToaUtils.toastShort(InterstitialActivity.this,getString(R.string.string_reward_video_open));

            }

            @Override
            public void onAdLoaded() {
                ToaUtils.toastShort(InterstitialActivity.this,getString(R.string.string_reward_video_loaded));
            }

            @Override
            public void onAdClicked() {
                ToaUtils.toastShort(InterstitialActivity.this,getString(R.string.string_reward_video_click));
            }

            @Override
            public void onAdImpression() {

            }

            @Override
            public void onUserEarnedReward() {
                ToaUtils.toastShort(InterstitialActivity.this,getString(R.string.string_reward_video_complete));
            }
        });


    }
}