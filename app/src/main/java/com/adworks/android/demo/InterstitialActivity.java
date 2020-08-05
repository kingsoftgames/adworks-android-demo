package com.adworks.android.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.kingsoft.shiyou.adworks.AdWorks;
import com.kingsoft.shiyou.adworks.IAdLoadListener;

public class InterstitialActivity extends AppCompatActivity {
    private AdWorks adWorks = AdWorks.getInstance();
    private String activePlatformInterstiticalId;
    private Button button;
    private IAdLoadListener interstitialCallback=new IAdLoadListener() {
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
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        button=findViewById(R.id.btn_finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        activePlatformInterstiticalId = AdKey.TEST_IRONSOURCE_INTERSTITICAL_ID;
        adWorks.loadInterstitialAd(this, activePlatformInterstiticalId, interstitialCallback);
        testInterstitial();
    }

    private void testInterstitial() {
        if (adWorks.isAdReady(this, activePlatformInterstiticalId)) {
            adWorks.showInterstitialAd(this, activePlatformInterstiticalId);
        }

    }

}