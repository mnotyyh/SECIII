package com.example.studysystem.service.author;

import com.example.studysystem.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService  {
    //author表功能
    Response getAuthors(); // 获得全部作者列表,论文数降序
    Response getAuthorById(int id); // 根据id获得某个作者
    Response getIdByAuthorAndOrg(String Author,String Org); //根据名字和机构获得id
    Response getSimplePaperByAuthor(int id);
    Response searchAuthors(int mode,int sort,String str);

    // authorDetail 功能
    Response getTopPaper(int id);
    Response getTopKeyword(int id);
    Response getTop10Author(int mode);
    Response getRelatedAuthors(int id,int mode);
    Response getRelatedOrgs(int id);
    Response getHistory(int id);
    Response getInterest(int id); // 从标题中找兴趣，合理性有待商榷
    Response getInterest2(int id); // 从论文关键词找兴趣，兴趣=领域？

    // todo
    Response getRencent(int id,int mode); // 获得最近的论文和最近的研究方向  mode =1最近方向，mode=2最近论文

    Response getInterest3(int id); // 按年份统计兴趣
    Response getMeetings(int id);// 获得这个作者参加过的所有会议
    Response getRank(int id,int mode);//获得这个作者的学术排名，mode=0 同机构，mode=1 所有机构，返回一个大小为2的list<string>
    Response getRankData(int id,int mode);//mode=0同机构，mode=1所有，返回Author的论文数和被引用数两项，按论文数降序
}
