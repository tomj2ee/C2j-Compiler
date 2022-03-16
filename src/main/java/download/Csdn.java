package download;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.soap.SAAJResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Csdn {

    String baseDir = "d:/at/";

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
                getContent(url.attr("href"),title.replaceAll("原创","").trim());
            }
        }
        return lst;
    }

    private void getContent(String url, String title) throws IOException {
        //article_content
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        Element element = doc.getElementById("article_content");
        Elements eles = element.getElementsByTag("img");
        if(eles!=null) {
            for (Element img : eles) {
                String src = img.attr("src");
                String p= new Date().getTime()+"";
                String path = baseDir + "/img/" +p+ "/";
                new File(path).mkdirs();
                String name = new Random().nextLong() + ".jpg";
                img.attr("src", "img/" +p+ "/"+name);
                downloadImg(src, path + name);
            }
        }
        String path = baseDir + title + ".html";
        writeContent(baseDir, title, element);
    }

    void downloadImg(String img, String path) throws IOException {
        try {
            Connection.Response response = Jsoup.connect(img).ignoreContentType(true).execute();
            byte[] bytes = response.bodyAsBytes();
            writeByteArrayToFile(new File(path), bytes);
        }catch (Exception ex){
            System.out.println(">>> error"+img);
            ex.printStackTrace();
        }
    }

    void writeByteArrayToFile(File f, byte[] bytes) throws IOException {
        FileOutputStream out = new FileOutputStream(f);
        out.write(bytes, 0, bytes.length);
        out.close();
    }

    private String getCss() {
        return "";
    }

    private void writeContent(String path, String title, Element body) {
        try {
            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"zh-CN\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"utf-8\">\n";
            html += "\n";
            html+="   <link rel=\"stylesheet\" href=\"main.css\" />\n";
            html += "  <style type=\"text/css\" > " + getCss() + "</style>\n";
            html += " <title> " + title + "</title>\n";
            html += " <body> " + title + "</body>\n";
            html += body.html();
            html += " </html>\n";
            writeByteArrayToFile(new File(path + "/" + title + ".html"),
                    html.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    void writeIndex() throws IOException {
        //D:\all_project\github\C2j-Compiler\src\main\resources\doc
        String url = "D:\\all_project\\github\\C2j-Compiler\\src\\main\\resources\\doc";
        File f = new File(url);
        File[] lst = f.listFiles();
        StringWriter writer = new StringWriter();
        writer.append("<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n");
        writer.append("</head>\n");
        writer.append("<body>\n");
        for (File i : lst) {
            String name = i.getName();
            if (name.endsWith(".html")) {
                String t=name.substring(0,name.lastIndexOf("."));
                String a = "<a  target=\"_blank\" href=\"" + name + "\">" + t + "</a><br/>\n\n";
                writer.write(a);
            }
        }
        writer.append("</body>\n");
        writer.append("</html>\n");
        byte[] bytes = writer.getBuffer().toString().getBytes();
        writeByteArrayToFile(new File(baseDir + "index.html"), bytes);
    }

    public static void main(String[] args) throws IOException {
        Csdn d = new Csdn();
       // d.getUrlList();
        d.writeIndex();
    }
}
