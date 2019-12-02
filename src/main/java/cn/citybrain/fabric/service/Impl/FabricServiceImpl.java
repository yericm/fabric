package cn.citybrain.fabric.service.Impl;

import cn.citybrain.fabric.entity.AppUser;
import cn.citybrain.fabric.service.FabricService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * created by yeric on 2019/11/28
 */
@Service
@Slf4j
public class FabricServiceImpl implements FabricService {

    @Autowired
    private HFCAClient hfcaClient;
    @Autowired
    private HFClient hfClient;


    public Channel createChannel(String channelName, Orderer order, String txPath, AppUser userInfo) throws IOException, org.hyperledger.fabric.sdk.exception.InvalidArgumentException, TransactionException {
        hfClient.setUserContext(userInfo);
        ChannelConfiguration channelConfiguration = new ChannelConfiguration(new File(txPath));
        return hfClient.newChannel(channelName, order, channelConfiguration, hfClient.getChannelConfigurationSignature(channelConfiguration, hfClient.getUserContext()));
    }

    public Orderer getOrderer(String name, String grpcUrl, String tlsFilePath,AppUser userInfo) throws org.hyperledger.fabric.sdk.exception.InvalidArgumentException {
        Properties properties = new Properties();
        properties.setProperty("pemFile",tlsFilePath);
        hfClient.setUserContext(userInfo);
        Orderer orderer = hfClient.newOrderer(name,grpcUrl,properties);
        return orderer;
    }

    public Peer getPeer(String name, String grpcUrl, String tlsFilePath,AppUser userInfo) throws org.hyperledger.fabric.sdk.exception.InvalidArgumentException {
        Properties properties = new Properties();
        properties.setProperty("pemFile",tlsFilePath);
        hfClient.setUserContext(userInfo);
        Peer peer = hfClient.newPeer(name,grpcUrl,properties);
        return peer;
    }

    public String register(AppUser registar, AppUser register) throws Exception {
        RegistrationRequest request = new RegistrationRequest(register.getName(),register.getAffiliation());
        String secret = hfcaClient.register(request, registar);
        return secret;
    }

    /**
     * @description 登录用户（拿到了证书私钥就可以操作合约等）
     * @param username
     * @param password
     * @return enrollment 从CA服务器端拿到私钥和证书信息到java对象中
     * @throws EnrollmentException
     * @throws org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException
     */
    public Enrollment enroll(String username, String password) throws EnrollmentException, InvalidArgumentException {
        Enrollment enrollment =  hfcaClient.enroll(username,password);
        return enrollment;
    }
}
