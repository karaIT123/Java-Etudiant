package com.example.etudiant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class proposer_activite extends AppCompatActivity {

    private EditText titre,description,dateDebut,dateFin,prix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposer_activite);

        this.titre = (EditText)findViewById(R.id.titre);
        this.description = (EditText)findViewById(R.id.description);
        this.dateDebut = (EditText) findViewById((R.id.dateDebut));
        this.dateFin = (EditText)findViewById(R.id.dateFin);
        this.prix = (EditText)findViewById(R.id.prix);
    }

    public void proposer(View view) {
        String titre = this.titre.getText().toString();
        String description = this.description.getText().toString();
        String dateDebut = this.dateDebut.getText().toString();
        String dateFin = this.dateFin.getText().toString();
        String prix = this.prix.getText().toString();

        dbWorker dbw = new dbWorker(this);
        dbw.execute(titre,description,dateDebut,dateFin,prix);
    }

    public class dbWorker extends AsyncTask {
        private Context c;
        private AlertDialog ad;

        public dbWorker(Context c) {
            this.c = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.ad = new AlertDialog.Builder(this.c).create();
            this.ad.setTitle("Proposition d'activite");
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            String link = ("http://192.168.64.2/PHP/Android/proposer_activite.php");

            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");

                OutputStream out = conn.getOutputStream();
                BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));

                String msg = URLEncoder.encode("titre", "utf-8") + "=" + URLEncoder.encode((String) objects[0], "utf-8")
                        + "&" +
                        URLEncoder.encode("description", "utf-8") + "=" + URLEncoder.encode((String) objects[1], "utf-8")
                        + "&" +
                        URLEncoder.encode("datedebut", "utf-8") + "=" + URLEncoder.encode((String) objects[2], "utf-8")
                        + "&" +
                        URLEncoder.encode("datefin", "utf-8") + "=" + URLEncoder.encode((String) objects[3], "utf-8")
                        + "&" +
                        URLEncoder.encode("prix", "utf-8") + "=" + URLEncoder.encode((String) objects[4], "utf-8");

                bfw.write(msg);
                bfw.flush();
                bfw.close();
                out.close();

                InputStream is = conn.getInputStream();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                String line;
                StringBuffer sbf = new StringBuffer();
                while ((line = bfr.readLine()) != null) {
                    sbf.append(line + "\n");
                }
                return sbf.toString();
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            String x = (String) o;
            String[] y = x.split("@");
            String z = y[1];

            if (y[0].equals("success")) {
                this.ad.setMessage("Activité proposé:\n" + z);
                this.ad.show();
                titre.setText("");
                description.setText("");
                dateDebut.setText("");
                dateFin.setText("");
                prix.setText("");
            } else {
                this.ad.setMessage("Echec de creation du compte");
                this.ad.show();
            }
        }
    }
}