package com.example.etudiant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class voir_activite extends AppCompatActivity {
    ArrayList<list> data;
    ListArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_activite);

        voir_activite();

        ListView listView = (ListView)findViewById(R.id.list);
        this.data = new ArrayList<list>();

        //data.add(new list("","","",""));

         this.adapter = new ListArrayAdapter(this,R.layout.list_layout,data);
        listView.setAdapter(adapter);
    }

    public void voir_activite() {
        /*String nom = this.nom.getText().toString();
        String prenom = this.prenom.getText().toString();
        String email = this.email.getText().toString();
        String telephone = this.telephone.getText().toString();
        String username = this.username.getText().toString();
        String password = this.password.getText().toString().replaceAll("\n","");;

        String id = idetudiant;*/
        dbWorker dbw = new dbWorker(this);
        dbw.execute();
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
            this.ad.setTitle("Liste des activites");
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            String link = ("http://192.168.64.2/PHP/Android/data_activite.php");

            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");

                OutputStream out = conn.getOutputStream();
                BufferedWriter bfw= new BufferedWriter(new OutputStreamWriter(out,"utf-8"));

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
            String [] y = x.replaceAll("\n","").split("/");
            //String z = y[1];
            int n = y.length;

            for (String element:y) {
                String [] s = element.split("@");
                String titre = s[0];
                String desciption = s[1];
                String datedebut = s[2];
                String datefin = s[3];
                String prix = s[4];
                String id = s[5];

                list ltemp = new list(titre,datedebut,datefin,prix);
                data.add(ltemp);
            }
            adapter.notifyDataSetChanged();
        }
    }
}