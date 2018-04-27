package com.example.thomas.projetiot;

        import org.json.JSONObject;

        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

        private static final String SERVICE_URL = "http://148.60.36.198:8000/rest/Chenlillard/post";
        private static final String TAG = "AndroidRESTClientActivity";
        /** Called when the activity is first created. */
        private WebServiceTask wstPost2;
    private WebServiceTask wstPost1;

    private boolean etat = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //On relie l'intent
        Intent intent = getIntent();


        // Connection REST
        Button start = (Button)findViewById(R.id.start);
        start.setOnClickListener(START);
        //stop
        Button stop = (Button)findViewById(R.id.stop);
        stop.setOnClickListener(STOP);
        //Changer
    }

    //reaction aux boutons Start et Stop.
    public View.OnClickListener START = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            etat = true;
            postData(v);
        }
    };
    public View.OnClickListener STOP = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            etat = false;
            postData(v);
        }
    };



    public void retrieveSampleData(View vw) {
                String sampleURL = SERVICE_URL + "/sample";
                WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, this, "GETting data...");

                wst.execute(new String[] { sampleURL });
        }
        public void clearControls(View vw) {
                EditText vitesse = (EditText) findViewById(R.id.vitesse);
                EditText ordre = (EditText) findViewById(R.id.ordre);
                vitesse.setText("");
                ordre.setText("");
        }



          public void postConnection(){
               WebServiceTask wstPost1 = new WebServiceTask(WebServiceTask.GET_TASK, this, "GETting data...");

               wstPost2.execute(new String[]{ SERVICE_URL });

         }
        //post et ajout des valeurs dans le tableau 'Json'
        public void postData(View v) {
                WebServiceTask wstPost2 = new WebServiceTask(WebServiceTask.GET_TASK, this, "GETting data...");

            //on récupere les EditText choisi par l'utilisateur
                EditText Mavitesse = (EditText) findViewById(R.id.vitesse);
                EditText Monordre = (EditText) findViewById(R.id.ordre);

                //On convertit chaque EditText
                String vit = Mavitesse.getText().toString();
                String ord = Monordre.getText().toString();
                String etatChen = "";

                //Selon la valeur de l'état : T ou F, on envoie une String dans le tableau 'Json'
                if (vit.equals("") || ord.equals("") ) {
                        Toast.makeText(this, "Please enter in all required fields.",
                                Toast.LENGTH_LONG).show();
                        return;
                }

                //Ajout des variables dans le fichier JSON (array "Json" dans notre cas)
                wstPost2.addNameValuePair("vitesse", vit);
                wstPost2.addNameValuePair("ordre", ord);

                //Ajout de la variable etat dans le tableau 'Json'
                if (etat==false) {
                    wstPost2.addNameValuePair("state","false");
                    Toast.makeText(this, "false",
                            Toast.LENGTH_LONG).show();
                }
                else if(etat==true){
                    wstPost2.addNameValuePair("state","true");
                    Toast.makeText(this, "true",
                            Toast.LENGTH_LONG).show();
                }
                // the passed String is the URL we will POST to
                wstPost2.execute(new String[] { SERVICE_URL });
        }



        @SuppressLint("LongLogTag")
        public void handleResponse(String response) {

                EditText vitesse = (EditText) findViewById(R.id.vitesse);
                EditText ordre = (EditText) findViewById(R.id.ordre);
                vitesse.setText("");
                ordre.setText("");
                try {
                        JSONObject jso = new JSONObject(response);
                        String vit = jso.getString("vitesse chen");
                        String ord = jso.getString("ord chen");

                        vitesse.setText(vit);
                        ordre.setText(ord);
                } catch (Exception e) {
                        Log.e(TAG, e.getLocalizedMessage(), e);
                }
        }
        public void hideKeyboard() {
                InputMethodManager inputManager = (InputMethodManager) MainActivity.this
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }


}





