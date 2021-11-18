package com.example.etudiant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements Serializable {

    private EditText user,pass;
    private Menu m;
    private Button compte,deconnection;
    private String info,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.info = this.id = "";
        this.user = (EditText)findViewById(R.id.user);
        this.pass = (EditText)findViewById(R.id.pass);
        this.compte = (Button)findViewById(R.id.compte);
        this.deconnection = (Button)findViewById(R.id.deconnection);
    }
//--------------------------MENU----------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);

        if(menu !=null){
            this.m = menu;
        }

        menu.add(Menu.NONE,Menu.FIRST + 0,0,"Créer un compte");
        menu.add(Menu.NONE,Menu.FIRST + 1,1,"Voir les activités");
        menu.add(Menu.NONE,Menu.FIRST + 2,2,"S'incrire a une activité");
        menu.add(Menu.NONE,Menu.FIRST + 3,3,"Proposer une activité");
        menu.add(Menu.NONE,Menu.FIRST + 4,4,"Noter une activité");
        menu.add(Menu.NONE,Menu.FIRST + 5,5,"Statitistique");
        menu.add(Menu.NONE,Menu.FIRST + 6,6,"Liste des inscription");
        menu.add(Menu.NONE,Menu.FIRST + 7,7,"Note moyenne");
        menu.add(Menu.NONE,Menu.FIRST + 8,8,"Etudiant par activite");

        hideMenu();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent i = null;
        switch (item.getItemId()){
            case Menu.FIRST:
                i = new Intent(getApplicationContext(),creer_compte.class);
                startActivity(i);
                break;
            case Menu.FIRST + 1:
                i = new Intent(getApplicationContext(),voir_activite.class);
                startActivity(i);
                break;
            case Menu.FIRST + 2:
                i = new Intent(getApplicationContext(),inscrire_activite.class);
                i.putExtra("id",id);
                startActivity(i);
                break;
            case Menu.FIRST + 3:
                i = new Intent(getApplicationContext(),proposer_activite.class);
                startActivity(i);
                break;
            case Menu.FIRST + 4:
                i = new Intent(getApplicationContext(),noter_activite.class);
                i.putExtra("id",id);
                startActivity(i);
                break;
            case Menu.FIRST + 5:
                i = new Intent(getApplicationContext(),statistique.class);
                startActivity(i);
                break;
            case Menu.FIRST + 6:
                i = new Intent(getApplicationContext(),liste_inscription.class);
                startActivity(i);
                break;
            case Menu.FIRST + 7:
                i = new Intent(getApplicationContext(),note_moyenne.class);
                startActivity(i);
                break;
            case Menu.FIRST + 8:
                i = new Intent(getApplicationContext(),etudiant_activite.class);
                startActivity(i);
                break;
        }
        return true;
    }

    private void hideMenu() {
        this.compte.setVisibility(View.INVISIBLE);
        this.deconnection.setVisibility(View.INVISIBLE);
        m.setGroupVisible(Menu.NONE,false);
        /*for(int i = 0; i < 8; i++)
        {
            MenuItem item = m.findItem(Menu.FIRST + i);
            item.setVisible(false);
        }*/

    }

    private void showMenu() {
        deconnection.setVisibility(View.VISIBLE);
        compte.setVisibility(View.VISIBLE);
        m.setGroupVisible(Menu.NONE,true);
        /*for(int i = 0; i < 8; i++)
        {
            MenuItem item = m.findItem(Menu.FIRST + i);
            item.setVisible(true);
        }*/
    }

    public void check(View view) {
        String username = user.getText().toString();
        String password = pass.getText().toString();

        dbWorker dbw = new dbWorker(this);
        dbw.execute(username,password);

        user.setText("");
        pass.setText("");
    }

    public void compte(View view) {
        Intent i = null;
        i = new Intent(getApplicationContext(),modifier_compte.class);
        i.putExtra("info",info);
        startActivity(i);
    }

    public void deconnection(View view) {
        hideMenu();
    }

    //--------------------------AsyncTask----------------------------
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
            this.ad.setTitle("Statut de connection");
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            String link = ("http://192.168.64.2/PHP/Android/login.php");

            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");

                OutputStream out = conn.getOutputStream();
                BufferedWriter bfw= new BufferedWriter(new OutputStreamWriter(out,"utf-8"));

                String msg = URLEncoder.encode("username","utf-8") + "=" +
                        URLEncoder.encode((String)objects[0],"utf-8") + "&" +
                        URLEncoder.encode("pass","utf-8") + "=" +
                        URLEncoder.encode((String)objects[1],"utf-8");

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

            if (o.toString().equals("error\n")){
                this.ad.setMessage("Erreur de connection");
                this.ad.show();
            }else{
                String x = (String) o;
                String[] y = x.split("/");
                //String z = y[1];
                info = y[1];

                String[] p = info.split(" ");
                id = p[0];
                showMenu();
            }
        }
    }
}
