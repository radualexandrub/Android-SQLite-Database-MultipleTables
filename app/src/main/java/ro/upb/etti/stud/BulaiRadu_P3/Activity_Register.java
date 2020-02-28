package ro.upb.etti.stud.BulaiRadu_P3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Register extends AppCompatActivity {

    DatabaseHelper db;
    EditText register_Username;
    EditText register_Password;
    EditText register_Password_confirm;
    Button register_button_register;
    TextView register_TextView_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        register_Username = (EditText) findViewById(R.id.idregister_edittext_username);
        register_Password = (EditText) findViewById(R.id.idregister_edittext_password);
        register_Password_confirm = (EditText) findViewById(R.id.idregister_edittext_confirm_password);
        register_button_register = (Button) findViewById(R.id.idregister_button_register);
        register_TextView_login = (TextView) findViewById(R.id.idregister_textview_login_back);

        register_TextView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(Activity_Register.this, Activity_Login.class);
                startActivity(loginIntent);
            }
        });

        register_button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = register_Username.getText().toString().trim();
                String pwd = register_Password.getText().toString().trim();
                String pwd_conf = register_Password_confirm.getText().toString().trim();

                if (user.isEmpty() || pwd.isEmpty() || pwd_conf.isEmpty()) {
                    Toast.makeText(Activity_Register.this, "Completati toate campurile!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pwd.equals(pwd_conf)) {

                    long val = db.InsertUtilizator(user, pwd, "Client");

                    if (val > 0) {
                        Toast.makeText(Activity_Register.this, "Inregistrat cu succes!", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(Activity_Register.this, Activity_Login.class);
                        startActivity(moveToLogin);

                    } else {
                        Toast.makeText(Activity_Register.this, "Inregistrarea NU s-a putut realiza.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Activity_Register.this, "Parolele nu coincid!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Register.this);
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
