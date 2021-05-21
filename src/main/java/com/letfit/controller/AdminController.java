package com.letfit.controller;

import com.letfit.aop.annotation.CheckRepeatSubmit;
import com.letfit.aop.annotation.NullCheck;
import com.letfit.pojo.entity.Admin;
import com.letfit.pojo.entity.Module;
import com.letfit.pojo.dto.ResultInfo;
import com.letfit.pojo.entity.Source;
import com.letfit.service.AdminService;
import com.letfit.common.CodeEnum;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author cjt
 * @date 2021/4/8 16:39
 */
@Api(tags = "管理员操作")
@Controller
@Validated
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    /**
     * 管理员登录
     * @param adminName
     * @param password
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String login(@NullCheck String adminName, @NullCheck String password, Model model){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户登录信息
        UsernamePasswordToken token = new UsernamePasswordToken(adminName,password);
        subject.login(token);
        try{
            //执行登录方法
            subject.login(token);
            return "function";
        }catch (UnknownAccountException e){
            //用户名不存在
            model.addAttribute("msg","管理员名错误");
            return "index";
        }catch (IncorrectCredentialsException e){
            //密码不存在
            model.addAttribute("msg","密码错误");
            return "index";
        }
    }

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @ResponseBody
    @PostMapping("/addAdmin")
    public ResultInfo<?> addAdmin(@Valid Admin admin){
        adminService.insertAdmin(admin);
        return ResultInfo.success(CodeEnum.SUCCESS,"添加成功");
    }

    /**
     * 上传视频
     */
    @CheckRepeatSubmit
    @ResponseBody
    @PostMapping("/addSource")
    public ResultInfo<Source> addSource(@RequestParam("moduleName") String moduleName,
                                        @RequestParam("sourceTitle") String sourceTitle,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return ResultInfo.error(CodeEnum.FILE_UPLOAD_FAIL);
        }
        //文件名
        String fileName = file.getOriginalFilename();
        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
        BufferedInputStream bufferedInputStream =new BufferedInputStream(fileInputStream);
        //文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        return adminService.addSource(moduleName, sourceTitle, bufferedInputStream, suffixName);
    }

    /**
     * 上传视频封面
     */
    @ResponseBody
    @PostMapping("/uploadSourceCover")
    public ResultInfo<Source> uploadSourceCover(@RequestParam("sourceId") Integer sourceId,
                                                @RequestParam("file") MultipartFile file) throws IOException {

        if(file.isEmpty()){
            return ResultInfo.error(CodeEnum.FILE_UPLOAD_FAIL);
        }
        //文件名
        String fileName = file.getOriginalFilename();
        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
        BufferedInputStream bufferedInputStream =new BufferedInputStream(fileInputStream);
        //文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        return adminService.uploadSourceCover(sourceId,bufferedInputStream,suffixName);
    }

    /**
     * 文件上传
     */
    @ResponseBody
    @PostMapping("/fileUpload")
    public ResultInfo<Module> fileUpload(@RequestParam("name") String name,
                                         @RequestParam("file") MultipartFile file) throws IOException {

        if(file.isEmpty()){
            return ResultInfo.error(CodeEnum.FILE_UPLOAD_FAIL);
        }
        //文件名
        String fileName = file.getOriginalFilename();
        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
        BufferedInputStream bufferedInputStream =new BufferedInputStream(fileInputStream);
        //文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        return adminService.addModule(name,bufferedInputStream,suffixName);
    }


    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/function")
    public String function(){
        return "function";
    }

    @GetMapping("/addModule")
    public String addModule(){
        return "addModule";
    }

    @GetMapping("/addSource")
    public String addSource(){
        return "addSource";
    }

    @GetMapping("/userManager")
    public String userManager(){
        return "addAdmin";
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "index";
    }

}
