package ro.upb.etti.stud.BulaiRadu_P3.Activities_Client;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.upb.etti.stud.BulaiRadu_P3.Activity_Adapters.Adapter_Medici;
import ro.upb.etti.stud.BulaiRadu_P3.DatabaseHelper;
import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Medici;
import ro.upb.etti.stud.BulaiRadu_P3.R;
import ro.upb.etti.stud.BulaiRadu_P3.utils.MyDividerItemDecoration;
import ro.upb.etti.stud.BulaiRadu_P3.utils.RecyclerTouchListener;

public class Activity_Medici_Client extends AppCompatActivity {
    private Adapter_Medici mAdapter;
    private List<Medici> mediciList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noMediciView;

    private DatabaseHelper db;

    /* ~~~ onCreate ~~~ */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //buton de intoarcere <- din toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medici);

        coordinatorLayout = findViewById(R.id.medici_coordinator_layout);
        recyclerView = findViewById(R.id.medici_recycler_view);
        noMediciView = findViewById(R.id.empty_medici_view);
        db = new DatabaseHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.medici_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMediciEditDialog(false, null, -1);
            }
        });

        mediciList.addAll(db.getAllMedici("nume"));
        mAdapter = new Adapter_Medici(this, mediciList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyMedici();

        // On long press on RecyclerView item, open alert dialog with options to choose:
        // Edit and Delete
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
                Log.d("LongClick", "FUNCTIONEAZA???");
                Log.d("LongClickPosition", "position " + position);
            }
        }));
    }

    /* ~~~ onCreateOptionsMenu - Pentru Sortare dupa Nume/Sectie ~~~ */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.medici_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idmenu_medici_sort1:
                //SORTARE MEDICI (de fapt stergere si reafisare lista...)
                mediciList.clear();
                mediciList.addAll(db.getAllMedici("nume"));
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.idmenu_medici_sort2:
                //SORTARE MEDICI (de fapt stergere si reafisare lista...)
                mediciList.clear();
                mediciList.addAll(db.getAllMedici("specializare"));
                mAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* ~~~ Methods for onCreate ~~~ */
    /* Insert new medic in db and refresh the mediciList */
    private void createMedic(String nume,
                             String prenume,
                             String specializare) {
        long id = db.insertMedic(nume, prenume, specializare); // insert medic in db

        Medici n = db.getMedic(id); // get the newly inserted medic id (autoincrement)

        if (n != null) {
            mediciList.add(0, n); // add new medic to array list at 0 position
            mAdapter.notifyDataSetChanged(); // refresh the list?

            toggleEmptyMedici();
        }
    }

    /* Update medic in db and update item in the mediciList by its position */
    private void updateMedic(String nume,
                             String prenume,
                             String specializare,
                             int position) {
        // update medic as object
        Medici medic_obj = mediciList.get(position);
        medic_obj.setNume(nume);
        medic_obj.setPrenume(prenume);
        medic_obj.setSpecializare(specializare);

        // update medic object in db
        db.updateMedici(medic_obj);

        // refresh the list
        mediciList.set(position, medic_obj);
        mAdapter.notifyItemChanged(position);

        toggleEmptyMedici();
    }

    /* Delete medic from SQLite db and remove the item from the mediciList by its position */
    private void deleteMedic(int position) {
        db.deleteMedici(mediciList.get(position)); // delete medic from db

        mediciList.remove(position); // delete medic from mediciList
        mAdapter.notifyItemRemoved(position);

        toggleEmptyMedici();
    }

    /* Open dialog with Edit - Delete options
     * Edit - 0
     * Delete - 1 */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selected_option) {
                if (selected_option == 0) {
                    showMediciEditDialog(true, mediciList.get(position), position);
                }
            }
        });
        builder.show();
    }

    /* Shows alert dialog with EditText options to enter / edit a medic.
     * when shouldUpdate=true, it automatically displays old note and changes the button text to UPDATE */
    private void showMediciEditDialog(final boolean shouldUpdate, final Medici medic, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_medici, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Activity_Medici_Client.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputNume = view.findViewById(R.id.iddialog_medic_nume);
        final EditText inputPrenume = view.findViewById(R.id.iddialog_medic_prenume);
        final EditText inputSpecializare = view.findViewById(R.id.iddialog_medic_specializare);

        TextView dialogTitle = view.findViewById(R.id.iddialog_medic_title);
        dialogTitle.setText(!shouldUpdate ? "Adaugare medic nou" : "Edit Medic");

        if (shouldUpdate && medic != null) {
            inputNume.setText(medic.getNume());
            inputPrenume.setText(medic.getPrenume());
            inputSpecializare.setText(medic.getSpecializare());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                dialogInterface.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputNume.getText().toString()) || TextUtils.isEmpty(inputPrenume.getText().toString())) {
                    Toast.makeText(Activity_Medici_Client.this,
                            "Completeaza numele si prenumele medicului!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                if (shouldUpdate && medic != null) {
                    // update medic by it's id
                    updateMedic(inputNume.getText().toString(),
                            inputPrenume.getText().toString(),
                            inputSpecializare.getText().toString(),
                            position);
                } else {
                    // create new medic
                    createMedic(inputNume.getText().toString(),
                            inputPrenume.getText().toString(),
                            inputSpecializare.getText().toString());
                }
            }
        });
    }

    private void toggleEmptyMedici() {
        if (db.getMediciCount() > 0) {
            noMediciView.setVisibility(View.GONE);
        } else {
            noMediciView.setVisibility(View.VISIBLE);
        }
    }
}
