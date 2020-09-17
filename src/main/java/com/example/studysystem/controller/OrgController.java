package com.example.studysystem.controller;


import com.example.studysystem.entity.Response;
import com.example.studysystem.service.org.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = {"/org"})
public class OrgController {
    @Autowired
    private OrgService orgService;
    public void set(OrgService orgService){this.orgService=orgService;}

    @ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Response getOrgs(){
        //Response flag=paperService.plugPapers();System.out.println(flag);
        return orgService.getOrgs();
    }

    @ResponseBody
    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    public Response getOrgById(@RequestParam("id")int id){
        return orgService.getOrgById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/getIdByOrgName",method = RequestMethod.GET)
    public Response getIdByOrgName(@RequestParam("name")String name){//System.out.println(name);
        return orgService.getIdByOrgName(name);
    }
    @ResponseBody
    @RequestMapping(value = "/getByName",method = RequestMethod.GET)
    public Response getOrgById(@RequestParam("name")String name , @RequestParam("mode")int mode){
        return orgService.getOrgsByname(name,mode);
    }

    @ResponseBody
    @RequestMapping(value = "/getSimplepaperById",method = RequestMethod.GET)
    public Response getSimplePaperByOrg(@RequestParam("id")int id){//System.out.println(28);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        return orgService.getSimplePaperByOrg(id);
    }


    @ResponseBody
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Response searchOrgs(@RequestParam("name") String name,@RequestParam("num") String num){
        //Response flag=paperService.plugPapers();System.out.println(flag);
        return orgService.searchOrgs(name,num);
    }


    @ResponseBody
    @RequestMapping(value = "/getTopPaper",method = RequestMethod.GET)
    public Response getTopPaper(@RequestParam("id") int id){
        return orgService.getTopPaper(id);
    }
    @ResponseBody
    @RequestMapping(value = "/topKeyword",method = RequestMethod.GET)
    public Response getTopKeyword(@RequestParam("id") int id){
        return orgService.getTopKeyword(id);
    }
    @ResponseBody
    @RequestMapping(value = "/getRelatedAuthors",method = RequestMethod.GET)
    public Response getRelatedAuthors(@RequestParam("id") int id){
        return orgService.getRelatedAuthors(id);
    }    @ResponseBody
    @RequestMapping(value = "/getRelatedOrgs",method = RequestMethod.GET)
    public Response getRelatedOrgs(@RequestParam("id") int id){
        return orgService.getRelatedOrgs(id);
    }

    @ResponseBody
    @RequestMapping(value = "/topAuthor",method = RequestMethod.GET)
    public Response getTopAuthor(@RequestParam("id") int id){
        return orgService.getTopAuthor(id);
    }

    @ResponseBody
    @RequestMapping(value = "/rating", method = RequestMethod.GET)
    public Response getTop10Author(@RequestParam("methodId") int methodId){
        return orgService.getTop10Org(methodId);
    }

    @ResponseBody
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public Response getHistory(@RequestParam("id") int id){
        return orgService.getHistory(id);
    }

    @ResponseBody
    @RequestMapping(value = "/interest", method = RequestMethod.GET)
    public Response getInterest(@RequestParam("id") int id){
        return orgService.getInterest(id);
    }
}
