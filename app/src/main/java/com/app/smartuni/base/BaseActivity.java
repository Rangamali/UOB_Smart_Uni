package com.app.smartuni.base;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.app.smartuni.R;
import es.dmoral.toasty.Toasty;

public class BaseActivity extends AppCompatActivity {

  private Dialog mProgressDialog;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mProgressDialog = new Dialog(this);
    mProgressDialog.setContentView(R.layout.progress_dialog);
    mProgressDialog.setCancelable(false);
    mProgressDialog.getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
  }

  public void showProgress() {
    mProgressDialog.show();
  }

  public void hideProgress() {
    mProgressDialog.hide();
  }

  public void showError(String message) {
    Toasty.error(this, message, Toast.LENGTH_SHORT, true).show();
  }

  public void showWarning(String message) {
    Toasty.warning(this, message, Toast.LENGTH_SHORT, true).show();
  }

  public void showSuccess(String message) {
    Toasty.success(this, message, Toast.LENGTH_SHORT, true).show();
  }
}
