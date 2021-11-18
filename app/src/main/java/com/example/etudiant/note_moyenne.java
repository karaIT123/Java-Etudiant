package com.example.etudiant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class note_moyenne extends AppCompatActivity {
    ArrayList<list> data;
    ListArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_moyenne);

        voir_data();
        ListView listView = (ListView)findViewById(R.id.list);
        this.data = new ArrayList<list>();

        this.adapter = new ListArrayAdapter(this,R.layout.list_layout_note,data);
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

            String link = ("http://192.168.64.2/PHP/Android/note_moyenne.php");

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
            String [] a = x.replaceAll("\n","").split("/");
            for (String element:a){
                String [] b = element.split("@");
                String index = b[0];
                String first = b[1];
                //String second = b[2];
                //String fird = b[3];

                list ltemp = new list(index,first,"","");
                data.add(ltemp);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
