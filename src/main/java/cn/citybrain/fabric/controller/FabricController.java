package cn.citybrain.fabric.controller;

import cn.citybrain.fabric.entity.AppUser;
import cn.citybrain.fabric.service.FabricService;
import cn.citybrain.fabric.utils.UserUtils;
import org.bouncycastle.crypto.CryptoException;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * created by yeric on 2019/11/28
 */
@Controller
@RequestMapping("fabric/ca/user")
public class FabricController {
    @Autowired
    private FabricService fabricServiceImpl;
    @Value("${com.yunyang.fabricClient.certificate.keyFolderPath}")
    private String keyFolderPath;
    @Value("${com.yunyang.fabricClient.certificate.keyFileName}")
    private String keyFileName;
    @Value("${com.yunyang.fabricClient.certificate.certFoldePath}")
    private String certFoldePath;
    @Value("${com.yunyang.fabricClient.certificate.certFileName}")
    private String certFileName;
    @Value("${com.yunyang.fabricClient.certificate.tlsOrderFilePath}")
    private String tlsOrderFilePath;
    @Value("${com.yunyang.fabricClient.certificate.txfilePath}")
    private String txfilePath;
    @Value("${com.yunyang.fabricClient.certificate.tlsPeerFilePath}")
    private String tlsPeerFilePath;

    @PostMapping(value = "createChannel")
    public Channel createChannel () throws InvalidKeySpecException, NoSuchAlgorithmException, CryptoException, IOException, InvalidArgumentException, TransactionException, ProposalException {
        AppUser appUser = new AppUser();
        appUser.setAffiliation("Org1");
        appUser.setMspId("Org1MSP");
        appUser.setAccount("李伟");
        appUser.setName("admin");
        Enrollment enrollment =  UserUtils.getEnrollment(keyFolderPath,keyFileName,certFoldePath,certFileName);
        appUser.setEnrollment(enrollment);
        Orderer orderer = fabricServiceImpl.getOrderer("orderer.example.com", "grpcs://orderer.example.com:7051", tlsOrderFilePath, appUser);
        Channel channel = fabricServiceImpl.createChannel("testChannel", orderer, txfilePath, appUser);
        channel.addOrderer(fabricServiceImpl.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",tlsOrderFilePath, appUser));
        channel.joinPeer(fabricServiceImpl.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath, appUser));
        channel.initialize();
        return channel;
    }
    @PostMapping(value = "register")
    public String register () {
        AppUser register = new AppUser();
        AppUser registar = new AppUser();
        try {
            register.setName("lihua");
            register.setAffiliation("org1");
            Enrollment enrollment = fabricServiceImpl.enroll("admin","adminpw");
            registar.setName("admin");
            registar.setAffiliation("org1");
            registar.setEnrollment(enrollment);
            return fabricServiceImpl.register(registar, register);
        } catch (Exception e) {
            throw new RuntimeException("注册失败");
        }
    }
}
