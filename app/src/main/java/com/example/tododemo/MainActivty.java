package com.example.tododemo;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.Toast;




import com.example.tododemo.Adapter.ListItemAdapter;
import com.example.tododemo.Model.Todo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class MainActivty extends AppCompatActivity {


    List<Todo>todoList =  new ArrayList<>();
    ListItemAdapter listItemAdapter;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fab;

    public MaterialEditText title ,Discription;

    public boolean  isUpdate = false;
    public String string ="";
        SpotsDialog dialog;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activty);

        db= FirebaseFirestore.getInstance();
        dialog = new SpotsDialog(this) ;
        title=(MaterialEditText)findViewById(R.id.title);
        Discription=(MaterialEditText)findViewById(R.id.discription);
        fab=(FloatingActionButton)findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isUpdate){
                    setData(title.getText().toString(),Discription.getText().toString());
                }
                else {
                    Updatedata(title.getText().toString(),Discription.getText().toString());

                    isUpdate = !isUpdate;
                }
            }
        });


        recyclerView=(RecyclerView)findViewById(R.id.RVview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager( this);
         recyclerView.setLayoutManager(layoutManager);

         Data();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle().equals("DELETE"))
            deleteitem(item.getOrder());
        return super.onContextItemSelected(item);
    }

    private void deleteitem(int order) {

        db.collection("ToDoList")
                .document(todoList.get(order).getId())
                .delete()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Data();
                    }
                });
    }

    private void Updatedata(String title, String discription) {

        db.collection("ToDoList").document(string)
                .update("title",title,"discription",discription)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivty.this, "Updated", Toast.LENGTH_SHORT).show();
                    }
                });
        db.collection("ToDoList").document(string)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                        Data();
                    }
                });
    }

    private void setData(String title, String discription) {

        String id = UUID.randomUUID().toString();

        Map<String,Object> todo = new HashMap<>();
        todo.put("id",id);
        todo.put("title",title);
        todo.put("discription",discription);

        db.collection("TodoList").document(id)
                .set(todo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Data();
            }
        });
    }

    public void Data(){

        dialog.show();
        db.collection("Todo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( Task<QuerySnapshot> task) {
                        for(DocumentSnapshot doc:task.getResult()){
                            Todo todo = new Todo(doc.getString("id"),
                                    doc.getString("title"),
                                    doc.getString("discription"));

                            todoList.add(todo);
                        }
                        listItemAdapter = new ListItemAdapter(MainActivty.this,todoList);

                        recyclerView.setAdapter(listItemAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Toast.makeText(MainActivty.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
