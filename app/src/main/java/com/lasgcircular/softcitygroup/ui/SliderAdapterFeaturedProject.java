package com.lasgcircular.softcitygroup.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lasgcircular.softcitygroup.CircularApplication;
import com.lasgcircular.softcitygroup.Constant;
import com.lasgcircular.softcitygroup.MainActivity;
import com.lasgcircular.softcitygroup.R;
import com.lasgcircular.softcitygroup.fragments.FragFeaturedProject;
import com.lasgcircular.softcitygroup.model.ProjectsItem;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterFeaturedProject extends
        SliderViewAdapter<SliderAdapterFeaturedProject.SliderAdapterVH> {

    private Context context;
    private List<ProjectsItem> mSliderItems = new ArrayList<>();

    public SliderAdapterFeaturedProject(Context context) {
        this.context = context;
    }

    public void renewItems(List<ProjectsItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

//    public void deleteItem(int position) {
//        this.mSliderItems.remove(position);
//        notifyDataSetChanged();
//    }
//
//    public void addItem(FeaturedProjectData sliderItem) {
//        this.mSliderItems.add(sliderItem);
//        notifyDataSetChanged();
//    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_dashboard, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        ProjectsItem sliderItem = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(sliderItem.getDescription());
        viewHolder.textViewDescription.setTypeface(CircularApplication.mTfMainRegular);
        Glide.with(context).load(Constant.IMG_PATH + sliderItem.getImg()).into(viewHolder.imageViewBackground);
        viewHolder.itemView.setTag(sliderItem);
        viewHolder.itemView.setOnClickListener(view->{
            if (context != null && context instanceof MainActivity){
                ((MainActivity)context).addFragmentFromBottom(R.id.fl_main_container,
                        new FragFeaturedProject((ProjectsItem) view.getTag()), true);
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_slider_dashboard);
            textViewDescription = itemView.findViewById(R.id.tv_slider_dashboard);
            this.itemView = itemView;
        }
    }

}