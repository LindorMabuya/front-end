package za.ac.cput.healthcare;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ReadByIDActivity extends AppCompatActivity {

    private Button btnSubmit, btnHome;
    private TextView txtShow;
    private EditText txtGetID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_by_id);

        btnSubmit = (Button) findViewById(R.id.btnReadByID);
        btnHome = (Button) findViewById(R.id.btnReadByIDHome);
        txtGetID = (EditText) findViewById(R.id.editEnterReadID);
        txtShow = (TextView) findViewById(R.id.txtReadByID);
    }

    public void onReadByIDClick(View view) {

        new ReadByID().execute();
    }

    public void onBtnHomeClick(View view) {
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
        finish();
    }


    private class ReadByID extends AsyncTask<Void, Void, Void> {
        private String id, result;
        private int httpResult;

        @Override
        protected void onPreExecute() {
            id = txtGetID.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://lindor-ijaaz1995.boxfuse.io:8080/client/" + id;
            HttpURLConnection connection = null;
            URL uri = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                //set up URL
                uri = new URL(url);
                connection = (HttpURLConnection) uri.openConnection();
                connection.setDoOutput(false);
                connection.setDoInput(true);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestMethod("GET");

                //Reader
                httpResult = connection.getResponseCode();
                if (httpResult == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    reader.close();
                    result = stringBuilder.toString();
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(httpResult == 200)
            {
                txtShow.setText(result);
            }
        }
    }
}
