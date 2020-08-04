package com.sukhavati.gotoplaying.bubble.BubbleShooter.mint.mi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kingsoft.shiyou.adworks.AdWorks;
import com.kingsoft.shiyou.adworks.IAdLoadListener;

public class InterstitialActivity extends AppCompatActivity {
    private AdWorks adWorks = AdWorks.getInstance();
    private String activePlatformInterstiticalId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        activePlatformInterstiticalId = AdKey.TEST_ADMOB_INTERSTITICAL_ID;
        testInterstitial();
    }

    private void testInterstitial() {
        if (adWorks.isAdReady(this, activePlatformInterstiticalId)) {
            adWorks.showInterstitialAd(this, activePlatformInterstiticalId);
        }

        adWorks.loadInterstitialAd(this, activePlatformInterstiticalId, new IAdLoadListener() {
            @Override
            public void onAdClosed() {
                ToaUtils.toastShort(InterstitialActivity.this, getString(R.string.string_interstitial_close));
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                ToaUtils.toastShort(InterstitialActivity.this, getString(R.string.string_interstitial_load_fail));
            }

            @Override
            public void onAdFailedToShow(int errorCode) {
            }

            @Override
            public void onAdLeaveApplication() {
                ToaUtils.toastShort(InterstitialActivity.this, getString(R.string.string_interstitial_left_app));
            }

            @Override
            public void onAdOpened() {
                ToaUtils.toastShort(InterstitialActivity.this, getString(R.string.string_interstitial_open));
            }

            @Override
            public void onAdLoaded() {
                ToaUtils.toastShort(InterstitialActivity.this, getString(R.string.string_interstitial_load_success));
            }

            @Override
            public void onAdClicked() {
                ToaUtils.toastShort(InterstitialActivity.this, getString(R.string.string_interstitial_click));
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