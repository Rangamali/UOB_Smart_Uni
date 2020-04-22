package com.app.smartuni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import com.app.smartuni.base.BaseActivity;

public class HomeActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    Boolean is_admin = prefs.getBoolean("is_admin", false);
    if (is_admin) {
      setContentView(R.layout.activity_admin_home);
    } else {
      setContentView(R.layout.activity_home);
    }

    ButterKnife.bind(this);
  }

  @Optional
  @OnClick({
      R.id.home_events_card, R.id.home_logout_card, R.id.home_add_event_card,
      R.id.home_time_table_card,
  })
  public void submit(View view) {
    switch (view.getId()) {
      case R.id.home_events_card:
        showEvents();
        break;

      case R.id.home_add_event_card:
        addEvents();
        break;

      case R.id.home_time_table_card:
        addTimeTable();
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

  private void addTimeTable() {
    startActivity(new Intent(getApplicationContext(), AddTimeTableActivity.class));
  }

  private void addEvents() {
    startActivity(new Intent(getApplicationContext(), AddEventActivity.class));
  }

  private void showEvents() {
    startActivity(new Intent(getApplicationContext(), EventActivity.class));
  }
}
