package com.sinwn.capsule.domain;

import com.github.pagehelper.Page;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class ResultListData<T> implements Serializable {

    private PageInfo pageInfo;

    private List<T> list;

    public ResultListData(@NotNull List<T> list) {
        if (list instanceof Page) {
            pageInfo = new PageInfo((Page) list);
            this.list = list;
        } else if (list != null) {
            pageInfo = new PageInfo(list.size());
            this.list = list;
        }
        if (list != null) {
            //判断页面边界
            judgePageBoundary();
        }
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoundary() {
        pageInfo.setFirstPage(pageInfo.getPageNum() == 1);
        pageInfo.setLastPage(pageInfo.getPageNum() == pageInfo.getPages());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageInfo{");
        if (pageInfo == null) {
            return "";
        }
        sb.append("pageNum=").append(pageInfo.getPageNum());
        sb.append(", pageSize=").append(pageInfo.getPageSize());
        sb.append(", total=").append(pageInfo.getTotal());
        sb.append(", pages=").append(pageInfo.getPages());
        sb.append(", list=").append(list);
        sb.append(", isFirstPage=").append(pageInfo.isFirstPage());
        sb.append(", isLastPage=").append(pageInfo.isLastPage());
        sb.append(", navigatepageNums=");
        sb.append('}');
        return sb.toString();
    }
}
