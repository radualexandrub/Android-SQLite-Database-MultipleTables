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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.upb.etti.stud.BulaiRadu_P3.Activity_Adapters.Adapter_Medicamente;
import ro.upb.etti.stud.BulaiRadu_P3.DatabaseHelper;
import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Medicamente;
import ro.upb.etti.stud.BulaiRadu_P3.R;
import ro.upb.etti.stud.BulaiRadu_P3.utils.MyDividerItemDecoration;
import ro.upb.etti.stud.BulaiRadu_P3.utils.RecyclerTouchListener;

public class Activity_Medicamente_Client extends AppCompatActivity {
    private Adapter_Medicamente mAdapter;
    private List<Medicamente> medicamenteList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noMedicamenteView;

    private DatabaseHelper db;

    /* ~~~ onCreate ~~~ */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //buton de intoarcere <- din toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamente);

        coordinatorLayout = findViewById(R.id.medicamente_coordinator_layout);
        recyclerView = findViewById(R.id.medicamente_recycler_view);
        noMedicamenteView = findViewById(R.id.empty_medicamente_view);
        db = new DatabaseHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.medicamente_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMedicamenteEditDialog(false, null, -1);
            }
        });

        medicamenteList.addAll(db.getAllMedicamente());
        mAdapter = new Adapter_Medicamente(this, medicamenteList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyMedicamente();

        // On long press on RecyclerView item, open alert dialog with options to choose: Edit / Delete
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }


    /* ~~~ Methods for onCreate ~~~ */
    /* Insert new medicament in db and refresh the medicamenteList */
    private void createMedicament(String denumire) {
        long id = db.insertMedicament(denumire);

        Medicamente obj_medicament = db.getMedicament(id);

        if (obj_medicament != null) {
            medicamenteList.add(0, obj_medicament);
            mAdapter.notifyDataSetChanged();

            toggleEmptyMedicamente();
        }
    }

    /* Update medicamente in db and update item in the medicamenteList by its position */
    private void updateMedicament(String denumire,
                                  int position) {
        // update medicament as object
        Medicamente medicament_obj = medicamenteList.get(position);
        medicament_obj.setDenumire(denumire);

        // update medicament object in db
        db.updateMedicamente(medicament_obj);

        // refresh the list
        medicamenteList.set(position, medicament_obj);
        mAdapter.notifyItemChanged(position);

        toggleEmptyMedicamente();
    }

    /* Delete medicament from SQLite db and remove the item from the medicamenteList by its position */
    private void deleteMedicament(int position) {
        db.deleteMedicamente(medicamenteList.get(position)); // delete medicament from db

        medicamenteList.remove(position); // delete medicament from medicamenteList
        mAdapter.notifyItemRemoved(position);

        toggleEmptyMedicamente();
    }

    /* Open dialog with Edit - Delete options
     * Edit - 0
     * Delete - 1 */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Editeaza"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selected_option) {
                if (selected_option == 0) {
                    showMedicamenteEditDialog(true, medicamenteList.get(position), position);
                }
            }
        });
        builder.show();
    }

    /* Shows alert dialog with EditText options to enter / edit a medicament.
     * when shouldUpdate=true, it automatically displays old note and changes the button text to UPDATE */
    public void showMedicamenteEditDialog(final boolean shouldUpdate, final Medicamente medicament, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_medicamente, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Activity_Medicamente_Client.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputDenumire = view.findViewById(R.id.iddialog_medicament_denumire);

        TextView dialogTitle = view.findViewById(R.id.iddialog_medicamente_title);
        dialogTitle.setText(!shouldUpdate ? "Adaugare medicament nou" : "Editare medicament");

        if (shouldUpdate && medicament != null) {
            inputDenumire.setText(medicament.getDenumire());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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
                if (TextUtils.isEmpty(inputDenumire.getText().toString())) {

                    Toast.makeText(Activity_Medicamente_Client.this,
                            "Completeaza numele medicamentului!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                if (shouldUpdate && medicament != null) {
                    // update medicament by it's id
                    updateMedicament(inputDenumire.getText().toString(),
                            position);
                } else {
                    // create new medicament
                    createMedicament(inputDenumire.getText().toString());
                }
            }
        });
    }

    private void toggleEmptyMedicamente() {
        if (db.getMedicamenteCount() > 0) {
            noMedicamenteView.setVisibility(View.GONE);
        } else {
            noMedicamenteView.setVisibility(View.VISIBLE);
        }
    }
}

