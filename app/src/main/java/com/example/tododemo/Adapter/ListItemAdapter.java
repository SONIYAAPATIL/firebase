package com.example.tododemo.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tododemo.ItemOnClickListner;
import com.example.tododemo.MainActivty;
import com.example.tododemo.Model.Todo;
import com.example.tododemo.R;

import java.util.List;

class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {


    ItemOnClickListner itemOnClickListner;

    TextView item_title, item_discription;

    public ListItemHolder( View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

        item_title=(TextView)itemView.findViewById(R.id.item_title);
        item_discription=(TextView)itemView.findViewById(R.id.item_desciption);
    }

    public void setItemOnClickListner(ItemOnClickListner itemOnClickListner){
        this.itemOnClickListner = itemOnClickListner;
    }


    @Override
    public void onClick(View v) {

        itemOnClickListner.onClick(v,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderTitle("Select the Action ");
        menu.add(0,0,getAdapterPosition(),"DELETE");


    }


}


public class ListItemAdapter extends RecyclerView.Adapter<ListItemHolder>{


    MainActivty mainActivty;
    List<Todo> todoList;

    public ListItemAdapter(MainActivty mainActivty, List<Todo> todoList) {
    }

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mainActivty.getBaseContext());
        View view = layoutInflater.inflate(R.layout.listitemlayout,viewGroup,false);
        return new ListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder listItemHolder, int i) {

        mainActivty.title.setText(todoList.get(i).getTitle());
        mainActivty.Discription.setText(todoList.get(i).getDiscription());

        mainActivty.isUpdate=true;
        mainActivty.string =todoList.get(i).getId() ;


        //listItemHolder.()

        listItemHolder.item_title.setText(todoList.get(i).getTitle());
        listItemHolder.item_discription.setText(todoList.get(i).getDiscription());


    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}
