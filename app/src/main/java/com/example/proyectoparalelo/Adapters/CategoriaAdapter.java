package com.example.proyectoparalelo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoparalelo.ElementosList.CategoriaElement;
import com.example.proyectoparalelo.R;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder>{
    private List<CategoriaElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    final CategoriaAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        //void onItemClick(CategoriaElement item, int adapterPosition);

        void onItemClick(int adapterPosition);
    }

    public CategoriaAdapter(List<CategoriaElement> mData, Context context,CategoriaAdapter.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = mData;
        this.listener = listener;

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public CategoriaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.categoria_element,null);
        return new CategoriaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoriaAdapter.ViewHolder holder,final int position) {
        holder.card.setAnimation(AnimationUtils.loadAnimation(context,R.anim.slide));
        holder.bindData(mData.get(position));
    }

    public void setItems(List<CategoriaElement> items ){mData = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImgae;
        TextView name;
        CardView card;

        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.txtCategoria);
            iconImgae = itemView.findViewById(R.id.imgCategoria);
            card = itemView.findViewById(R.id.cvCategoria);
        }

        void bindData(CategoriaElement item){
            name.setText(item.getNom());
            iconImgae.setImageResource(item.getImageId());
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
