package download;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Csdn extends BaseLoad {



    List<String> getUrlList() throws IOException {
        List<String> lst = new ArrayList<>();
        //Document doc = Jsoup.connect("https://blog.csdn.net/tyler_download/category_6097178.html").ignoreContentType(true).get();
       //https://blog.csdn.net/tyler_download/category_6097178_2.html

        Document doc = Jsoup.connect("https://blog.csdn.net/tyler_download/category_6097178_2.html")
                .ignoreContentType(true).get();

        Elements elements = doc.getElementsByClass("column_article_list");
        for (Element e : elements) {
            Elements liList = e.getElementsByTag("li");
            for (Element li : liList) {
                Element url = li.getElementsByTag("a").first();
                String title = li.getElementsByClass("title").text();
                System.out.println("url=" + url.attr("href") + " >>" + title);
                lst.add(url.attr("href"));
                getContent(url.attr("href"),title.replaceAll("原创","").trim(),"article_content");
            }
        }
        return lst;
    }



    public static void main(String[] args) throws IOException {
        Csdn d = new Csdn();
       // d.getUrlList();
        d.writeIndex();
    }
}
