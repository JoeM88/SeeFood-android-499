//package com.example.seefood.menuManagement;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.seefood.models.MealModel;
//import com.example.seefood.restaurantList.RecyclerViewAdapter;
//
//import org.w3c.dom.Text;
//
//import java.util.List;
//
//public class editAdapter extends RecyclerViewAdapter.Adapter<editAdapter.MyViewHolder> {
//    private Context mContext;
//    private OnMealModelListener mOnMealModelListener;
//    private List<MealModel> mData;
//
//    public editAdapter(Context myContext, List<MealModel> mData, OnMealModelListener mOnMealModelListener){
//        this.mContext = myContext;
//        this.mData = mData;
//        this.mOnMealModelListener = mOnMealModelListener;
//    }
//
//    @Override
//    public int getItemCount() {return mData.size();}
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        private ImageView image;
//        private TextView foodName;
//        private TextView foodDescription;
//        private TextView allergyList;
//        private TextView calories;
//    }
//
//    public interface OnMealModelListener{
//        void onMealClick(int position);
//    }
//}
