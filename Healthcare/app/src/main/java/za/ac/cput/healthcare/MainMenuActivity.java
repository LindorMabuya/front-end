package za.ac.cput.healthcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onBtnCreateClick(View view) {
        Intent i = new Intent(this,CreateObjectActivity.class);
        startActivity(i);
        finish();
    }

    public void onBtnReadByIDClick(View view) {
        Intent i = new Intent(this,ReadByIDActivity.class);
        startActivity(i);
        finish();
    }

    public void onBtnReadAllClick(View view) {
        Intent i = new Intent(this,ReadAllActivity.class);
        startActivity(i);
        finish();
    }

    public void obBtnEditClick(View view) {
        Intent i = new Intent(this,EditObjectActivity.class);
        startActivity(i);
        finish();
    }

    public void onBtnDeleteClick(View view) {
        Intent i = new Intent(this,DeleteObjectActivity.class);
        startActivity(i);
        finish();
    }
}
