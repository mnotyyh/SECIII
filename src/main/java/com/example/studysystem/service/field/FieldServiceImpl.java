package com.example.studysystem.service.field;

import com.example.studysystem.dao.*;
import com.example.studysystem.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FieldServiceImpl implements FieldService{
    @Autowired FieldDao fieldDao;
    @Autowired PaperDao paperDao;
    @Autowired SimplePaperDao simplePaperDao;
    @Autowired AuthorDao authorDao;
    @Autowired OrgDao orgDao;
    public void set(FieldDao fieldDao,PaperDao paperDao,OrgDao orgDao,AuthorDao authorDao){this.fieldDao=fieldDao;this.paperDao=paperDao;this.orgDao=orgDao;this.authorDao=authorDao;}
    @Override
    public Response getFields() {
        try{
            return Response.buildSuccess(fieldDao.getFields());
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
    @Override
    public Response getTop20Field(int mode) {
        try{
            List<Field> fields=new ArrayList<>();
            switch (mode){
                case 1:fields=fieldDao.getTopField_paperNum();  // 论文总数排名
                    break;
                case 2:fields=fieldDao.getTopField_citationSum(); //引用总数排名
                    break;
                case 3:fields=fieldDao.getTopField_point();// 7 3 开
            }
            return Response.buildSuccess(fields);
        }catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }
    @Override
    public Response getIdByName(String name) {
        try{
            while(name.charAt(0)==' ')name=name.substring(1);
            int id=fieldDao.getIdByName(name);//System.out.println(id);
            return Response.buildSuccess(id);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getFieldById(int id) {
        try {
            return Response.buildSuccess(fieldDao.getFieldById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getCloud() {
        try{
            List<Field> allFields=fieldDao.getFields();
            List<Field> randomFields=new ArrayList<>();
            Random random = new Random();
            for(int i=0;i<allFields.size()/10;i+=10){
                int k=random.nextInt(10)+i;
                randomFields.add(allFields.get(k));
            }
            return Response.buildSuccess(randomFields);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getField_year_change(int mode1,int mode2) {
        try{
            List<Field> fields=new ArrayList<>();
            if(mode1==1)fields=fieldDao.getTopField_paperNum();
            else if(mode1==2)fields=fieldDao.getTopField_citationSum();
            else if(mode1==3)fields=fieldDao.getTopField_point();
            List<String> names=new ArrayList<>();
            List<String> years=new ArrayList<>();
            List<List<Integer>> nums=new ArrayList<>();
            for(int i=2000;i<=2020;i++)years.add(Integer.toString(i));
            for(Field f:fields){
                names.add(f.getField_name());
                int[] num=new int[21];
                for(int j=0;j<21;j++)num[j]=0;
                String s=f.getPaper_list();
                List<Integer> ids=new ArrayList<>();
                for(String ss:s.split(";"))ids.add(Integer.parseInt(ss));
                List<Paper> papers=paperDao.getPapersByIds(ids);
                for(Paper p:papers){
                    String year=p.getPublication_Year();
                    if(year.isEmpty())continue;
                    int d=Integer.parseInt(year);
                    if(d<2000||d>2020)continue;   //多余年份的数据扔掉

                    int target=0;
                    if(mode1==1)target=1;
                    else if(mode1==2)target=Integer.parseInt(p.getArticle_Citation_Count());

                    //  累计到该年
                    if(mode2==1)
                        for(int y=d-2000;y<=20;y++)
                            num[y]+=target;
                    // 只看这一年
                    else if(mode2==2)
                        num[d-2000]+=target;
                }
                List<Integer> temp=new ArrayList<>();
                for (int i : num) temp.add(i);
                nums.add(temp);
            }
            Interest interest=new Interest(-99,years,names,nums);
            return Response.buildSuccess(interest);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getHistory(int id) {
        try {
            String[] temp=fieldDao.getPaperId(id).split(";");
            List<Integer> ids=new ArrayList<>();
            for(String s:temp)if(!s.isEmpty())ids.add(Integer.parseInt(s));
            List<Paper> papers=paperDao.getPapersByIds(ids);//System.out.println(papers.size());
            ///List<SimplePaper> simplePapers=simplePaperDao.getSimplePapersByIds(ids);//System.out.println(simplePapers.size());
            Map<String,Integer> paper_year=new TreeMap<>();
            int fk=0;
            for(Paper sm:papers){
                String year=sm.getPublication_Year();
                //if(year.isEmpty())fk++;
                if(paper_year.containsKey(year))
                    paper_year.put(year,paper_year.get(year)+1);
                else
                    paper_year.put(year,1);
            }//System.out.println(fk);
            List<History> histories=new ArrayList<>();
            for(Map.Entry<String,Integer> a:paper_year.entrySet()){
                histories.add(new History(a.getKey(),a.getValue()));
            }
            return Response.buildSuccess(histories);
        } catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getRank(int id) {
        try {
            List<Field> fields=fieldDao.getFields();
            List<String> ans=new ArrayList<>();
            Field field=fieldDao.getFieldById(id);
            String field_name=field.getField_name();
            for(int i=0;i<fields.size();i++){
                if(fields.get(i).getField_name().equals(field_name)){
                    String s= Integer.toString(i+1)+" / "+Integer.toString(fields.size());
                    ans.add(s);
                    break;
                }
            }
            sortField_citation(fields);
            for(int i=0;i<fields.size();i++){
                if(fields.get(i).getField_name().equals(field_name)){
                    String s= Integer.toString(i+1)+" / "+Integer.toString(fields.size());
                    ans.add(s);
                    break;
                }
            }

            return Response.buildSuccess(ans);
        } catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getRankData(int id) {
        try {
            List<Field> fields=fieldDao.getFields();
            Field field=fieldDao.getFieldById(id);
            String field_name=field.getField_name();
            List<List<Integer>> ans=new ArrayList<>();
            for(int i=0;i<fields.size();i++) {
                if (fields.get(i).getField_name().equals(field_name)) {
                    //System.out.println(i+"  "+name+" "+org+"  "+a.getPaper_num()+"  "+a.getCitation_sum());
                    List<Integer> temp = new ArrayList<>();
                    temp.add(id);
                    temp.add(i+1);
                    ans.add(temp);
                    break;
                }
            }
            for(Field f:fields){
                List<Integer> temp=new ArrayList<>();
                temp.add(f.getPaper_num());temp.add(f.getCitation_sum());
                ans.add(temp);
            }

            return Response.buildSuccess(ans);
        } catch (Exception e) {
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    private static void sortField_citation(List<Field> list){ //按照被引用数降序重拍paper
        Collections.sort(list,new Comparator<Field>() {
            @Override
            public int compare(Field a1,Field a2) {
                return a2.getCitation_sum()-a1.getCitation_sum();
            }
        });
    }

    @Override
    public Response getTopAuthors(int id) {//在本方向 论文数前20的作者
        try{
            String[] temp=fieldDao.getPaperId(id).split(";");
            List<Integer> ids=new ArrayList<>();
            for(String s:temp)if(!s.isEmpty())ids.add(Integer.parseInt(s));//System.out.println(ids.size());
            //List<Author> top20authors=authorDao.getTop20AuthorByPaperId(ids);System.out.println(top20authors.size());

            List<SimpleAuthor> simpleAuthors=authorDao.getSimpleAuthorByPaperId(ids);//System.out.println(simpleAuthors.size());
            Set<SimpleAuthor> userSet = new HashSet<>(simpleAuthors);// 去重
            List<SimpleAuthor> simpleAuthors2 = new ArrayList<>(userSet);//System.out.println(simpleAuthors2.size());
            List<String> name_org=new ArrayList<>();
            for(SimpleAuthor sa:simpleAuthors2)name_org.add(sa.getAuthor_name()+sa.getOrg());
            List<Author> realAuthors=authorDao.getAuthors_concat_name_org(name_org);//System.out.println(realAuthors.size());
            //for(SimpleAuthor sa:simpleAuthors)System.out.println(sa.getAuthor_name()+"  "+sa.getOrg());

            return Response.buildSuccess(realAuthors);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getTopOrgs(int id) {//在本方向 论文数前20的机构
        try{
            String[] temp=fieldDao.getPaperId(id).split(";");
            List<Integer> ids=new ArrayList<>();
            for(String s:temp)if(!s.isEmpty())ids.add(Integer.parseInt(s));
            List<String> org_name=orgDao.getOrg_nameByPaperId(ids);
            org_name=org_name.stream().distinct().collect(Collectors.toList());//去重
            List<Org> orgs=orgDao.getOrgsByNameList(org_name);

            return Response.buildSuccess(orgs);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }

    @Override
    public Response getTopPapers(int id) {//某方向被引用数排名前20论文
        try{
            String[] temp=fieldDao.getPaperId(id).split(";");
            List<Integer> ids=new ArrayList<>();
            for(String s:temp)if(!s.isEmpty())ids.add(Integer.parseInt(s));
            List<Paper> Papers=paperDao.getTopPaper(ids);
            return Response.buildSuccess(Papers);
        }catch (Exception e){
            e.printStackTrace();
            return (Response.buildFailure("失败"));
        }
    }





}
