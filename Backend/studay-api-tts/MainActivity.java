import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if (!text.isEmpty()) {
                    new SynthesizeSpeechTask().execute(text);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class SynthesizeSpeechTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... texts) {
            String response = "";
            try {
                // Ganti <YOUR_SERVER_IP> dengan IP server Anda
                URL url = new URL("http://10.0.2.2:3000/synthesize");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Mengirim data JSON ke server
                String jsonInputString = "{\"text\": \"" + texts[0] + "\"}";
                try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                    wr.writeBytes(jsonInputString);
                    wr.flush();
                }

                // Membaca respons dari server
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Menutup koneksi
                in.close();
                conn.disconnect();

                response = content.toString();
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Menampilkan respons ke pengguna atau melakukan sesuatu dengan data
            Toast.makeText(MainActivity.this, "Response: " + result, Toast.LENGTH_SHORT).show();
        }
    }
}