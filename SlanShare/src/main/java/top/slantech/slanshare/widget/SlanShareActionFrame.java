package top.slantech.slanshare.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.slantech.slanshare.R;
import top.slantech.slanshare.SlanSharePlatform;
import top.slantech.slanshare.listener.SlanShareBoardListener;

/**
 * 单独提出来的一个面板内部View
 * Created by slantech on 2021/05/25 18:15
 */
public class SlanShareActionFrame extends LinearLayout {
    private PopupWindow.OnDismissListener mDismissListener;
    private SlanShareBoardListener boardListener;

    public SlanShareActionFrame(Context context) {
        super(context);
    }

    public SlanShareActionFrame(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlanShareActionFrame(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlanShareActionFrame(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setPlatformData(List<SlanSharePlatform> list, SlanShareBoardListener boardListener) {
        this.boardListener = boardListener;
        this.setBackgroundColor(Color.argb(50, 0, 0, 0));
        LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(layoutParams);

        AlphaAnimation var2 = new AlphaAnimation(0.0F, 1.0F);
        var2.setDuration(100L);
        this.setAnimation(var2);
        this.setOrientation(1);

        this.setGravity(80);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SlanShareActionFrame.this.mDismissListener == null)
                    return;
                SlanShareActionFrame.this.mDismissListener.onDismiss();
            }
        });

        View view = createShareBoardLayout(list);
        if (view != null) {
            view.setClickable(true);
            this.addView(view);
        }
    }

    private View createShareBoardLayout(List<SlanSharePlatform> list) {
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        LayoutParams params = new LayoutParams(-1, -2);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(params);

        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.list_layout, null);
        RecyclerView rv = view.findViewById(R.id.list_layout_rv);

        LinearLayoutManager manager = new GridLayoutManager(this.getContext(),4);
        DataAdapter adapter = new DataAdapter(list);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SlanShareActionFrame.this.boardListener.onclick(list.get(position),list.get(position).platform);
                if (SlanShareActionFrame.this.mDismissListener == null)
                    return;
                SlanShareActionFrame.this.mDismissListener.onDismiss();
            }
        });

        linearLayout.addView(view);
        return linearLayout;
    }


    public void setmDismissListener(PopupWindow.OnDismissListener mDismissListener) {
        this.mDismissListener = mDismissListener;
    }

    public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyHolder> {

        private List<SlanSharePlatform> list;
        private OnItemClickListener onItemClickListener;

        public DataAdapter(List<SlanSharePlatform> list) {
            this.list = list;
        }


        public void setOnItemClickListener(OnItemClickListener onItemClickListener)
        {
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            MyHolder holder = new MyHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.tv.setText(list.get(position).title);
            holder.iv.setImageResource(list.get(position).icon);
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(v,holder.getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        class MyHolder extends RecyclerView.ViewHolder {
            ImageView iv;
            TextView tv;

            public MyHolder(View itemView) {
                super(itemView);
                iv = itemView.findViewById(R.id.item_iv_logo);
                tv = itemView.findViewById(R.id.item_tv_title);
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
