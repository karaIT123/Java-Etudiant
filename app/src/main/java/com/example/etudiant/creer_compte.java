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

public class creer_compte extends AppCompatActivity {
    private EditText nom;
    private EditText prenom;
    private EditText email;
    private EditText telephone;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);

        this.nom = (EditText)findViewById(R.id.nom);
        this.prenom = (EditText)findViewById(R.id.prenom);
        this.email = (EditText)findViewById(R.id.email);
        this.telephone = (EditText)findViewById(R.id.telephone);
        this.username = (EditText)findViewById(R.id.username);
        this.password = (EditText)findViewById(R.id.password);
    }

    public void creer_compte(View view) {
        String nom = this.nom.getText().toString();
        String prenom = this.prenom.getText().toString();
        String email = this.email.getText().toString();
        String telephone = this.telephone.getText().toString();
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();

        dbWorker dbw = new dbWorker(this);
        dbw.execute(nom,prenom,email,telephone,username,password);
    }

    public class dbWorker extends AsyncTask {
        private Context c;
        private AlertDialog ad;

        public dbWorker(Context c)
        {
            this.c = c;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.ad = new AlertDialog.Builder(this.c).create();
            this.ad.setTitle("Creation de compte");
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            String link = ("http://192.168.64.2/PHP/Android/creer_compte.php");

            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");

                OutputStream out = conn.getOutputStream();
                BufferedWriter bfw= new BufferedWriter(new OutputStreamWriter(out,"utf-8"));

                String msg = URLEncoder.encode("nom","utf-8") + "=" + URLEncoder.encode((String)objects[0],"utf-8")
                        + "&" +
                        URLEncoder.encode("prenom","utf-8") + "=" + URLEncoder.encode((String)objects[1],"utf-8")
                        + "&" +
                        URLEncoder.encode("email","utf-8") + "=" + URLEncoder.encode((String)objects[2],"utf-8")
                        + "&" +
                        URLEncoder.encode("telephone","utf-8") + "=" + URLEncoder.encode((String)objects[3],"utf-8")
                        + "&" +
                        URLEncoder.encode("username","utf-8") + "=" + URLEncoder.encode((String)objects[4],"utf-8")
                        + "&" +
                        URLEncoder.encode("password","utf-8") + "=" + URLEncoder.encode((String)objects[5],"utf-8");

                bfw.write(msg);
                bfw.flush();
                bfw.close();
                out.close();

                InputStream is = conn.getInputStream();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                String line;
                StringBuffer sbf = new StringBuffer();
                while ((line = bfr.readLine())!=null)
                {
                    sbf.append(line + "\n");
                }
                return sbf.toString();
            }
            catch (Exception e)
            {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            String x  = (String)o;
            String [] y = x.split("/");
            String z = y[1];

            if (y[0].equals("success")){
                this.ad.setMessage("Compte crée:\n" + z);
                this.ad.show();
                nom.setText("");
                prenom.setText("");
                email.setText("");
                telephone.setText("");
                username.setText("");
                password.setText("");
            }else{
                this.ad.setMessage("Echec de creation du compte");
                this.ad.show();
            }
        }
    }
}
