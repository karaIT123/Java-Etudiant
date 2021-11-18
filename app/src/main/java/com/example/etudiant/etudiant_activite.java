package com.example.etudiant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class etudiant_activite extends AppCompatActivity {
    ArrayList<list> data;
    ListArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant_activite);
        voir_data();

        ListView listView = (ListView)findViewById(R.id.list);
        this.data = new ArrayList<list>();

        this.adapter = new ListArrayAdapter(this,R.layout.list_layout_vertical,data);
        listView.setAdapter(adapter);
    }

    public void voir_data() {
        dbWorker dbw = new dbWorker();
        dbw.execute();
    }

    public class dbWorker extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            String link = ("http://192.168.64.2/PHP/Android/data_etudiant_activite.php");

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
            String [] a = x.replaceAll("\n","").split("@");
            for (String element:a){
                String [] b = element.split("/");
                String pn = b[0];
                String first = b[1];
                String second = b[2];
                String fird = b[3];

                list ltemp = new list(pn,first,second,fird);
                data.add(ltemp);
            }
            /*String [] b = element.split("/");
            String prenom = b[0];
            String nom = b[1];
            String first = b[2];
            String second = b[3];
            String fird = b[4];*/

            //list ltemp = new list();
            //data.add(ltemp);


            //String z = y[1];
            //int n = a.length;

            /*for (String element:y) {
                String [] s = element.split("#");
                String id = s[0];
                String nom = s[1];
                String prenom = s[2];
                String nomprenom = prenom + " " + nom;
                String email = s[3];
                String telephone = s[4];
                String username = s[5];
                String password = s[6];

                list ltemp = new list(nomprenom,email,telephone,username);
                //data.add(ltemp);
            }*/
            adapter.notifyDataSetChanged();
        }
    }
}