package com.app.smartuni;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.app.smartuni.base.BaseActivity;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class CreateTimeTableEventActivity extends BaseActivity {
  @BindView(R.id.edit_date) EditText mTimeTableDate;
  @BindView(R.id.edit_time) EditText mEventTime;
  @BindView(R.id.edit_lect_name) EditText mLecturer;
  @BindView(R.id.edit_course_code) EditText mCourseCode;
  @BindView(R.id.edit_hall) EditText mHall;
  private Integer mResults = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_time_table_event);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("Add Time Table Event");
    ButterKnife.bind(this);
    String mDate = getIntent().getStringExtra("SELCTION_DATE");
    if (mDate != null) {
      mTimeTableDate.setText(mDate);
    }
  }

  @OnClick({ R.id.edit_time, R.id.button_create, R.id.button_cancel })
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.edit_time:
        showTimePicker();
        break;

      case R.id.button_create:
        processSave();
        break;

      case R.id.button_cancel:
        finish();
        break;
    }
  }

  private void processSave() {
    if (isValidForm()) {
      processAddEvent();
    }
  }

  private boolean isValidForm() {
    clearValidations();
    if (mLecturer.getText().toString().isEmpty()) {
      showWarning("Lecturer Name Required!");
      mLecturer.setError("");
      return false;
    }

    if (mEventTime.getText().toString().isEmpty()) {
      showWarning("Event Time Required!");
      mEventTime.setError("");
      return false;
    }

    if (mCourseCode.getText().toString().isEmpty()) {
      showWarning("Course Code Required!");
      mCourseCode.setError("");
      return false;
    }

    if (mHall.getText().toString().isEmpty()) {
      showWarning("Hall Name Required!");
      mHall.setError("");
      return false;
    }
    return true;
  }

  private void clearValidations() {
    mLecturer.setError(null);
    mCourseCode.setError(null);
    mLecturer.setError(null);
    mCourseCode.setError(null);
    mHall.setError(null);
    mEventTime.setError(null);
  }

  private void showTimePicker() {
    Calendar mcurrentTime = Calendar.getInstance();
    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
    int minute = mcurrentTime.get(Calendar.MINUTE);
    TimePickerDialog mTimePicker;
    mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
        StringBuilder mTime = new StringBuilder();
        mTime.append(selectedHour);
        mTime.append(":");
        mTime.append(selectedMinute);
        mEventTime.setText(mTime.toString());
      }
    }, hour, minute, true);//Yes 24 hour time
    mTimePicker.setTitle("Select Time");
    mTimePicker.show();
  }

  private void processAddEvent() {
    if (isValidForm()) {
      String mDateString = mTimeTableDate.getText().toString().trim();
      String mTimeString = mEventTime.getText().toString().trim();
      String mLecturerString = mLecturer.getText().toString().trim();
      String mCourseCodeString = mCourseCode.getText().toString().trim();
      String mHallString = mHall.getText().toString().trim();

      showProgress();

      new Thread(new Runnable() {
        @Override public void run() {
          try {
            Connection connection = DbConnection.getConnect();
            Statement mStatement = connection.createStatement();
            String mquery =
                "INSERT INTO time_table (date,time,lecturer,hall,course) VALUES ('"
                    +
                    mDateString
                    + "','"
                    +
                    mTimeString
                    + "','"
                    +
                    mLecturerString
                    + "','"
                    +
                    mHallString
                    + "','"
                    +
                    mCourseCodeString
                    + "')";
            mResults = mStatement.executeUpdate(mquery);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
              @Override
              public void run() {
                hideProgress();
                if (mResults == 1) {
                  //showSuccess("Successful!");
                  finish();
                } else {
                  showError("Failed To Save The Event");
                }
              }
            });
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }).start();
    }
  }
}
