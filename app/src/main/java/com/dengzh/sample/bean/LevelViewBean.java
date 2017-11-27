package com.dengzh.sample.bean;

import com.example.levelviewlibray.LevelViewBaseData;

import java.util.List;

/**
 * Created by dengzh on 2017/11/23.
 * 晋级图Bean
 */

public class LevelViewBean extends LevelViewBaseData {

    private String ids;
    private String names;
    private String counts;
    private String icons;
    private List<String> lists;

    public LevelViewBean(String ids, String names, String counts, String icons, List<String> lists) {
        this.ids = ids;
        this.names = names;
        this.counts = counts;
        this.icons = icons;
        this.lists = lists;
    }

    @Override
    public String getId() {
        return ids;
    }

    @Override
    public String getName() {
        return names;
    }

    @Override
    public String getCount() {
        return counts;
    }

    @Override
    public String getIcon() {
        return icons;
    }

    @Override
    public List<String> getList() {
        return lists;
    }
}
