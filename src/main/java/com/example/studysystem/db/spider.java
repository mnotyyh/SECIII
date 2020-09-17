package com.example.studysystem.db;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.security.Key;
import java.util.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.studysystem.entity.Paper;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.springframework.data.relational.core.sql.In;

public class spider {
    public static void main(String[] args) throws InterruptedException, IOException {
        String s1="\"[Front matter]\",\"\",\"\",\"2016 31st IEEE/ACM International Conference on Automated Software Engineering (ASE)\",\"\",\"2016\",\"\",\"\",\"i\",\"xviii\",\"Conference proceedings front matter may contain various advertisements, welcome messages, committee or program information, and other miscellaneous conference information. This may in some cases also include the cover art, table of contents, copyright statements, title-page or half title-pages, blank pages, venue maps or other general information relating to the conference that was part of the original conference proceedings.\",\"\",\"\",\"\",\"\",\"https://ieeexplore.ieee.org/stamp/stamp.jsp?arnumber=7582736\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"IEEE\",\"IEEE Conferences\"";

        System.out.println(s1.split("\",\"").length);

    }

    public static void exchange(String from,String to ) throws IOException {
        Boolean bool = false;
        File file = new File(to);
        try {
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                bool = true;
                System.out.println("success create file,the file is "+to);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader in=new BufferedReader(new FileReader(from));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(to)));
        String line;
        line=in.readLine();
        while((line=in.readLine())!=null){
            String[] ss=line.split("\",\"");
            String line0=ss[0];
            if(ss.length==30){
                for(int i=1;i<ss.length;i++){
                    if(i!=22){
                        line0=line0+"\",\""+ss[i];
                    }
                }
                out.write(line0+"\n");
            }
        }
        in.close();
        out.close();
    }
    public static int compare(String s1,String s2){
        if(s1.length()!=29){
            if(s2.length()==29){
                return 2;
            }
            else{
                return 0;
            }
        }
        else{
            if(s2.length()!=29){
                return 1;
            }
            else{
                for(int i=0;i<29;i++){
                    if(s1.charAt(i)<s2.charAt(i)){
                        return 2;
                    }
                    else if(s1.charAt(i)>s2.charAt(i)){
                        return 1;
                    }
                }
                return 4;
            }
        }
    }
    private static void saveAsFileWriter(String content,String filePath) {
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(filePath);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static String getSysClipboardText() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪切板中的内容
        Transferable clipTf = sysClip.getContents(null);
        if (clipTf != null) {
            // 检查内容是否是文本类型
            if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    ret = (String) clipTf
                            .getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }
    public static ArrayList<String> getPapers(WebDriver driver,String loc){
        ArrayList<String> papers=new ArrayList<>();
        try {
            String encoding="utf-8";
            File file=new File(loc);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int i=0;
                while((lineTxt = bufferedReader.readLine()) != null){
                    try{
                        String year="";
                        String publisher="";
                        String booltitle="";
                        String startPage="";
                        String endPage="";
                        String Affiliations="";
                        Paper p00=new Paper();
                        String Abstract="";
                        if(lineTxt.charAt(0)=='@'){
                            Paper paper=new Paper();
                            int sa=lineTxt.indexOf('{');
                            String link=lineTxt.substring(sa+1,lineTxt.length()-1);
                            link="https://dl.acm.org/doi/"+link;
                            while(true){
                                String ss=bufferedReader.readLine();
                                if (ss.charAt(0)=='}'){
                                    break;
                                }
                                else{
                                    if(ss.substring(0,4).equals("year")){
                                        year=ss.substring(ss.indexOf('{')+1,ss.indexOf('}'));
                                    }
                                    if(ss.substring(0,9).equals("publisher")){
                                        publisher=ss.substring(ss.indexOf('{')+1,ss.indexOf('}'));
                                    }
                                    if(ss.substring(0,9).equals("booktitle")){
                                        booltitle=ss.substring(ss.indexOf('{')+1,ss.indexOf('}'));
                                    }
//                                    if(ss.substring(0,5).equals("pages")){
//                                        String page=ss.substring(ss.indexOf('{')+1,ss.indexOf('}'));
//                                        String[] ss1=page.split("-");
//                                        System.out.println("asdfasdfa:   "+ss1[1]);
//                                    }
                                }
                            }
                            driver.get(link);
                            Thread.sleep(2000);
                            List<WebElement> elements1=driver.findElements(By.xpath("/html/body/div[1]/div/main/div[2]/article/div[1]/div[2]/div/div[3]/div/ul/li"));
                            System.out.println("li num: "+elements1.size());

                            for(int j=1;j<elements1.size()-1;j++){
                                WebElement ee=elements1.get(j);
                                Actions actions1=new Actions(driver);
                                actions1.click(ee).perform();
                                Thread.sleep(300);
                                List<WebElement> ees=ee.findElements(By.tagName("p"));
                                //System.out.println("Frome: "+ees.get(1).getText());
                                String fro=ees.get(1).getText();
                                Affiliations=Affiliations+fro+";";
                                WebElement ee0=driver.findElement(By.xpath("/html/body/div[1]/div/main/div[2]/article/div[1]/div[2]/div/div[2]/h1"));
                                Actions actions2=new Actions(driver);
                                actions2.click(ee0).perform();
                                Thread.sleep(300);
                            }
                            //Actions actions1=new Actions(driver);
//                        actions1.click(element).perform();
                            String authors="";
                            WebElement RootE=driver.findElement(By.xpath("/html/body/div[1]/div/main/div[2]/article/div[1]/div[2]/div/div[3]/div/ul"));
                            Integer ai=2;
                            String locE="/html/body/div[1]/div/main/div[2]/article/div[1]/div[2]/div/div[3]/div/ul/li["+ai.toString()+"]";
                            WebElement auE=driver.findElement(By.xpath(locE));
                            String s0="/html/body/div[1]/div/main/div[2]/article/div[1]/div[2]/div/div[3]/div/ul/li";
                            List<WebElement> auEs=driver.findElements(By.xpath(s0));
                            for(int j=2;j<auEs.size();j++){
                                Integer jI=j;
                                WebElement we=driver.findElement(By.xpath("/html/body/div[1]/div/main/div[2]/article/div[1]/div[2]/div/div[3]/div/ul/li["+jI.toString()+"]/a/span/div/span/span"));
                                authors=authors+we.getText()+";";
                            }
                            WebElement index=driver.findElement(By.xpath("/html/body/div[1]/div/main/div[2]/article/div[2]/div[2]/div[2]"));
                            List<WebElement> indexs=index.findElements(By.tagName("p"));
                            String Indexs="";
                            Abstract=indexs.get(0).getText();
                            for(int j=1;j<indexs.size();j++){
                                if(indexs.get(j).getText().length()<50&indexs.get(j).getText().length()>0){
                                    Indexs=Indexs+indexs.get(j).getText()+";";
                                }
                            }
                            authors=authors.substring(0,authors.length()-1);
                            Indexs=Indexs.substring(0, Indexs.length()-1);
                            Affiliations=Affiliations.substring(0,Affiliations.length()-1);
                            String title=driver.findElement(By.xpath("/html/body/div[1]/div/main/div[2]/article/div[1]/div[2]/div/div[2]/h1")).getText();
                            String YYS=driver.findElement(By.xpath("/html/body/div[1]/div/main/div[2]/article/div[1]/div[2]/div/div[5]/div/div[1]/div/ul/li[1]/span/span[1]")).getText();
//                            System.out.println(year);
//                            System.out.println(YYS);
//                            System.out.println(title);
//                            System.out.println(link);
//                            System.out.println("publisher:"+publisher);
//                            System.out.println(Indexs);
//                            System.out.println(authors);
//                            System.out.println("bookTitel:"+booltitle);
//                            System.out.println("Affiliations: "+Affiliations);
//                            System.out.println("Abstract: "+Abstract);
                            paper.setPDF_Link(link);
                            paper.setAuthors(authors);
                            paper.setAuthor_Keywords(Indexs);
                            paper.setDocument_title(title);
                            paper.setPublication_Year(year);
                            paper.setReference_Count(YYS);
                            paper.setPublisher(publisher);
                            paper.setAbstract(Abstract);
                            paper.setAuthor_Affiliations(Affiliations);
                            String linePaper="";
                            //"Document Title",Authors,"Author Affiliations","Publication Title",
                            // Date Added To Xplore,"Publication Year",
                            // "Volume","Issue",
                            // "Start Page","End Page",
                            // "Abstract","ISSN",
                            // ISBNs,"DOI",
                            // Funding Information,PDF Link,
                            // "Author Keywords","IEEE Terms",

                            // "INSPEC Controlled Terms","INSPEC Non-Controlled Terms",
                            // "Mesh_Terms",Article Citation Count,

                            // "Reference Count","License",
                            // "Online Date",Issue Date,"Meeting Date",
                            // "Publisher",Document Identifier
                            linePaper=linePaper+"\""+paper.getDocument_title()+"\""+",";
                            linePaper=linePaper+"\""+paper.getAuthors()+"\""+",";
                            linePaper=linePaper+"\""+paper.getAuthor_Affiliations()+"\""+",";
                            linePaper=linePaper+"\""+paper.getPublication_Title()+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+paper.getPublication_Year()+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+paper.getPDF_Link()+"\""+",";
                            linePaper=linePaper+"\""+paper.getAuthor_Keywords()+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";

                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+paper.getReference_Count()+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";

                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";
                            linePaper=linePaper+"\""+"\""+",";

                            linePaper=linePaper+"\""+paper.getPublisher()+"\""+",";
                            linePaper=linePaper+"\""+"\"";
                            System.out.println(linePaper);
                            papers.add(linePaper);
                        }
                    }catch (Exception e){
                        System.out.println("----该论文错误");
                    }
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return papers;
    }
    public static String getFromACM(String str) throws InterruptedException, IOException {
        System.setProperty("java.awt.headless", "false");
        try{
                    str.replace(' ','+');
                    DesiredCapabilities caps = setDownloadsPath("src/main/resources/excelExport");
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/spider/chromedriver.exe");
                    WebDriver driver = new ChromeDriver(caps);
                    String url="https://dl.acm.org/action/doSearch?AllField="+str+"&startPage=0&pageSize=500";
                    driver.get(url);
                    Thread.sleep(1000);
                    WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div/main/div[1]/div/div[2]/div/div[3]/div[1]/div[2]/div[1]"));
                    Actions actions=new Actions(driver);
                    actions.click(element).perform();
                    Thread.sleep(10000);
                    element=driver.findElement(By.xpath("/html/body/div[1]/div/main/div[1]/div/div[2]/div/div[3]/div[1]/div[2]/div[3]/a[1]/i"));
                    Actions actions1=new Actions(driver);
                    actions1.click(element).perform();
                    Thread.sleep(30000);
                    element=driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[1]/div/div[2]/div/form/div[2]/ul/li[2]"));
                    Actions actions2=new Actions(driver);
                    actions2.click(element).perform();
                    System.out.println("---------geasgasdf");
                    System.out.println(getSysClipboardText());
                    System.out.println("---------geasgasdf");
                    Thread.sleep(1000);
                    saveAsFileWriter(getSysClipboardText(),"src/main/resources/spider/b.txt");
                    Thread.sleep(1000);
                    ArrayList<String> papers=getPapers(driver,"src/main/resources/spider/b.txt");
                    Boolean bool = false;
                    Random r=new Random();
                    Integer k=r.nextInt(1000000);
                    String filenameTemp ="src/main/resources/excel/" +"acm"+k.toString()+".csv";//文件路径+名称+文件类型
                    File file = new File(filenameTemp);
                    try {
                        //如果文件不存在，则创建新的文件
                        if(!file.exists()){
                            file.createNewFile();
                            bool = true;
                            System.out.println("success create file,the file is "+filenameTemp);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                    for(int i=0;i<papers.size();i++){
                        System.out.println(papers.get(i));
                        bw.write(papers.get(i)+'\n');
                    }
                    System.out.println("asdfasdfas:"+papers.size());
                    bw.close();
                    return filenameTemp;
                }catch (Exception e){
                    System.out.println("Fail,try again");
                    getFromACM(str);
                }
            return null;
    }
    public static String getFromIEEE(String str) throws InterruptedException, IOException {
            try{
                //改成绝对路径
                String dlPath=System.getProperty("user.dir")+"\\src\\main\\resources\\excelExport";
                System.out.println(dlPath);
                DesiredCapabilities caps = setDownloadsPath(dlPath);
                System.setProperty("webdriver.chrome.driver", "src/main/resources/spider/chromedriver.exe");
                WebDriver driver = new ChromeDriver(caps);
                String url = "https://ieeexplore.ieee.org/";
                driver.get(url);
                System.out.println("Title:" + driver.getTitle());
                System.out.println("currentUrl:" + driver.getCurrentUrl());
                WebElement element = driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div[4]/div/xpl-root/xpl-header/div/div[4]/xpl-search-bar-migr/div/form/div[2]/div/div/xpl-typeahead-migr/div/input"));
                element.sendKeys(str);
                element.sendKeys(Keys.ENTER);
                Thread.sleep(5000);
                element=driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div[4]/div/xpl-root/div/xpl-search-results/main/div[1]/div[2]/xpl-search-dashboard/section/div/div[1]/span[1]/span[2]"));
                String Paper_num=element.getAttribute("textContent").toString();
                System.out.println("Paper_num"+Paper_num);
                Thread.sleep(500);
                element=driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div[4]/div/xpl-root/div/xpl-search-results/main/div[1]/div[1]/ul/li[3]/xpl-export-search-results/button"));
                element.click();
                Thread.sleep(500);
                element=driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div[4]/div/xpl-root/div/xpl-search-results/main/div[1]/div[1]/ul/li[3]/xpl-export-search-results/ngb-tooltip-window/div[2]/ngb-tabset/div/div/div/div/form/div/button"));
                element.click();
                Thread.sleep(20000);
                dlPath.replace('\\','/');
                //改成绝对路径
                File directory = new File(dlPath);
                File[] files=directory.listFiles();
                String result="";
                String result1=files[0].getName();
                for(int i=1;i<files.length;i++){
                    String result2=files[i].getName();
                    if(compare(result1,result2)==2){
                        result1=result2;
                    }
                }
                System.out.println(result1);
                //改成绝对路径
                exchange(dlPath+"/"+result1,"src/main/resources/excel/"+result1);
                return "src/main/resources/excel/"+result1;
            }catch (Exception e){
                return getFromIEEE(str);
            }
    }
    public static DesiredCapabilities setDownloadsPath(String path) {
        String downloadFilepath = path;
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        return cap;
    }
}
