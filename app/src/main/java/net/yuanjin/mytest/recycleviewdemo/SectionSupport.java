package net.yuanjin.mytest.recycleviewdemo;

/**
 *  多种分类Header
 *  Created by WuZhanQiang on 2016/12/21.
 */

public interface SectionSupport<T> {

    public int sectionHeaderLayoutId();

    public int sectionTitleTextViewId();

    public String getTitle(T t);
}
