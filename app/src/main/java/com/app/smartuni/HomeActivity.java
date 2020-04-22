package com.app.smartuni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.app.smartuni.base.BaseActivity;

public class HomeActivity extends BaseActivity {

  @BindView(R.id.home_events_card) CardView events;
  @BindView(R.id.home_time_table_card) CardView timeTable;
  @BindView(R.id.home_generate_cv_card) CardView genrateCv;
  @BindView(R.id.home_logout_card) CardView logout;
  //@BindView(R.id.home_events_card) CardView events;
  //@BindView(R.id.home_events_card) CardView events;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ButterKnife.bind(this);
  }

  @OnClick({ R.id.home_events_card, R.id.home_logout_card })
  public void submit(View view) {
    switch (view.getId()) {
      case R.id.home_events_card:
        showEvents();
        break;
      case R.id.home_logout_card: {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Editor mEditor = prefs.edit();
        mEditor.clear();
        mEditor.apply();
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
      }
    }
  }

  private void showEvents() {
    startActivity(new Intent(getApplicationContext(), EventActivity.class));

  }
}
