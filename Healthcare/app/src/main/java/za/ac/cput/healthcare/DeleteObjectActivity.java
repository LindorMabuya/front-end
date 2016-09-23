package za.ac.cput.healthcare;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DeleteObjectActivity extends AppCompatActivity {

    private Button btnSubmit;
    private EditText txtIdToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_object);
        btnSubmit = (Button)findViewById(R.id.btnSubmitDelete);
        txtIdToDelete = (EditText) findViewById(R.id.editEnterDeleteID);
    }

    public void onBtnSubmitDeleteClick(View view)
    {
        new DeleteClient().execute();
    }

    private class DeleteClient extends AsyncTask<Void,Void,Void>
    {
        private String id;
        private int httResponse;

        @Override
        protected void onPreExecute() {
            id = txtIdToDelete.getText().toString();
            httResponse = 0;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://lindor-ijaaz1995.boxfuse.io:8080/client/" + id;
            HttpURLConnection connection = null;
            URL uri = null;
            try {
                //set up URL
                uri = new URL(url);
                connection = (HttpURLConnection) uri.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(false);
                connection.setRequestMethod("DELETE");
                httResponse = connection.getResponseCode();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           if(httResponse == 204)
           {
               Intent intent = new Intent(getApplicationContext(),MainMenuActivity.class);
               startActivity(intent);
               finish();
           }
        }
    }
}
