package com.mrspider.service.impl;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mrspider.dao.NewsDao;
import com.mrspider.model.NewsInfo;
import com.mrspider.service.SpiderService;
import com.mrspider.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr on 2017/9/28.
 */
@Service
public class SpiderServiceImpl implements SpiderService {

    private String url = "http://www.yidianzixun.com/channel/hot";

    @Autowired
    private NewsDao newsDao;

    public void executeSpiderTask() {
        String res = HttpUtils.getHtml(url);
        if ("".equals(res)) {
            System.out.println(res);
        } else {
            try {
                getData(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void executeTask() {
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            //htmlunit 对css和javascript的支持不好，所以请关闭之
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(true);
            HtmlPage page = null;
            page = (HtmlPage) webClient.getPage("http://www.yidianzixun.com/");
            final HtmlAnchor titles = (HtmlAnchor) page.getByXPath("//a[@href='/channel/hot']").get(0);
            HtmlPage page2 = titles.click();
            webClient.waitForBackgroundJavaScript(5000);
            //输出新页面的文本
            System.out.println(page2.asXml());
            List<NewsInfo> news = getData(page2.asXml());
            newsDao.save(news);
            webClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<NewsInfo> getData(String html) throws Exception {
        //获取的数据，存放在集合中
        List<NewsInfo> data = new ArrayList<NewsInfo>();
        //采用Jsoup解析
        Document doc = Jsoup.parse(html);
        //获取html标签中的内容
        Elements elements = doc.select("div[class=channel-news channel-news-0]").select("a[class=item doc style-small-image style-content-middle]");
        for (Element ele : elements) {
            String title = ele.select("div[class=doc-content]").select("div[class=doc-content-inline]").select("div[class=doc-title]").text();
            String content = ele.select("div[class=doc-image-small-wrapper]").select("div[class=doc-image-box]").select("img[class=doc-image doc-image-small]").attr("src");
            System.out.println(title);
            System.out.println(content);
            NewsInfo newsInfo=new NewsInfo();
            newsInfo.setNewsTitle(title);
            newsInfo.setNewsContent(content);
            data.add(newsInfo);
        }
        //返回数据
        return data;
    }

    @Scheduled(fixedRate = 60000)
    public void execute100Task() {
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            //htmlunit 对css和javascript的支持不好，所以请关闭之
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            HtmlPage page = null;
            page = (HtmlPage) webClient.getPage("http://www.kuaidi100.com/");
            webClient.waitForBackgroundJavaScript(5000);
            System.out.println(page.asText());
            //获取搜索输入框并提交搜索内容
            HtmlInput input = (HtmlInput)page.getHtmlElementById("postid");
            input.setValueAttribute("604006951028801");
            //获取搜索按钮并点击
            HtmlInput btn = (HtmlInput)page.getHtmlElementById("query");
            HtmlPage page2 = btn.click();
            //webClient.waitForBackgroundJavaScript(5000);
            //输出新页面的文本
            System.out.println(page2.asText());
            System.out.println(page2.asXml());
            webClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
