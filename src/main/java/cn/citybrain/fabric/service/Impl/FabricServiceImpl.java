package cn.citybrain.fabric.service.Impl;

import cn.citybrain.fabric.entity.AppUser;
import cn.citybrain.fabric.service.FabricService;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by yeric on 2019/11/28
 */
@Service
public class FabricServiceImpl implements FabricService {

    @Autowired
    private HFCAClient hfcaClient;

    public String register(AppUser registar, AppUser register) throws Exception {
        System.out.println(hfcaClient);
        RegistrationRequest request = new RegistrationRequest(register.getName(),register.getAffiliation());
        return hfcaClient.register(request,registar);
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
