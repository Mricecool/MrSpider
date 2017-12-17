package com.mrspider.model;

import javax.persistence.*;

/**
 * Created by mr on 2017/9/28.
 */
@Entity
@Table(name = "newsinfo")
public class NewsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "newsid")
    private String newsId;
    @Column(name = "newstitle")
    private String newsTitle;
    @Column(name = "newscontent")
    private String newsContent;
    @Column(name = "newstime")
    private String newsTime;

    /**
     * 获取
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * 设置
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getNewsId() {
        return newsId;
    }

    /**
     * 设置
     *
     * @param newsId
     */
    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getNewsTitle() {
        return newsTitle;
    }

    /**
     * 设置
     *
     * @param newsTitle
     */
    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getNewsContent() {
        return newsContent;
    }

    /**
     * 设置
     *
     * @param newsContent
     */
    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getNewsTime() {
        return newsTime;
    }

    /**
     * 设置
     *
     * @param newsTime
     */
    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }
}
