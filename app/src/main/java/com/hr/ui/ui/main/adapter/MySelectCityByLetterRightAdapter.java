package com.hr.ui.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MySelectCityByLetterRightAdapter extends RecyclerView.Adapter<MySelectCityByLetterRightAdapter.MyViewHolder> {
    private String[] letters;
    private OnItemClickListener onItemClickListener;

    public void setLetters(String[] letters) {
        this.letters = letters;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selectcitybyletterright, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {
        holder.tvItemSelectCityByLetterRight.setText(letters[position]);
        if(onItemClickListener!=null) {
            final  int pos=position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemCLick(v,pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return letters == null ? 0 : letters.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_selectCityByLetterRight)
        TextView tvItemSelectCityByLetterRight;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
