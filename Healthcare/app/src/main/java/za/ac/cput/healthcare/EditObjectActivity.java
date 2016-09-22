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

public class EditObjectActivity extends AppCompatActivity {

    private Button btnSubmit;
    private EditText txtId,txtName,txtSurname,txtCity,txtStreet,txtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_object);
        btnSubmit = (Button)findViewById(R.id.btnSubmitEdit);
        txtId = (EditText)findViewById(R.id.editEnterEditID);
        txtName = (EditText)findViewById(R.id.editEnterNewName);
        txtSurname = (EditText)findViewById(R.id.editEnterNewSurname);
        txtCity = (EditText)findViewById(R.id.editEnterNewCity);
        txtStreet = (EditText)findViewById(R.id.editEnterNewStreet);
        txtCode = (EditText)findViewById(R.id.editEnterNewCode);
    }

    public void onBtnSubmitEditClick(View view)
    {

    }

    private class EditClient extends AsyncTask<Void,Void,Void>
    {
        private String id, name, lastName,city,street,code;
        private int response;
        private Client client = new Client();
        private ClientAddress clientAddress = new ClientAddress();
        @Override
        protected void onPreExecute() {
            id = txtId.getText().toString();
            name = txtName.getText().toString();
            lastName = txtSurname.getText().toString();
            city = txtCity.getText().toString();
            street = txtStreet.getText().toString();
            code = txtCode.getText().toString();
            client.setName(name);
            client.setLastName(lastName);
            clientAddress.setStreet(street);
            clientAddress.setCity(city);
            clientAddress.setCode(code);
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection connection = null;
            String url = "http://lindor-ijaaz1995.boxfuse.io:8080/client/" + id;
            URL uri = null;
            JSONObject jsonObject = new JSONObject();
            JSONObject object = new JSONObject();
            //Set JSON values
            try {
                jsonObject.put("name",client.getName());
                jsonObject.put("price",client.getLastName());
                object.put("Street",clientAddress.getStreet());
                object.put("city",clientAddress.getCity());
                object.put("code",clientAddress.getCode());
                jsonObject.put("objAdress",object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                //set up URL
                uri = new URL(url);
                connection = (HttpURLConnection)uri.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(false);
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestMethod("PUT");
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
            if (response == 200)
            {
                Intent intent = new Intent(getApplicationContext(),MainMenuActivity.class);
                startActivity(intent);
            }
        }
    }
}
