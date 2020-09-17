package com.example.studysystem.service.author;

import com.example.studysystem.dao.AuthorDao;
import com.example.studysystem.dao.OrgDao;
import com.example.studysystem.dao.PaperDao;
import com.example.studysystem.dao.SimplePaperDao;
import com.example.studysystem.entity.*;
import com.example.studysystem.service.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private PaperDao paperDao;
    @Autowired
    private SimplePaperDao simplePaperDao;

    public void set(AuthorDao a,OrgDao o,PaperDao p,SimplePaperDao sp){
        this.authorDao=a;
        this.orgDao=o;
        this.paperDao=p;
        this.simplePaperDao=sp;
    }

    @Override
    public Response getAuthors() {
        try{
            return Response.buildSuccess(authorDao.getAuthors());
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }


    @Override
    public Response getAuthorById(int id) {
        try{
            return Response.buildSuccess(authorDao.getAuthorById(id));
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getIdByAuthorAndOrg(String Author, String Org) {//System.out.println(Author+"  "+Org);
        try{
            //int d=authorDao.getIdByAuthorAndOrg(Author,Org);System.out.println(d);
            while(Author.charAt(0)==' ')Author=Author.substring(1);
            while(Org.charAt(0)==' ')Org=Org.substring(1);
            return Response.buildSuccess(authorDao.getIdByAuthorAndOrg(Author,Org));
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getSimplePaperByAuthor(int id) {
        try{
            String[] temp=authorDao.getPaperIdByAuthor(id).split(";");
            List<Integer> paperId=new ArrayList<>();
            for(String d:temp)paperId.add(Integer.parseInt(d));
            return Response.buildSuccess(paperDao.getPapersByIds(paperId));
//            return Response.buildSuccess(simplePaperDao.getSimplePaperByAuthor(name));
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response searchAuthors(int mode, int sort, String str) {// 支持 4种搜索模式，4种排序模式的 搜索方法
        try {
            List<Author> authors=new ArrayList<>();

            if(mode==0){// 根据作者名字搜，忽略大小写，只要包含搜索子串
                authors=authorDao.searchAuthors_name(str);
            }
            else if(mode==1){// 根据机构名字搜，忽略大小写，只要包含搜索子串
                authors=authorDao.searchAuthors_org(str);
            }
            else if(mode==2){// 目标作者的论文总数 >= 搜索要求的数量
                authors=authorDao.searchAuthors_paperNum(Integer.parseInt(str));
            }
            else if(mode==3){// 目标作者的被引用总数 >= 搜索要求的数量
                authors=authorDao.searchAuthors_citationSum(Integer.parseInt(str));
            }
/*
            if(sort==0){// 看起来像废物方法，因为搜到的肯定包含子串，相关性到底是啥？
                if(mode==0){ // 目标作者名字和查找字符串,相同的连续字符串长度越长，认为相关性越高
                    Map<Integer,Integer> relativity=new TreeMap<>();
                    int k=0;
                    for(;k<authors.size();k++){
                        String name=authors.get(k).getAuthor_name().toLowerCase();
                        String target=str.toLowerCase();
                        int l=0,n1=name.length(),n2=target.length();
                        for(int i=0;i<=n1-n2;i++){
                            int j=0;
                            for(;j<n2&&name.charAt(i+j)==target.charAt(j);j++){}
                            if(j>l)l=j;
                        }
                        relativity.put(k,l);
                    }
                    relativity=sortByValueDescending(relativity);
                    ArrayList<Author> authors_backup=new ArrayList<Author>();
                    for(Author a:authors)authors_backup.add(a);
                    authors.clear();
                    for(Map.Entry<Integer,Integer> a:relativity.entrySet()) {//System.out.println(a.getValue());
                        authors.add(authors_backup.get(a.getKey()));
                    }
                }
            }
*/
            if(sort==1){// 作者名字字母序
                Collections.sort(authors,new Comparator<Author>() {
                    @Override
                    public int compare(Author a1,Author a2) {
                        // TODO Auto-generated method stub
                        return (a1.getAuthor_name().compareTo(a2.getAuthor_name()));
                    }
                });
            }
            else if(sort==2){// 论文数降序
                Collections.sort(authors,new Comparator<Author>() {
                    @Override
                    public int compare(Author a1,Author a2) {
                        // TODO Auto-generated method stub
                        return -1*(a1.getPaper_num()-a2.getPaper_num());
                    }
                });
            }
            else if(sort==3){// 论文数升序
                Collections.sort(authors,new Comparator<Author>() {
                    @Override
                    public int compare(Author a1,Author a2) {
                        // TODO Auto-generated method stub
                        return (a1.getPaper_num()-a2.getPaper_num());
                    }
                });
            }
            else if(sort==4){// 被引用数降序
                Collections.sort(authors,new Comparator<Author>() {
                    @Override
                    public int compare(Author a1,Author a2) {
                        // TODO Auto-generated method stub
                        return -1*(a1.getCitation_sum()-a2.getCitation_sum());
                    }
                });
            }
            else if(sort==5){// 被引用数升序
                Collections.sort(authors,new Comparator<Author>() {
                    @Override
                    public int compare(Author a1,Author a2) {
                        // TODO Auto-generated method stub
                        return (a1.getCitation_sum()-a2.getCitation_sum());
                    }
                });
            }

            return Response.buildSuccess(authors);
        } catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }


    @Override
    public Response getTopPaper(int id) { //根据作者id获得被引用数最多的前10个 simplepaper
        try{
            String p=authorDao.getPaperIdByAuthor(id);//System.out.println(p);
            List<Integer> ids=new ArrayList<>();
            for(String s:p.split(";"))ids.add(Integer.parseInt(s));
            Author author=authorDao.getAuthorById(id);
            String name=author.getAuthor_name();
            String org=author.getOrg();
            List<SimplePaper> papers=simplePaperDao.getSimplePaperByAuthor_Org(name,org);
            sortSimplePaper(papers);
            List<SimplePaper> ans=new ArrayList<>();
            /*List<Paper> papers=paperDao.getPapersByIds(ids);
            sortPaper(papers);
            List<Paper> ans=new ArrayList<>();*/
            for(int i=0;i<Math.min(10,papers.size());i++)ans.add(papers.get(i));
            for(SimplePaper pp:papers)
                if(pp.getArticle_Citation_Count().isEmpty())
                    pp.setArticle_Citation_Count("0");

            //System.out.println(ans.size());
            return Response.buildSuccess(ans);
        }
        catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
    private static void sortSimplePaper(List<SimplePaper> list){ //按照被引用数降序重拍paper
        Collections.sort(list,new Comparator<SimplePaper>() {
            @Override
            public int compare(SimplePaper p1, SimplePaper p2) {
                String a=p1.getArticle_Citation_Count(),b=p2.getArticle_Citation_Count();
                int aa=a.isEmpty()?0:Integer.parseInt(a),bb=b.isEmpty()?0:Integer.parseInt(b);
                return bb-aa;
            }
        });
    }
    private static void sortPaper_year(List<Paper> list){ //按照年份降序重拍paper
        Collections.sort(list,new Comparator<Paper>() {
            @Override
            public int compare(Paper p1, Paper p2) {
                String a=p1.getPublication_Year(),b=p2.getPublication_Year();
                int aa=a.isEmpty()?0:Integer.parseInt(a),bb=b.isEmpty()?0:Integer.parseInt(b);
                return bb-aa;
            }
        });
    }

    @Override
    public Response getTopKeyword(int id) { //返回该作者所有文章关键词最高频前5，是词组不是单个单词
        try{
            List<String> words=authorDao.getKeywords(id);
            List<String> ans=new ArrayList<>();
            Map<String,Integer> map=new TreeMap<>();
            for(String w:words){
                for(String s:w.split(";")){
                    if(s.isEmpty())continue;
                    if(map.containsKey(s))map.put(s,map.get(s)+1);
                    else map.put(s,1);
                }
            }
            map=sortByValueDescending(map);
            int n=0;
            for(Map.Entry<String,Integer> a:map.entrySet()){
                //System.out.println(a.getKey()+"  "+a.getValue());
                ans.add(a.getKey());
                n++;
                if(n==5)break;
            }

            return Response.buildSuccess(ans);
        }
        catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    public Response getTop10Author(int mode){
        try{
            List<Author> authors=new ArrayList<>();
            switch (mode){
                case 1:authors=authorDao.getTopAuthor_paperNum();  // 论文总数排名
                    break;
                case 2:authors=authorDao.getTopAuthor_citationSum(); //引用总数排名
                    break;
                case 3:authors=authorDao.getTopAuthor_point();// 7 3 开
                    break;
            }
            return Response.buildSuccess(authors);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getRelatedAuthors(int id,int mode) {// 和这个作者一起发过论文的作者，不限数量，根据一起发过的论文数量降序
                                                        // mode=0 同机构  mode=1 其他机构
        try{
            Author author=authorDao.getAuthorById(id);
            String s=authorDao.getPaperIdByAuthor(id);
            List<Integer> paperId=new ArrayList<>();
            for(String ss:s.split(";"))paperId.add(Integer.parseInt(ss));
            List<Author> authors=authorDao.getRelatedAuthor_byPaperId(paperId);//System.out.println(authors.size());
            List<Author> ans=new ArrayList<>();
            String org=author.getOrg(),name=author.getAuthor_name();
            if(mode==0){
                for(Author a:authors){
                    if(a.getOrg().equals(org) && !a.getAuthor_name().equals(name)){
                        ans.add(a);
                    }
                }
            }
            else if(mode==1){
                for(Author a:authors){
                    if(!a.getOrg().equals(org)){
                        ans.add(a);
                    }
                }
            }

            return Response.buildSuccess(ans);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getRelatedOrgs(int id) {// 和这个作者一起发过论文的机构，前五，根据一起发过的论文数量降序
        try{
            return Response.buildSuccess(orgDao.getRelatedOrg_byAuthorId(id));
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getHistory(int id){
        try{
            List<History> histories=authorDao.getHistory(id);
            //for(History h:histories)System.out.println(h.getYear()+"  "+h.getNum());
            List<Integer> ans=new ArrayList<>();
            for(int i=1988;i<=2020;i++) {
                boolean find=false;
                for (History h : histories) {
                    if(h.getYear().isEmpty())continue;
                    if(Integer.parseInt(h.getYear())==i) {
                        ans.add(h.getNum());
                        find=true;
                        break;
                    }
                }
                if(!find)ans.add(0);
            }
            return Response.buildSuccess(histories);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getInterest(int id){
        try{
            List<String> titles=authorDao.getTitles(id);
            Map<String,Integer> map=new TreeMap<>();
            for(String t:titles){
                for(String s:t.split(" ")){
                    if(s.isEmpty())continue;
                    if(boringWord(s))continue;
                    s=s.toLowerCase();
                    if(map.containsKey(s))map.put(s,map.get(s)+1);
                    else map.put(s,1);
                }
            }
            map=sortByValueDescending(map);
            List<List<String>> ans=new ArrayList<>();
            int i=0;
            for(Map.Entry<String,Integer> a:map.entrySet()){//System.out.println(a.getKey()+"  "+a.getValue());
                List<String> temp=new ArrayList<>();
                temp.add(a.getKey());
                temp.add(Integer.toString(a.getValue()));
                ans.add(temp);
                i++;
                if(i==8)break;
            }
            return Response.buildSuccess(ans);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getInterest2(int id) {//  获得一个作者全部论文关键词 词组 排名前 i
        try{
            String paper=authorDao.getPaperIdByAuthor(id);
            List<Integer> paperId=new ArrayList<>();
            for(String s:paper.split(";"))paperId.add(Integer.parseInt(s));
            List<Paper> papers=paperDao.getPapersByIds(paperId);
            List<String> keywords=new ArrayList<>();
            for(Paper p:papers) Collections.addAll(keywords, p.getAuthor_Keywords().split(";"));
            //List<String> keywords=authorDao.getKeywords(id);
            //for(String s:keywords)System.out.println(s);
            Map<String,Integer> map2=new TreeMap<>();
            for(String t:keywords){
                for(String s:t.split(";")){
                    if(s.isEmpty())continue;
                    if(boringWord(s))continue;
                    s=s.toLowerCase();
                    if(map2.containsKey(s))map2.put(s,map2.get(s)+1);
                    else map2.put(s,1);
                }
            }
            map2=sortByValueDescending(map2);
            List<List<String>> ans=new ArrayList<>();
            int i=0;
            for(Map.Entry<String,Integer> a:map2.entrySet()){//System.out.println(a.getKey()+"  "+a.getValue());
                List<String> temp=new ArrayList<>();
                temp.add(a.getKey());
                temp.add(Integer.toString(a.getValue()));
                ans.add(temp);
                i++;
                if(i==20)break;
            }
            return Response.buildSuccess(ans);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getRencent(int id,int mode) {
        try{
            String paper=authorDao.getPaperIdByAuthor(id);
            List<Integer> ids=new ArrayList<>();
            for(String s:paper.split(";"))ids.add(Integer.parseInt(s));
            List<Paper> papers=paperDao.getPapersByIds(ids);
            sortPaper_year(papers);
            //for(Paper p:papers)System.out.println(p.getPublication_Year());
            List<Paper> recentPaper=new ArrayList<>();
            for(int i=0;i<Math.min(3,papers.size());i++)recentPaper.add(papers.get(i));
            List<String> recentField=new ArrayList<>();
            Map<String,Integer> recentKeyword=new TreeMap<>();
            for(Paper p:recentPaper) {
                for (String s : p.getAuthor_Keywords().split(";")) {
                    if(s.isEmpty())continue;
                    if(recentKeyword.containsKey(s))    recentKeyword.put(s,recentKeyword.get(s)+1);
                    else    recentKeyword.put(s,1);
                }
            }
            recentKeyword=sortByValueDescending(recentKeyword);
            int num=Math.min(6,recentKeyword.size());
            int i=0;
            for(Map.Entry<String,Integer> a:recentKeyword.entrySet()){
                if(i==num)break;
                recentField.add(a.getKey());
                i++;
            }

            if(mode==1)return Response.buildSuccess(recentField);
            else if(mode==2)return  Response.buildSuccess(recentPaper);
            return Response.buildSuccess("success!");
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getInterest3(int id) {
        try{
            String paper=authorDao.getPaperIdByAuthor(id);
            List<Integer> paperId=new ArrayList<>();
            for(String s:paper.split(";"))paperId.add(Integer.parseInt(s));
            List<Paper> papers=paperDao.getPapersByIds(paperId);
            //for(Paper p:papers)System.out.println(p.getPublication_Year()+"  "+p.getAuthor_Keywords());
            List<String> temp_years=new ArrayList<>();
            for(Paper p:papers)temp_years.add(p.getPublication_Year());
            List<String> years = temp_years.stream().distinct().sorted().collect(Collectors.toList());
            Map<String,List<Paper>> year_paper=new TreeMap<>();
            for(Paper p:papers){
                String year=p.getPublication_Year();
                if(year_paper.containsKey(year)){
                    year_paper.get(year).add(p);
                }else{
                    List<Paper> temp=new ArrayList<>();
                    temp.add(p);
                    year_paper.put(year,temp);
                }
            }
            /*
            for(Map.Entry<String,List<Paper>> a:year_paper.entrySet()){//System.out.println(a.getKey()+"  "+a.getValue());
               System.out.print(a.getKey()+"   ");
               for(Paper pp:a.getValue())System.out.print(pp.getId()+"  ");System.out.println();
            }*/
            List<String> all_keywords=new ArrayList<>();
            Map<String,Map<String,Integer>> year_keyword=new TreeMap<>();
            for(Map.Entry<String,List<Paper>> a:year_paper.entrySet()) {
                String year=a.getKey();
                List<Paper> temp_papers=a.getValue();

                for(Paper p:temp_papers)
                    all_keywords.addAll(Arrays.asList(p.getAuthor_Keywords().split(";")));

                Map<String,Integer> temp_value=new TreeMap<>();
                for(String t:all_keywords){
                    for(String s:t.split(";")){
                        if(s.isEmpty())continue;
                        if(boringWord(s))continue;
                        s=s.toLowerCase();
                        if(temp_value.containsKey(s))temp_value.put(s,temp_value.get(s)+1);
                        else temp_value.put(s,1);
                    }
                }
                temp_value=sortByValueDescending(temp_value);
                Map<String,Integer> top10_value=new TreeMap<>();
                int num=0;
                for(Map.Entry<String,Integer> b:temp_value.entrySet()) {//System.out.print(b.getKey()+"  "+b.getValue()+" * ");
                    if(num==20)break;
                    top10_value.put(b.getKey(),b.getValue());
                    num++;
                }//System.out.println();
                year_keyword.put(year,top10_value);
            }
            List<Map<String,Integer>> maps=new ArrayList<>();
            for(Map.Entry<String,Map<String,Integer>> a:year_keyword.entrySet()) {
                Map<String,Integer> t=sortByValueDescending(a.getValue());
                maps.add(t);
                //System.out.print(a.getKey()+"  ");
               /* for(Map.Entry<String,Integer> c:a.getValue().entrySet()) {
                    System.out.print(c.getKey()+"  "+c.getValue()+"  ");
                }System.out.println();*/
            }
/*
            for(int i=0;i<years.size();i++) {
                System.out.print(years.get(i)+"  ");
                Map<String,Integer> mm=maps.get(i);
                    for (Map.Entry<String, Integer> c : mm.entrySet()) {
                        System.out.print(c.getKey() + " "+c.getValue()+"  ");
                    }System.out.println();

            }
*/
            List<String> names=new ArrayList<>();
            Map<String,Integer> mm=maps.get(maps.size()-1);
            for (Map.Entry<String, Integer> c : mm.entrySet()) names.add(c.getKey());

            List<List<Integer>> nums=new ArrayList<>();
            for(Map<String,Integer> map:maps){
                List<Integer> temp=new ArrayList<>();
                for(String fieldName:names){
                    temp.add(map.getOrDefault(fieldName, 0));
                }nums.add(temp);
            }
            Interest interest=new Interest(id,years,names,nums);
            //for(String s:interest.getNames())System.out.print(s+"----");System.out.println();
            //for(List<Integer> num:interest.getNums()){for(int n:num){System.out.print(n+" ");}System.out.println();}

            return Response.buildSuccess(interest);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }


    @Override
    public Response getMeetings(int id) {
        try {
            String ids=authorDao.getPaperIdByAuthor(id);//System.out.println(ids);
            List<Integer> ids2=new ArrayList<>();
            for(String s:ids.split(";"))ids2.add(Integer.parseInt(s));//System.out.println(ids2.size());

            List<SimpleMeeting> simpleMeetings=paperDao.getSimpleMeetingByIds(ids2);//System.out.println(simpleMeetings.size());
            /*List<Meeting> meetings=new ArrayList<>();
            for(int d:ids2)meetings.add(paperDao.getMeetingById(d));System.out.println(meetings.size());*/
            Map<String,Integer> title_num=new TreeMap<>();
            //Map<String,Integer> title_id=new TreeMap<>();
            Map<String,String> title_year=new HashMap<String,String>();
            for(SimpleMeeting sm:simpleMeetings){
                title_year.put(sm.getPublication_title(),sm.getPublication_year());
                //title_id.put(sm.getPublication_title(),sm.getId());
                String title=sm.getPublication_title();
                if(title_num.containsKey(title))title_num.put(title,title_num.get(title)+1);
                else title_num.put(title,1);
            }//System.out.println(map.size());
           List<Meeting> meetings=new ArrayList<>();
            for(Map.Entry<String,Integer> a:title_num.entrySet()){
                String title=a.getKey();
                int num=a.getValue();
                String year=title_year.get(title);
                //int id2=title_id.get(title);
                Meeting m=new Meeting(-99999,title,year,num); // id 没有任何意义，纯粹嘲讽meeting没有单独的表
                meetings.add(m);
            }
            sortMeeting(meetings);
            //for(Meeting m:meetings)System.out.println(m.getPublication_title()+" "+m.getPublication_year()+" "+m.getPaper_num());

            return Response.buildSuccess(meetings);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getRank(int id, int mode) {
        try{
            List<Author> authors=new ArrayList<>();
            Author me=authorDao.getAuthorById(id);
            String name=me.getAuthor_name(),org=me.getOrg();//System.out.println(org);
            if(mode==0)authors=authorDao.getAuthorsByOrg(org);
            else if(mode==1)authors=authorDao.getAuthors();
            //System.out.println(authors.size());
            List<String> ans=new ArrayList<>();

            for(int i=0;i<authors.size();i++){
                Author a=authors.get(i);
                if(a.getAuthor_name().equals(name)&&a.getOrg().equals(org)){
                    String s= Integer.toString(i+1)+" / "+Integer.toString(authors.size());
                    ans.add(s);
                    break;
                }
            }
            sortAuthor_citation(authors);
            for(int i=0;i<authors.size();i++){
                Author a=authors.get(i);
                if(a.getAuthor_name().equals(name)&&a.getOrg().equals(org)){
                    String s= Integer.toString(i+1)+" / "+Integer.toString(authors.size());
                    ans.add(s);
                    break;
                }
            }

            return Response.buildSuccess(ans);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getRankData(int id, int mode) { // 注意，返回的数组中，第一个list<Integer> 第一个值为该作者id，第二个值为他在整个data的第几位
        try{
            List<Author> authors=new ArrayList<>();
            Author me=authorDao.getAuthorById(id);
            String name=me.getAuthor_name(),org=me.getOrg();//System.out.println(org);
            if(mode==0)authors=authorDao.getAuthorsByOrg(org);
            else if(mode==1)authors=authorDao.getAuthors();
            List<List<Integer>> ans=new ArrayList<>();

            for(int i=0;i<authors.size();i++){
                Author a=authors.get(i);
                if(a.getAuthor_name().equals(name)&&a.getOrg().equals(org)){
                    //System.out.println(i+"  "+name+" "+org+"  "+a.getPaper_num()+"  "+a.getCitation_sum());
                    List<Integer> temp=new ArrayList<>();
                    temp.add(id);temp.add(i+1);ans.add(temp);
                    break;
                }
            }
            for(Author a:authors){
                List<Integer> temp=new ArrayList<>();
                temp.add(a.getPaper_num());temp.add(a.getCitation_sum());
                ans.add(temp);
            }

            return Response.buildSuccess(ans);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    private static void sortAuthor_citation(List<Author> list){ //按照被引用数降序重拍paper
        Collections.sort(list,new Comparator<Author>() {
            @Override
            public int compare(Author a1,Author a2) {
                return a2.getCitation_sum()-a1.getCitation_sum();
            }
        });
    }

    private static void sortMeeting(List<Meeting> list){ //按照被引用数降序重拍paper
        Collections.sort(list,new Comparator<Meeting>() {
            @Override
            public int compare(Meeting m1, Meeting m2) {
                String a=m1.getPublication_year(),b=m2.getPublication_year();
                int aa=a.isEmpty()?0:Integer.parseInt(a),bb=b.isEmpty()?0:Integer.parseInt(b);
                return aa-bb;
            }
        });
    }

    private boolean boringWord(String s){
        if(s.contains("(")||s.contains(")"))return true;
        String regex2 = ".*[0-9].*";
        if(s.matches(regex2))return true;
        String[] list={"of","an","for","a","and","from","on","in","the","high","low","over","with","to","through"};
        for(String l:list)if(l.equals(s))return true;
        return false;
    }

    public <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
