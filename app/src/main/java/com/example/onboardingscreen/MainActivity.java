package com.example.onboardingscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnbordingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutOnbordingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setupOnboardingItems();

        final ViewPager2 onbordingViewPager = findViewById(R.id.onboardingViewPager);
        onbordingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();

        setCurrentOnboardingIndicators(0);

        onbordingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onbordingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    onbordingViewPager.setCurrentItem(onbordingViewPager.getCurrentItem()+1);
                }else {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            }
        });
    }

    private void setupOnboardingItems() {

        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemPayOnline = new OnboardingItem();
        itemPayOnline.setTitle("Pay your Bills");
        itemPayOnline.setDescription("Please pay your Bills on time online or offline..");
        itemPayOnline.setImage(R.drawable.ui);


        OnboardingItem itemOnWay = new OnboardingItem();
        itemOnWay.setTitle("Delivery Fast");
        itemOnWay.setDescription("We provides you the fastest delivery..");
        itemOnWay.setImage(R.drawable.ui);


        OnboardingItem itemEatTogether = new OnboardingItem();
        itemEatTogether.setTitle("Lets eat some Snacks.");
        itemEatTogether.setDescription("Eating snacks at evening time is the habit of childrens..");
        itemEatTogether.setImage(R.drawable.ui);

        onboardingItems.add(itemPayOnline);
        onboardingItems.add(itemOnWay);
        onboardingItems.add(itemEatTogether);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,0,8,0);
        for(int i=0;i<indicators.length;i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnbordingIndicators.addView(indicators[i]);
        }

    }

    private void setCurrentOnboardingIndicators(int index) {
        int childCount = layoutOnbordingIndicators.getChildCount();
        for(int i=0;i<childCount;i++){
            ImageView imageView = (ImageView) layoutOnbordingIndicators.getChildAt(i);
            if(i==index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active));
            }
            else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));
            }
        }

        if(index == onboardingAdapter.getItemCount()-1){
            buttonOnboardingAction.setText("Start");
        }
        else {
            buttonOnboardingAction.setText("Next");
        }

    }
}
