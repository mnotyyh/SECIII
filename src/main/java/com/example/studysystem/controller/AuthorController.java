package com.example.studysystem.controller;
//qwrfqet
import com.example.studysystem.entity.Response;

import com.example.studysystem.service.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping(value = {"/author"})
public class AuthorController {
//    aaa
    @Autowired
    private AuthorService authorService;
    public void set(AuthorService authorService){
        this.authorService=authorService;
    }

    @ResponseBody
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Response getAuthors(){//System.out.println(21);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        return authorService.getAuthors();
    }

    @ResponseBody
    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    public Response getAuthorById(@RequestParam("id")int id){//System.out.println(21);
        return authorService.getAuthorById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/getIdByAuthorAndOrg",method = RequestMethod.GET)
    public Response getIdByAuthorAndOrg(@RequestParam("Author")String Author,@RequestParam("Org")String Org){//System.out.println(21);
        return authorService.getIdByAuthorAndOrg(Author,Org);
    }

    @ResponseBody
    @RequestMapping(value = "/getTopPaper",method = RequestMethod.GET)
    public Response getTopPaper(@RequestParam("id")int id){
        return authorService.getTopPaper(id);
    }
    @ResponseBody
    @RequestMapping(value = "/getTopKeyword",method = RequestMethod.GET)
    public Response getTopKeyword(@RequestParam("id")int id){
        return authorService.getTopKeyword(id);
    }

    @ResponseBody
    @RequestMapping(value = "/getSimplepaperById",method = RequestMethod.GET)
    public Response getSimplePaperByOrg(@RequestParam("id")int id){//System.out.println(28);
        //Response flag=paperService.plugPapers();System.out.println(flag);
        return authorService.getSimplePaperByAuthor(id);
    }

    @ResponseBody
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Response searchAuthors(@RequestParam("mode") int mode, @RequestParam("sort") int sort,@RequestParam("str") String str){
        //Response flag=paperService.plugPapers();System.out.println(flag);
        return authorService.searchAuthors(mode,sort,str);
    }

    @ResponseBody
    @RequestMapping(value = "/getRelatedAuthors",method = RequestMethod.GET)
    public Response getRelatedAuthors(@RequestParam("id")int id,@RequestParam("mode")int mode){
        return authorService.getRelatedAuthors(id,mode);
    }
    @ResponseBody
    @RequestMapping(value = "/getRelatedOrgs",method = RequestMethod.GET)
    public Response getRelatedOrgs(@RequestParam("id")int id){
        return authorService.getRelatedOrgs(id);
    }

    @ResponseBody
    @RequestMapping(value = "/rating", method = RequestMethod.GET)
    public Response getTop10Author(@RequestParam("methodId") int methodId){
        return authorService.getTop10Author(methodId);
    }


    @ResponseBody
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public Response getHistory(@RequestParam("id") int id){
        return authorService.getHistory(id);
    }

    @ResponseBody
    @RequestMapping(value = "/interest", method = RequestMethod.GET)
    public Response getInterest(@RequestParam("id") int id){
        return authorService.getInterest2(id);
    }

    @ResponseBody
    @RequestMapping(value = "/getRecentById", method = RequestMethod.GET)
    public Response getRencent(@RequestParam("id") int id,@RequestParam("mode") int mode){
        return authorService.getRencent(id,mode);
    }

    @ResponseBody
    @RequestMapping(value = "/interest_change", method = RequestMethod.GET)
    public Response getInterest_change(@RequestParam("id") int id){
        return authorService.getInterest3(id);
    }

    @ResponseBody
    @RequestMapping(value = "/meeting", method = RequestMethod.GET)
    public Response getMeeting(@RequestParam("id") int id){
        return authorService.getMeetings(id);
    }

    @ResponseBody
    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public Response getRank(@RequestParam("id") int id,@RequestParam("mode") int mode){
        return authorService.getRank(id,mode);
    }
    @ResponseBody
    @RequestMapping(value = "/rankData", method = RequestMethod.GET)
    public Response getRankData(@RequestParam("id") int id,@RequestParam("mode") int mode){
        return authorService.getRankData(id,mode);
    }
}