package com.example.studysystem.dao;
import com.example.studysystem.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface AuthorDao {
    List<Author> getAuthors(); //获得所有作者，按论文数降序
    List<Author> getAuthorsByOrg(@Param("org") String org);//获得同机构作者，按论文数降序
    Author getAuthorById(@Param("id")int id);
    Integer getIdByAuthorAndOrg(@Param("Author") String Author,@Param("Org") String Org);
    List<Author> searchAuthors_name(@Param("name") String name);
    List<Author> searchAuthors_org(@Param("org") String org);
    List<Author> searchAuthors_paperNum(@Param("paperNum") int paperNum);
    List<Author> searchAuthors_citationSum(@Param("citationSum") int citationSum);

    String  getPaperIdByAuthor(@Param("id") int id);

    List<String> getKeywords(@Param("id") int id);
    List<String> getTitles(@Param("id") int id);
    List<Author> getTopAuthor_paperNum();    // 论文数前10 作者
    List<Author> getTopAuthor_citationSum();   //引用数前10 作者
    List<Author> getTopAuthor_point();   // 综合排名前10  70%论文数 30%引用数   作者
    List<Author> getTopAuthor_byName(List<String> name);//  同上，参数不同
    List<Author> getTopAuthor_byId(List<Integer> id);//  同上，参数不同


    List<Author> getRelatedAuthor_byPaperId(List<Integer> id); // 一起发过文章的作者,按发文量降序
    List<String> getRelatedAuthor_byAuthorId_orgDif(List<Integer> id,String org,String name); // 和这个作者一起发过论文的其他机构的作者，不限，根据一起发过的论文数量降序
    List<String> getRelatedAuthor_byOrgId(int id);// 和这个作者一起发过论文的机构，前五，根据一起发过的论文数量降序
    List<History> getHistory(int id);  //某个作者 按照年份发的论文数  {2015，5}，{2019，8}。。。

    List<Author> getTop20AuthorByPaperId(List<Integer> id);// 给定论文id，找发过这些论文的，发文量前20作者
    List<SimpleAuthor> getSimpleAuthorByPaperId(List<Integer> id); // 只获得作者的名字和机构
    List<Author> getAuthors_concat_name_org(List<String> name_org); // 传入作者名字和机构的拼接字符串，找这两个属性都符合的作者，避免传入2个List
}
