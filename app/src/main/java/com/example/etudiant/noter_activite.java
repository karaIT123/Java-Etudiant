package com.example.etudiant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class noter_activite extends AppCompatActivity implements Dialogue.DialogueListener {
    private Intent i;
    private String note;
    private String idetudiant,idactivite;
    ArrayList<String> listof;
    ArrayList<list> data;
    ListView l;
    //ArrayAdapter adapter;

    ListArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noter_activite);


        this.l = (ListView) findViewById(R.id.list_inscription);
        this.idetudiant = (String)getIntent().getSerializableExtra("id");
        this.listof = new ArrayList<String>();
        this.data = new ArrayList<list>();
        /*this.adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listof);*/

        this.adapter = new ListArrayAdapter(this,R.layout.list_layout_note,data);
        l.setAdapter(adapter);

        voir_activite();

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String [] note = {"Passable","Pas mal"};

                TextView x = (TextView) view.findViewById(R.id.datanom);

                String test = x.getText().toString();
                for(int i = 0;i < data.size();i++){
                    if(test == data.get(i).getName()){
                        idactivite =  data.get(i).getSecond();
                    }
                }
                Dialogue dialogue = new Dialogue();
                dialogue.show(getSupportFragmentManager(),"TEST");

            }
        });
    }

    public void notez_activite(){
        //EditText indexDecimal = (EditText)findViewById(R.id.indexDecimal);
        //int note = Integer.getInteger(indexDecimal.getText().toString());
        dbWorker dbw = new dbWorker(this);
        dbw.execute(idetudiant,idactivite,note);
    }

    public void voir_activite() {
        dbWorkerData dbwdt = new dbWorkerData(this);
        dbwdt.execute(idetudiant);
    }

    @Override
    public void getText(String n) {
        //String titre = t;
        note = n;
        notez_activite();
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

            String link = ("http://192.168.64.2/PHP/Android/data_note_activite.php");

            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");

                OutputStream out = conn.getOutputStream();
                BufferedWriter bfw= new BufferedWriter(new OutputStreamWriter(out,"utf-8"));

                String msg = URLEncoder.encode("id","utf-8") + "=" +
                        URLEncoder.encode((String)objects[0]);

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
            String [] y = x.replaceAll("\n","").split("/");
            //String z = y[1];
            int n = y.length;

            for (String element:y) {
                String [] s = element.split("@");
                String titre = s[0];
                String id = s[1];
                String note = s[2];
                /*String datefin = s[3];
                String prix = s[4];
                String description = s[5];*/

                list ltemp = new list(titre,note,id,"");
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

            String link = ("http://192.168.64.2/PHP/Android/inserer_note.php");

            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");

                OutputStream out = conn.getOutputStream();
                BufferedWriter bfw= new BufferedWriter(new OutputStreamWriter(out,"utf-8"));

                String msg = URLEncoder.encode("idetudiant","utf-8") + "=" + URLEncoder.encode((String)objects[0],"utf-8") + "&" +
                        URLEncoder.encode("idactivite","utf-8") + "=" + URLEncoder.encode((String)objects[1],"utf-8") + "&" +
                        URLEncoder.encode("note","utf-8") + "=" + URLEncoder.encode((String)objects[2],"utf-8");

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
            //Toast.makeText(c,x.replaceAll("\n",""),Toast.LENGTH_LONG).show();
            Toast.makeText(c,"succes",Toast.LENGTH_LONG).show();
            //voir_activite();
            adapter.notifyDataSetChanged();
        }
    }

}


