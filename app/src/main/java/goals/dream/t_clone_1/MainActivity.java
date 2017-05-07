package goals.dream.t_clone_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import goals.dream.t_clone_1.R;
import goals.dream.t_clone_1.SettingsActivity;

import static android.R.attr.defaultValue;
import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static goals.dream.t_clone_1.R.id.tv_show_x;

public class MainActivity extends AppCompatActivity {

     String email_phone_key = "xxxxxx@gmail.com";
    String password_phone_key = "password_xxx";

    String email;
    String password;
    String zipcode;

    int current_step;
    int current_step_count;

    int timer_main_delay;
    int timer_sub_delay;
    boolean auto_post = false;
    long post_started_time;

    String title;
    String body;

    String location;
    String geographic_area;
    String postal_code;

    String category;
    String sub_category;

    String employment_type;
    String remuneration;

    String send_line_to_log;
    String next_ad_post_id;


    TextView my_text_log;
    EditText my_row_id;
    WebView wv1;

    Button btn_start_main_timer;
    Button btn_click_counter;
    Button btn_read_nextad_timer_main;
    Button btn_timer_sub;


    //SharedPreferences========================================================
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String key_1 = "k1";
    public static final String key_2 = "k2";
    public static final String key_3 = "k3";
    //can also use integers
    SharedPreferences sharedpreferences;
//SharedPreferences========================================================


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//SharedPreferences========================================================
        //sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//SharedPreferences========================================================

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string_hold_it = "xxx";
                Snackbar.make(view, string_hold_it, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Action", null).show();
            }

        });
    }
//


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//icons
        if (id == R.id.icon_1) {
            Toast.makeText(this, "icon_1", Toast.LENGTH_SHORT).show();
            Intent start_act_home = new Intent(this,MainActivity.class);
            startActivity(start_act_home);
            //start_act_2.putExtra(EXTRA_MESSAGE.message);
        }
        if (id == R.id.icon_2) {
            Intent start_act_2 = new Intent(this,Main2Activity.class);
            startActivity(start_act_2);
            //start_act_2.putExtra(EXTRA_MESSAGE.message);
            Toast.makeText(this, "icon_2", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.icon_3) {
            Toast.makeText(this, "icon_3", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.icon_4) {
            Toast.makeText(this, "icon_4", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.icon_5) {
            Toast.makeText(this, "icon_5", Toast.LENGTH_SHORT).show();
        }

        //menu
        if (id == R.id.menu_1) {
            Toast.makeText(this, "menu_1", Toast.LENGTH_SHORT).show();
            //starts the settings activity
            Intent my_intent=new Intent(this,SettingsActivity.class);
            startActivity(my_intent);
            //
        }
        if (id == R.id.menu_2) {
            Toast.makeText(this, "menu_2", Toast.LENGTH_SHORT).show();
            //
        }
        if (id == R.id.menu_3) {
            Toast.makeText(this, "menu_3", Toast.LENGTH_SHORT).show();
            //
        }
        if (id == R.id.menu_4) {
            Toast.makeText(this, "menu_4", Toast.LENGTH_SHORT).show();
            //
        }
        if (id == R.id.menu_5) {
            Toast.makeText(this, "menu_5", Toast.LENGTH_SHORT).show();
            //
        }
        return super.onOptionsItemSelected(item);
}

    //button clicks------------------------------------------------------------------
    public void buttonOnClick(View view) {
        int the_id = view.getId();
        if (the_id == R.id.b_1) {

            Toast.makeText(this, "b_1", Toast.LENGTH_SHORT).show();
        }
        if (the_id == R.id.b_2) {
          Toast.makeText(this, "b_2", Toast.LENGTH_SHORT).show();
        }
        if (the_id == R.id.b_3) {


            //read_nextad_post();
            new read_row_json().execute(); //pass it the email and password from here duh
            //pass it the params here so you can fucking reuse it !!
            //like new read_row_json().execute(url_remote,email_remote,password_remote);


           TextView tv_show_x = (TextView)findViewById(R.id.tv_show_x);
            tv_show_x.setMovementMethod(new ScrollingMovementMethod());
           tv_show_x.append("555"+"\n"+global_x+"\n"+email+"\n"+password+"\n"+zipcode);
            //Toast.makeText(this, "b_3", Toast.LENGTH_SHORT).show();
        }

    }

String global_x = "global";
    //need internet permissions
    //add jsoup.jar to folder you can create called libs(resides next to app folder)
    public class read_row_json extends AsyncTask<Void,Void,Void> {
        String words;
        //String sql_row_id = my_row_id.getText().toString();
        String sql_row_id = "125";
        @Override
        protected Void doInBackground(Void... params) {
            try {
Document doc = Jsoup.connect("http://www.dreamgoals.info/cl_post/read_row_json.php?id="+sql_row_id+"&email="+"taiwanskout@gmail.com").get();
//Document doc = Jsoup.connect("http://www.dreamgoals.info/craig").get();

                words = doc.text();
                global_x = words;
            } catch (IOException e){e.printStackTrace();}return null;}

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //my_text_log.setText(words);

            try {
                JSONObject mainObject = new JSONObject(words);

                email = mainObject.getString("email");
                password = mainObject.getString("password");
                zipcode = mainObject.getString("zip_code"); // has an underscore

            } catch (JSONException e) {e.printStackTrace();}

        }
    }


}
//notes

/* ....

*
*
*
*/
