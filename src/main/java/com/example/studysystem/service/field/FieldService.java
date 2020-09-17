package com.example.studysystem.service.field;

import com.example.studysystem.entity.Response;
import org.springframework.stereotype.Service;

@Service
public interface FieldService {
    //field页面功能
    Response getFields();
    Response getTop20Field(int mode);
    Response getIdByName(String name);
    Response getFieldById(int id);
    Response getCloud();// 随机获得1/10 数量的研究方向
    Response getField_year_change(int mode1,int mode2); // 获得从2000-2020，每一年论文数前20的方向
    // mode1 分别选择  论文数  被引用数     mode2选择 累计 还是 单年

    //fieldDetail页面功能
    Response getHistory(int id);// 该方向发论文历史
    Response getRank(int id);// 该方向排名
    Response getRankData(int id);//排好序的所有field的论文数和被引用数list，论文数排序，第一个List来指示当前field在排好序的第几个
    Response getTopAuthors(int id); // 发论文最多10作者，//不限量，不然得不到总数，到js那边显示时候再限前20
    Response getTopOrgs(int id); // 发论文最多10机构
    Response getTopPapers(int id);//引用数前10文章

}
