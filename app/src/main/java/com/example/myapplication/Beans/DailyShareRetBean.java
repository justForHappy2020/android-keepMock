package com.example.myapplication.Beans;

import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.ShareAbb;

import java.util.ArrayList;
import java.util.List;

public class DailyShareRetBean {
    private boolean hasNext;
    private Long totalPages;
    private List<ShareAbb> shareList = new ArrayList<ShareAbb>();

    public DailyShareRetBean() {

    }

    public DailyShareRetBean(boolean hasNext, Long totalPages, List<ShareAbb> shareList) {
        this.hasNext = hasNext;
        this.totalPages = totalPages;
        this.shareList = shareList;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public List<ShareAbb> getShareList() {
        return shareList;
    }

    public void setShareList(List<ShareAbb> shareList) {
        this.shareList = shareList;
    }

    public void addShare(ShareAbb shareAbb){
        this.shareList.add(shareAbb);
    }


}
