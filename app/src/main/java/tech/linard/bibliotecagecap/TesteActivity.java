package tech.linard.bibliotecagecap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;

public class TesteActivity extends AppCompatActivity implements View.OnClickListener {
    final String BASE_URL= "https://www.googleapis.com/books/v1/volumes?";
    final String QUERY_PARM = "q";

    Button mPesquisar;
    EditText mISBN;

    private NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        mPesquisar = findViewById(R.id.button_pesquisar);
        mPesquisar.setOnClickListener(this);
        mISBN = findViewById(R.id.edit_isbn);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            findViewById(R.id.button_pesquisar).setEnabled(false);
        } else {
            findViewById(R.id.button_pesquisar).setEnabled(true);
        }

    }

    private String preparaConsulta(String isbn) {
        if (networkInfo != null && networkInfo.isConnected()) {
            if (!TextUtils.isEmpty(isbn)) {
                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARM, isbn)
                        .build();
                return builtUri.toString();
            }
        }
        return null;
    }

    private void performSearch(String url) {
        if (url != null) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO: Handle Sucess
                        try {
                            List<Volume> volumes = UtilJson.converteJsonEmVolume(response);
                            Toast.makeText(TesteActivity.this, "OK!", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: Handle error
                        Toast.makeText(TesteActivity.this, "Erro!", Toast.LENGTH_SHORT).show();

                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_pesquisar:
                performSearch(preparaConsulta(mISBN.getText().toString()));
                break;
        }
    }
}
