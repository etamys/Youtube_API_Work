package com.example.song_player;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    //    private static final String TAG = "RecyclerView";
//    int count = 0;
    private Context mContext;
    private List<VideoList> mVideoList;


    public RecyclerAdapter(Context mcontext,List<VideoList> mVideoList) {
        this.mContext=mcontext;
        this.mVideoList = mVideoList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.song_list, parent, false);
        //now here we create instance of ViewHolderClass

        return new ViewHolder(view); //Viewholder class is responsible for manging the rows inside a row like ,imageView,textview,textview
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView video_title, rowCountTextView, video_id;
        LinearLayout Video_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnailView);
            video_title = itemView.findViewById(R.id.videoTitle);
            Video_view = itemView.findViewById(R.id.Video_view);

        }
        public  void  onClick(View view){
            Toast.makeText(view.getContext(), (CharSequence) mVideoList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final VideoList singleVideo = mVideoList.get(position);
//        holder.rowCountTextView.setText(singleVideo.getDescription());
        holder.video_title.setText(singleVideo.getTitle());
        //if you have use the same method above
        Picasso.get()
                .load(singleVideo.getThumbnailUrl())
                .resize(120,120)
                .centerCrop()
                .into(holder.imageView);
        holder.Video_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VideoPlayer.class);

                intent.putExtra("VIDEO_TITLE",singleVideo.getTitle());
                intent.putExtra("VIDEO_DESC",singleVideo.getDescription());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    //it return no of item in our recycler View
    @Override
    public int getItemCount() {
        return mVideoList.size();
    }





//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    moviesList.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());
//                    return true;
//                }
//            });
//
//        }
//
//        public  void  onClick(View view){
//            Toast.makeText(view.getContext(), moviesList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
//
//        }




}
