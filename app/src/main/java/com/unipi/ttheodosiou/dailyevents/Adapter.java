package com.unipi.ttheodosiou.dailyevents;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.sql.ConnectionEventListener;

import static android.support.v4.content.ContextCompat.startActivity;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private  List<String> title,content;
    private  OnEventListener myOnEventListener;

    Adapter(Context context, List<String> title, List<String> content, OnEventListener onEventListener){
        this.layoutInflater = LayoutInflater.from(context);
        this.title = title;
        this.content = content;
        this.myOnEventListener = onEventListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_view,parent, false);
        return new ViewHolder(view, myOnEventListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // bind the textview with data recieved
        holder.textTitle.setText(title.get(position));
        holder.textContent.setText(content.get(position));
        System.out.println(ParseData.URL[position]);
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textTitle, textContent;
        OnEventListener onEventListener;

        public ViewHolder(View itemView, OnEventListener onEventListener) {
         super(itemView);
         textTitle = itemView.findViewById(R.id.textTitle);
         textContent = itemView.findViewById(R.id.textContent);
         this.onEventListener = onEventListener;

         itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onEventListener.onEventClick(getAdapterPosition());
        }
    }
    public interface  OnEventListener{
        void  onEventClick(int position);
    }
}
