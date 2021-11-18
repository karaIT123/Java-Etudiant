package com.example.etudiant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

public class inscrire_activite extends AppCompatActivity {
    private Intent i;
    private int selected;
    private String idetudiant,idactivite;
    ArrayList<String> listof;
    ArrayList<list> data;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscrire_activite);

        voir_activite();
        ListView l = (ListView) findViewById(R.id.list_inscription);
        this.selected  = 0;
        this.idetudiant = (String)getIntent().getSerializableExtra("id");
        this.listof = new ArrayList<String>();
        this.data = new ArrayList<list>();

        final AlertDialog.Builder ab = new AlertDialog.Builder(this);
        //final String[] tab = {"z","a","t"};
        ab.setTitle("Insciption");
        ab.setPositiveButton("S'inscrire", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inscrire_activite();
            }
        });
        ab.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        this.adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listof);
        l.setAdapter(adapter);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
                idactivite = data.get(selected).getFirst();
                ab.setMessage("Voulez-vous vous inscrire a l'activité: \n" + "'" + ((TextView)view).getText().toString()+ "' ?");
                AlertDialog alert = ab.create();
                alert.show();

            }
        });
    }

    public void inscrire_activite(){
        dbWorker dbw = new dbWorker(this);
        dbw.execute(idetudiant,idactivite);
    }

    public void voir_activite() {
        dbWorkerData dbwdt = new dbWorkerData(this);
        dbwdt.execute();
    }

    public class dbWorkerData extends AsyncTask {
        private Context c;
        public dbWorkerData(Context c)
        {
            this.c = c;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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

                list ltemp = new list(titre,id);
                data.add(ltemp);

            }
            for (list e:data
            ) {
                listof.add(e.getName());
            }
            adapter.notifyDataSetChanged();
        }
    }

    public class dbWorker extends AsyncTask {
        private Context c;

        public dbWorker(Context c)
        {
            this.c = c;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            String link = ("http://192.168.64.2/PHP/Android/inscrire_activite.php");

            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");

                OutputStream out = conn.getOutputStream();
                BufferedWriter bfw= new BufferedWriter(new OutputStreamWriter(out,"utf-8"));

                String msg = URLEncoder.encode("idetudiant","utf-8") + "=" + URLEncoder.encode((String)objects[0],"utf-8") + "&" +
                        URLEncoder.encode("idactivite","utf-8") + "=" + URLEncoder.encode((String)objects[1],"utf-8");

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

            String x = (String)o;
            Toast.makeText(c,x.replaceAll("\n",""),Toast.LENGTH_LONG).show();
        }
    }
}