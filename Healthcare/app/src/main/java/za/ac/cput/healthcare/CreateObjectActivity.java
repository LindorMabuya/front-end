package za.ac.cput.healthcare;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import za.ac.cput.healthcare.domain.Client;
import za.ac.cput.healthcare.domain.ClientAddress;

public class CreateObjectActivity extends AppCompatActivity {

    private Button btnSubmit;
    private EditText txtName, txtSurname,txtStreet,txtCity,txtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_object);
        btnSubmit = (Button)findViewById(R.id.btnSubmitCreate);
        txtName = (EditText)findViewById(R.id.editFirstName);
        txtSurname = (EditText)findViewById(R.id.editLastName);
        txtStreet = (EditText)findViewById(R.id.editStreet);
        txtCity = (EditText)findViewById(R.id.editEnterCity);
        txtCode = (EditText)findViewById(R.id.editEnterCode);
    }

    public void onBtnSubmitCreateClick(View view)
    {
        new CreateClient().execute();
    }

    private class CreateClient extends AsyncTask<Void,Void,Void>
    {
        private Client client = new Client();
        private ClientAddress address = new ClientAddress();
        private String name, surname,street,city,code;
        private int response;
        @Override
        protected void onPreExecute() {
            name = txtName.getText().toString();
            surname = txtSurname.getText().toString();
            street = txtStreet.getText().toString();
            city = txtCity.getText().toString();
            code = txtCode.getText().toString();

            client.setName(name);
            client.setLastName(surname);
            address.setStreet(street);
            address.setCity(city);
            address.setCode(code);
            client.setObjAdress(address);
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection connection = null;
            String url = "http://lindor-ijaaz1995.boxfuse.io:8080/client/";
            URL uri = null;
            JSONObject jsonObject = new JSONObject();
            JSONObject object = new JSONObject();
            //Set JSON values
            try {
                jsonObject.put("name",client.getName());
                jsonObject.put("lastName",client.getLastName());
                object.put("Street",address.getStreet());
                object.put("city",address.getCity());
                object.put("code",address.getCode());
                jsonObject.put("objAdress",object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Open connection
            try {
                //set up URL
                uri = new URL(url);
                connection = (HttpURLConnection)uri.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(false);
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestMethod("POST");
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

                //Write Data
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();
                response = connection.getResponseCode();
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
            if(response == 201)
            {
                Intent intent = new Intent(getApplicationContext(),MainMenuActivity.class);
                startActivity(intent);
            }
        }
    }
}
