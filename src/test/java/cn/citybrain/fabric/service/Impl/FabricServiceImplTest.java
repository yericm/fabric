package cn.citybrain.fabric.service.Impl;

import cn.citybrain.fabric.entity.AppUser;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * created by yeric on 2019/12/1
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FabricServiceImplTest {
    @Autowired
    private HFCAClient hfcaClient;

    @Autowired
    private FabricServiceImpl fabricServiceImpl;

    /**
     * lihua1/YLZWDHnGZkls
     * lihua2/tfOqfDBDBlgc
     * 113服务器 yeric/bCzmvWrUdIKu
     */
    @Test
    public void testRegister() {
        AppUser register = new AppUser();
        AppUser registar = new AppUser();
        try {
            register.setName("lihua2");
            register.setAffiliation("HZSZF");
            Enrollment enrollment = fabricServiceImpl.enroll("admin", "adminpw");
            registar.setName("admin");
            registar.setAffiliation("HZSZF");
            registar.setEnrollment(enrollment);
            String secret = fabricServiceImpl.register(registar, register);
            System.out.println("secret：" + secret);
        } catch (Exception e) {
            throw new RuntimeException("注册失败");
        }
    }

    @Test
    public void enroll() throws EnrollmentException, InvalidArgumentException {
        Enrollment enroll = fabricServiceImpl.enroll("yeric", "bCzmvWrUdIKu");
        System.out.println("key:   " + enroll.getKey());
        System.out.println("certificate:   " + enroll.getCert());
    }
}