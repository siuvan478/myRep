package com.asgab;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 *
 */
public class Spider {

    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("http://1024.gd/").get();
        Elements lis = document.select("#recent-posts-2 ul li a");
        for (Element e : lis) {
            Document detail = Jsoup.connect(e.attr("href")).get();
            String title = detail.select(".entry-header h1").text();
            Elements imgs = detail.select("img");
            for (Element img : imgs) {
                String imgSrc = img.attr("src");
                downImages("C:\\Users\\Administrator\\Desktop\\美图秀秀\\" + title, imgSrc);
                System.out.println("download success" + imgSrc);
            }
        }
    }

    /**
     * 下载图片到指定目录
     *
     * @param filePath 文件路径
     * @param imgUrl   图片URL
     */
    public static void downImages(String filePath, String imgUrl) {
        // 若指定文件夹没有，则先创建
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 截取图片文件名
        String fileName = UUID.randomUUID().toString().replaceAll("-","").concat(".jpg");
        // 写出的路径
        File file = new File(filePath + File.separator + fileName);
        try {
            // 获取图片URL
            URL url = new URL(imgUrl);
            // 获得连接
            URLConnection connection = url.openConnection();
            // 设置10秒的相应时间
            connection.setConnectTimeout(10 * 1000);
            // 获得输入流
            InputStream in = connection.getInputStream();
            // 获得输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            // 构建缓冲区
            byte[] buf = new byte[1024];
            int size;
            // 写入到文件
            while (-1 != (size = in.read(buf))) {
                out.write(buf, 0, size);
            }
            out.close();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
