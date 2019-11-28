package cn.citybrain.fabric.controller;

import cn.citybrain.fabric.entity.AppUser;
import cn.citybrain.fabric.service.FabricService;
import org.hyperledger.fabric.sdk.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * created by yeric on 2019/11/28
 */
@Controller
@RequestMapping("fabric/ca/user")
public class FabricController {
    @Autowired
    private FabricService fabricServiceImpl;

    @PostMapping(value = "register")
    public String register () {
        AppUser register = new AppUser();
        AppUser registar = new AppUser();
        try {
            register.setName("lihua");
            register.setAffiliation("org2");
            Enrollment enrollment = fabricServiceImpl.enroll("admin","adminpw");
            registar.setName("admin");
            registar.setAffiliation("org2");
            registar.setEnrollment(enrollment);
            return fabricServiceImpl.register(registar, register);
        } catch (Exception e) {
            throw new RuntimeException("注册失败");
        }
    }
}
