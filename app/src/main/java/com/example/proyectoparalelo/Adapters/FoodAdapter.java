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

import com.example.proyectoparalelo.ElementosList.ListElement;
import com.example.proyectoparalelo.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

private List<ListElement> mData;
private LayoutInflater mInflater;
private Context context;
final FoodAdapter.OnItemClickListener listener;

public interface OnItemClickListener {
    void onItemClick(ListElement item );
}

    public FoodAdapter(List<ListElement> mData, Context context, FoodAdapter.OnItemClickListener listener) {
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
    public FoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.from(parent.getContext()).inflate(R.layout.list_element,parent,false);
        return new FoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodAdapter.ViewHolder holder,final int position) {
        holder.card.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition));
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListElement> items ){mData = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImgae;
        TextView name,descrip,price;
        CardView card;

        ViewHolder(View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.nameTextView);
            descrip=itemView.findViewById(R.id.description);
            price=itemView.findViewById(R.id.txtPrice);
            iconImgae=itemView.findViewById(R.id.iconImageView);
            card=itemView.findViewById(R.id.cv);
        }

        void bindData(ListElement item){
            name.setText(item.getNombre());
            descrip.setText(item.getDescripcion());
            price.setText(item.getPrecio());
            iconImgae.setImageResource(item.getImageId());
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
