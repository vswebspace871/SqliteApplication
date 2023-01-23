package com.example.sqliteapplication;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<Model> list;
    Context context;
    MyDBHelper myDBHelper;

    public Adapter(ArrayList<Model> list, Context context) {
        this.list = list;
        this.context = context;
        myDBHelper = new MyDBHelper(context);
    }

    public void setList(ArrayList<Model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.id.setText(list.get(holder.getAdapterPosition()).id+"");
            holder.name.setText(list.get(holder.getAdapterPosition()).name);
            holder.number.setText(list.get(holder.getAdapterPosition()).phone_number);
            holder.bind(position, list.get(holder.getAdapterPosition()).id);
            holder.imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                // Custom Update Dialog Box
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.update_dialog);

                    EditText name1 = dialog.findViewById(R.id.etName1);
                    EditText Phnumber1 = dialog.findViewById(R.id.etNumber1);
                    Button btnUpdate = dialog.findViewById(R.id.buttonUpdate);
                    btnUpdate.setText("Update");

                    name1.setText(list.get(holder.getAdapterPosition()).name);
                    Phnumber1.setText(list.get(holder.getAdapterPosition()).phone_number);

                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String newName = name1.getText().toString();
                            String newPhonenum = Phnumber1.getText().toString();

                            Model model = new Model();
                            model.id = list.get(holder.getAdapterPosition()).id;
                            model.name = newName;
                            model.phone_number = newPhonenum;
                            myDBHelper.updateContact(model);
                            list.set(holder.getAdapterPosition(), model);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id, name, number;
        ImageView imgEdit, imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.textViewID);
            name = itemView.findViewById(R.id.textViewName);
            number = itemView.findViewById(R.id.textViewNumber);

            imgEdit = itemView.findViewById(R.id.imageView);
            imgDelete = itemView.findViewById(R.id.imageView2);
        }

        public void bind(int position, int id) {
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("vbv", "onClick: "+position);
                    myDBHelper.deleteContact(id);
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
