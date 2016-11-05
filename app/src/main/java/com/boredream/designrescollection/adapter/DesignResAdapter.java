package com.boredream.designrescollection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boredream.designrescollection.entity.SearchInvestListResponse.ElementInvestList;

import java.util.ArrayList;

public class DesignResAdapter extends RecyclerView.Adapter<DesignResAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ElementInvestList> datas;

    public DesignResAdapter(Context context, ArrayList<ElementInvestList> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_image;
        public TextView tv_name;

        public ViewHolder(View rootView) {
            super(rootView);

//            this.iv_image = (ImageView) rootView.findViewById(R.id.iv_image);
            this.tv_name = (TextView) rootView.findViewById(android.R.id.text1);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(android.R.layout.activity_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ElementInvestList data = datas.get(position);

//        GlideHelper.showImage(context, data.getImgUrl(), holder.iv_image);
//        String str = String.format("[%s] %s", data.getSrcTag(), data.getName());
        holder.tv_name.setText(data.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, WebViewActivity.class);
//                intent.putExtra(WebViewActivity.EXTRA_TITLE, data.getName());
//                intent.putExtra(WebViewActivity.EXTRA_URL, data.getSrcLink());
//                context.startActivity(intent);
            }
        });
    }


}
