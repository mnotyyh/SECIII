package com.example.studysystem.controller;

import com.example.studysystem.entity.Response;
import com.example.studysystem.service.field.FieldService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = {"/field"})
public class FieldController {
    @Autowired
    private FieldService fieldService;
    public void set(FieldService f){this.fieldService=f;}

    @ResponseBody
    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    public Response getById(@Param("id") int id){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        return fieldService.getFieldById(id);
    }
    @ResponseBody
    @RequestMapping(value = "/getIdByName",method = RequestMethod.GET)
    public Response getIdByName(@Param("name") String name){
        return fieldService.getIdByName(name);
    }

    @ResponseBody
    @RequestMapping(value = "/getTop20",method = RequestMethod.GET)
    public Response getTop10Field(@Param("mode") int mode){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        return fieldService.getTop20Field(mode);
    }

    @ResponseBody
    @RequestMapping(value = "/getTopAuthors",method = RequestMethod.GET)
    public Response getTopAuthors(@Param("id") int id){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        return fieldService.getTopAuthors(id);
    }

    @ResponseBody
    @RequestMapping(value = "/getTopOrgs",method = RequestMethod.GET)
    public Response getTopOrgs(@Param("id") int id){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        return fieldService.getTopOrgs(id);
    }

    @ResponseBody
    @RequestMapping(value = "/getTopPapers",method = RequestMethod.GET)
    public Response getTopPapers(@Param("id") int id){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        return fieldService.getTopPapers(id);
    }

    @ResponseBody
    @RequestMapping(value = "/history",method = RequestMethod.GET)
    public Response getHistory(@Param("id") int id){
        return fieldService.getHistory(id);
    }

    @ResponseBody
    @RequestMapping(value = "/rank",method = RequestMethod.GET)
    public Response getRank(@Param("id") int id){
        return fieldService.getRank(id);
    }

    @ResponseBody
    @RequestMapping(value = "/rankData",method = RequestMethod.GET)
    public Response getRankData(@Param("id") int id){
        return fieldService.getRankData(id);
    }

    @ResponseBody
    @RequestMapping(value = "/cloud",method = RequestMethod.GET)
    public Response getCloud(){
        return fieldService.getCloud();
    }

    @ResponseBody
    @RequestMapping(value = "/year_change",method = RequestMethod.GET)
    public Response getField_year_change(@Param("mode1") int mode1,@Param("mode2") int mode2){
        return fieldService.getField_year_change(mode1, mode2);
    }
}
