package download;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CnBlog  extends  BaseLoad{
    //post_detail

    List<String> getUrlList() throws IOException {
        List<String> lst = new ArrayList<>();

        Document doc = Jsoup.connect("https://www.cnblogs.com/csguo/category/1089719.html")
                .ignoreContentType(true).get();

        Elements elements = doc.getElementsByClass("entrylist");
        for (Element e : elements) {
            Elements liList = e.getElementsByClass("entrylistItemTitle");
            for (Element li : liList) {
                String url = li.attr("href");
                String title =li.text().trim();
                System.out.println("url=" + url + " >>" + title);
                getContent(url,title.replaceAll("原创","").trim(),"post_detail");
            }
        }
        return lst;
    }

    public static void main(String[] args) throws IOException {
        CnBlog b=    new CnBlog();
        //b.getUrlList();
        b.writeIndex();
    }

}
