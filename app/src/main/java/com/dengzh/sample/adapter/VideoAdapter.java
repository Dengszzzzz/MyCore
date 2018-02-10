package com.dengzh.sample.adapter;

import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengzh.sample.R;
import com.dengzh.sample.bean.VideoBean;
import com.dengzh.sample.module.gitHubView.VideoConstant;
import com.dengzh.sample.utils.glideUtils.GlideUtils;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by dengzh on 2018/1/29.
 */

public class VideoAdapter extends BaseQuickAdapter<VideoBean,BaseViewHolder>{

    public VideoAdapter(@Nullable List<VideoBean> data) {
        super(R.layout.item_videoview,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        JZVideoPlayerStandard  videoplayer = helper.getView(R.id.videoplayer);
        videoplayer.setUp(item.getUrl(), JZVideoPlayer.SCREEN_WINDOW_LIST,item.getTitle());
        GlideUtils.loadImg(mContext,videoplayer.thumbImageView,VideoConstant.videoThumbs[0][0]);
    }
}
