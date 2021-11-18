package com.example.etudiant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

public class modifier_compte extends AppCompatActivity {
    private EditText nom,prenom,email,telephone,username,password;
    private Intent i;
    private String idetudiant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_compte);

        this.nom = (EditText)findViewById(R.id.nom);
        this.prenom = (EditText)findViewById(R.id.prenom);
        this.email = (EditText)findViewById(R.id.email);
        this.telephone = (EditText)findViewById(R.id.telephone);
        this.username = (EditText)findViewById(R.id.username);
        this.password = (EditText)findViewById(R.id.password);

        String inf = (String)getIntent().getSerializableExtra("info");
        String [] info = inf.split(" ");

        this.nom.setText(info[1]);
        this.prenom.setText(info[2]);
        this.email.setText(info[3]);
        this.telephone.setText(info[4]);
        this.username.setText(info[5]);
        this.password.setText(info[6]);
        idetudiant = info[0];
    }

    public void modifer_compte(View view) {
        String nom = this.nom.getText().toString();
        String prenom = this.prenom.getText().toString();
        String email = this.email.getText().toString();
        String telephone = this.telephone.getText().toString();
        String username = this.username.getText().toString();
        String password = this.password.getText().toString().replaceAll("\n","");;

        String id = idetudiant;
        dbWorker dbw = new dbWorker(this);
        dbw.execute(nom,prenom,email,telephone,username,password,id);
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
            this.ad.setTitle("Mise a jour du compte");
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            String link = ("http://192.168.64.2/PHP/Android/update_compte.php");

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
                        URLEncoder.encode("password","utf-8") + "=" + URLEncoder.encode((String)objects[5],"utf-8")
                        + "&" +
                        URLEncoder.encode("idetudiant","utf-8") + "=" + URLEncoder.encode((String)objects[6],"utf-8");

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
            /*String [] y = x.split("/");
            String z = y[1];*/

            if (x.equals("success\n")){
                this.ad.setMessage("Mise a jour effectuée");
                this.ad.show();
                /*nom.setText("");
                prenom.setText("");
                email.setText("");
                telephone.setText("");
                username.setText("");
                password.setText("");*/
            }else{
                this.ad.setMessage("Echec de la mise a jour du compte");
                this.ad.show();
            }
        }
    }
}