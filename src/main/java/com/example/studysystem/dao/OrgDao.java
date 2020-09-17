package com.example.studysystem.dao;

import com.example.studysystem.entity.History;
import com.example.studysystem.entity.Org;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrgDao {
    Org getOrgById(@Param("id") int id);
    //根据名字获得机构，只要机构名字包含搜索子串，无视大小写,返回的机构列表按字母序
    List<Org> getOrgsByname(@Param("name") String name);
    //根据名字获得机构，机构名字 = 搜索子串，无视大小写,返回的机构列表按字母序,应该不会有机构重名吧。。

    Integer getIdByOrgName(@Param("name") String name);//根据名字得到id
    List<String> getOrg_nameByPaperId(List<Integer> id);// 获得 发过这些论文(>=1篇)的机构名字
    List<Org> getOrgsByNameList(List<String> name); // 找和这些名字对应的机构,论文数降序
    Org getOrgByname(@Param("name") String name);
    List<Org> getOrgs();
    List<Org> searchOrgs(@Param("name") String name, @Param("num") int num);
    String  getPaperIdByOrg(@Param("id") int id);

    List<Integer> getTopPaperIds(@Param("id") int id);   //某个机构所有文章引用数最多的前五
    List<String> getKeywords(@Param("id") int id);
    List<String> getTitles(@Param("id") int id);
    String getAuthors(@Param("id") int id);
    List<Org> getTopOrg_paperNum();    // 论文数前10 机构
    List<Org> getTopOrg_citationSum();   //引用数前10 机构
    List<Org> getTopOrg_point();   // 综合排名前10  70%论文数 30%引用数   机构
    List<Org> getTopOrg(List<Integer> ids);//  同上，参数不同
    List<String> getRelatedOrg_byAuthorId(int id);
    List<String> getRelatedOrg_byOrgId(int id);
    List<History> getHistory(int id);  //某个作者 按照年份发的论文数  {2015，5}，{2019，8}。。
}
