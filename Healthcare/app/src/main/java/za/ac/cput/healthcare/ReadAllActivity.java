package za.ac.cput.healthcare;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ReadAllActivity extends AppCompatActivity {
    private Button home;
    private TextView allEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_all);
        home = (Button)findViewById(R.id.btnReadAllHome);
        allEntries = (TextView)findViewById(R.id.txtReadAll);
        allEntries.setMovementMethod(new ScrollingMovementMethod());

        new ReadAllClients().execute();
    }

    public void onBtnReadAllHomeClick(View view)
    {
        Intent i = new Intent(this,MainMenuActivity.class);
        startActivity(i);
    }

    private class ReadAllClients extends AsyncTask<Void,Void,Void>
    {
        private String result;
        private int httpResult;
        @Override
        protected void onPreExecute() {
            result ="";
            httpResult = 0;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://lindor-ijaaz1995.boxfuse.io:8080/clients/";
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
                if(httpResult == 200)
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
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
            }  finally {
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
                allEntries.setText(result);
            }
        }
    }
}
