package download;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Random;

public class BaseLoad {

    String baseDir = "d:/at/";

    public void getContent(String url, String title,String cssId) throws IOException {
        //article_content
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        Element element = doc.getElementById(cssId);
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

    public void downloadImg(String img, String path) throws IOException {
        try {
            if(img.startsWith("//")){
                img="http:"+img;
            }
                Connection.Response response = Jsoup.connect(img)
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36")
                    .ignoreContentType(true).execute();
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


    public   void writeIndex() throws IOException {
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
}
