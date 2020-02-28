package ro.upb.etti.stud.BulaiRadu_P3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import ro.upb.etti.stud.BulaiRadu_P3.Activities.Activity_Consultatii;
import ro.upb.etti.stud.BulaiRadu_P3.Activities.Activity_Medicamente;
import ro.upb.etti.stud.BulaiRadu_P3.Activities.Activity_Medici;
import ro.upb.etti.stud.BulaiRadu_P3.Activities.Activity_Pacienti;
import ro.upb.etti.stud.BulaiRadu_P3.Activities.Activity_Sectii;
import ro.upb.etti.stud.BulaiRadu_P3.Activities_Client.Activity_Consultatii_Client;
import ro.upb.etti.stud.BulaiRadu_P3.Activities_Client.Activity_Medicamente_Client;
import ro.upb.etti.stud.BulaiRadu_P3.Activities_Client.Activity_Medici_Client;
import ro.upb.etti.stud.BulaiRadu_P3.Activities_Client.Activity_Pacienti_Client;
import ro.upb.etti.stud.BulaiRadu_P3.Activities_Client.Activity_Sectii_Client;

public class MainActivity_Client extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declar 5 obiecte cardview - PENTRU BUTOANELE/ACTIVITATILE MENIULUI PRINCIPAL CU CARE URMEAZA SA INTERACTIONEZ
        CardView MediciCard, SectiiCard, PacientiCard, ConsultatiiCard, MedicamenteCard;
        Button Button_BackToLogin;

        //AICI initializez obiectele Card si le setez metoda OnClickListener !
        MediciCard = (CardView) findViewById(R.id.idMedici);
        SectiiCard = (CardView) findViewById(R.id.idSectii);
        PacientiCard = (CardView) findViewById(R.id.idPacienti);
        ConsultatiiCard = (CardView) findViewById(R.id.idConsultatii);
        MedicamenteCard = (CardView) findViewById(R.id.idMedicamente);
        Button_BackToLogin = (Button) findViewById(R.id.id_back_to_login_page);
        Button_BackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(MainActivity_Client.this, Activity_Login.class);
                startActivity(loginIntent);
            }
        });

        //adaug metoda click listener la fiecare din carduri:
        MediciCard.setOnClickListener(this);
        SectiiCard.setOnClickListener(this);
        PacientiCard.setOnClickListener(this);
        ConsultatiiCard.setOnClickListener(this);
        MedicamenteCard.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_Client.this);
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

    //PORNIRE ACTIVITATI (butoanele din meniu) de fiecare data cand apasam:
    //suprascriu metoda onClick
    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) { //testam de fiecare data ce card a fost apasat(click)
            case R.id.idMedici:
                i = new Intent(this, Activity_Medici_Client.class);
                startActivity(i); break;
            case R.id.idSectii :
                i = new Intent(this, Activity_Sectii_Client.class);
                startActivity(i); break;
            case R.id.idPacienti :
                i = new Intent(this, Activity_Pacienti_Client.class);
                startActivity(i); break;
            case R.id.idConsultatii :
                i = new Intent(this, Activity_Consultatii_Client.class);
                startActivity(i); break;
            case R.id.idMedicamente :
                i = new Intent(this, Activity_Medicamente_Client.class);
                startActivity(i); break;
            default: break;
        }
    }
}
