package ro.upb.etti.stud.BulaiRadu_P3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ro.upb.etti.stud.BulaiRadu_P3.R;

public class Activity_Login extends AppCompatActivity {

    EditText login_Username;
    EditText login_Password;
    Button login_button_login;
    TextView login_TextView_register;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        login_Username = (EditText) findViewById(R.id.idlogin_edittext_username);
        login_Password = (EditText) findViewById(R.id.idlogin_edittext_password);
        login_button_login = (Button) findViewById(R.id.idlogin_button_login);
        login_TextView_register = (TextView) findViewById(R.id.idlogin_textview_register);

        login_TextView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Activity_Login.this, Activity_Register.class);
                startActivity(registerIntent);
            }
        });

        //db.InsertUtilizator("admin", "admin", "Admin");

        login_button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = login_Username.getText().toString().trim();
                String pwd = login_Password.getText().toString().trim();

                Boolean check_admin = db.checkUtilizatorAdmin(user, pwd);

                if ( check_admin == true) {
                    Toast.makeText(Activity_Login.this, "Logat ca ADMINISTRATOR!", Toast.LENGTH_SHORT).show();

                    Intent GoToMain = new Intent(Activity_Login.this, MainActivity.class);
                    startActivity(GoToMain);
                } else {

                    Boolean check = db.checkUtilizator(user, pwd);
                    if ( check == true) {
                        Toast.makeText(Activity_Login.this, "Logat ca Utilizator Client!", Toast.LENGTH_SHORT).show();
                        Intent GoToMain = new Intent(Activity_Login.this, MainActivity_Client.class);
                        startActivity(GoToMain);
                    } else {
                        Toast.makeText(Activity_Login.this, "Logare Esuata, verificati user/parola!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Login.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Doriti sa parasiti aplicatia?")
                .setCancelable(false)
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                })
                .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
