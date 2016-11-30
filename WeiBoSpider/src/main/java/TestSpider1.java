import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

import java.io.*;

/**
 * Created by stone on 02/10/2016.
 */
public class TestSpider1 extends BreadthCrawler {
    private BufferedWriter writer;

    public TestSpider1(String crawlPath, boolean autoParse) throws IOException {
        super(crawlPath, autoParse);
        writer = new BufferedWriter(new FileWriter("./out.txt"));
    }

    public void visit(Page page, Links links) {
        String title = page.getDoc().select("h1[id=articleTitle]").first().text();
        try {
            writer.write(title+"\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        TestSpider1 spider = new TestSpider1("test1",true);
        spider.addSeed("https://segmentfault.com/blogs");
        spider.addRegex("^https://segmentfault.com/a/[0-9]*");

        spider.setThreads(10);
        spider.start(10);
    }
}
