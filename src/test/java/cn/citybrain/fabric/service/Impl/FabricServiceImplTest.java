package cn.citybrain.fabric.service.Impl;

import cn.citybrain.fabric.config.OrgListConfig;
import cn.citybrain.fabric.entity.AppUser;
import cn.citybrain.fabric.entity.OrgConfig;
import cn.citybrain.fabric.utils.UserUtils;
import com.citybrain.blockchain.sdk.base.cfg.ApplicationConfig;
import com.citybrain.blockchain.sdk.base.entity.ShuttleUser;
import com.citybrain.blockchain.sdk.base.exception.EBizCode;
import com.citybrain.blockchain.sdk.base.exception.ShuttleException;
import com.citybrain.blockchain.sdk.base.util.FabricCaUtil;
import com.sun.deploy.ui.AppInfo;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.identity.X509Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.*;
import org.hyperledger.fabric_ca.sdk.exception.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * created by yeric on 2019/12/1
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FabricServiceImplTest {
    @Autowired
    private HFCAClient hfcaClient;
    @Autowired
    private OrgListConfig orgListConfig;
    private final Map<String, AppUser> members = new HashMap<>();

    @Autowired
    private FabricServiceImpl fabricServiceImpl;

    /**
     * lihua1/YLZWDHnGZkls
     * lihua2/tfOqfDBDBlgc
     * 113服务器 yeric/bCzmvWrUdIKu  yeric2/ZdAPsnlnSplu
     */
    @Test
    public void testRegister() throws IOException {
        AppUser register = new AppUser();
        AppUser registar = new AppUser();
        try {
            register.setName("yerikm1");
            register.setAffiliation("hzssjzyj");
            Enrollment enrollment = fabricServiceImpl.enroll("admin", "adminpw");
            registar.setName("admin");
            registar.setAffiliation("hzssjzyj");
            registar.setEnrollment(enrollment);
            // the enrollment secret
            String secret = fabricServiceImpl.register(registar, register);
            System.out.println("secret：" + secret);
        } catch (Exception e) {
            throw new RuntimeException("注册失败");
        }
    }

    @Test
    public void enroll() throws EnrollmentException, InvalidArgumentException {
        Enrollment enroll = fabricServiceImpl.enroll("wugj", "xNkbjQiECFph");

        System.out.println("key:   " + enroll.getKey());
        System.out.println("certificate:   " + enroll.getCert());
    }

    @Test
    public void testOther() throws Exception {
        HFCACertificateRequest certReq = hfcaClient.newHFCACertificateRequest();
//        certReq.setEnrollmentID("fabricKL17rC0aOm");
        AppUser registar = new AppUser();
        registar.setName("admin");
        Enrollment enrollment = fabricServiceImpl.enroll("admin", "adminpw");
        registar.setEnrollment(enrollment);
        X509Certificate cert = getCert(enrollment.getCert().getBytes());
        HFCAAffiliation hfcaAffiliations = hfcaClient.getHFCAAffiliations(registar);
        HFCACertificateResponse resp = hfcaClient.getHFCACertificates(registar, certReq);
        String caName = hfcaClient.getCAName();
        CryptoSuite cryptoSuite = hfcaClient.getCryptoSuite();
        Collection<HFCAIdentity> hfcaIdentities = hfcaClient.getHFCAIdentities(registar);
        HFCAInfo info = hfcaClient.info();
//        HFCAIdentity hfcaIdentity = hfcaClient.newHFCAIdentity("wugj129999999958855");
        HFCAAffiliation hfcaAffiliation = hfcaClient.newHFCAAffiliation("org1");
//        int read = hfcaAffiliation.read(registar);
//        int i = hfcaIdentity.create(registar);
        HFCACertificateResponse hfcaCertificates = hfcaClient.getHFCACertificates(registar, certReq);
//        hfcaClient.revoke(registar, "fabric-name", "【test】revoke one user (including his all enrollments)");
//        HFCACertificateRequest hfcaCertificateRequest = hfcaClient.newHFCACertificateRequest();
//        hfcaCertificateRequest.setEnrollmentID("sycj");
//        HFCAIdentity hfcaIdentity = hfcaClient.newHFCAIdentity("fabric-name");
//        int delete = hfcaIdentity.delete(registar);
//        System.out.println(delete);
//        Enrollment enroll = hfcaClient.enroll("yeric", "bCzmvWrUdIKu");
//        certReq.setEnrollmentID("yeric");
//        RegistrationRequest testRegisterReq = new RegistrationRequest("wugj", "org1");
//        hfcaClient.revoke(registar, "fabricP0DPGVE6Pm", "test撤销用户和所有证书");
        System.out.println();
    }

    @Test
    public void reenroll() throws EnrollmentException, InvalidArgumentException, CertificateException, HFCACertificateException {
        List<OrgConfig> orgs = orgListConfig.getOrgs();
        System.out.println();
        EnrollmentRequest testEnrollReq = new EnrollmentRequest();
        HFCACertificateRequest certReq = hfcaClient.newHFCACertificateRequest();
        certReq = hfcaClient.newHFCACertificateRequest();
        AppUser registar = new AppUser();
        registar.setName("admin");
        Enrollment enrollment = fabricServiceImpl.enroll("admin", "adminpw");
        registar.setEnrollment(enrollment);
        HFCACertificateResponse hfcaCertificates = hfcaClient.getHFCACertificates(registar, certReq);
        System.out.println();
       /* AppUser registar = new AppUser();
        Enrollment enroll = hfcaClient.enroll("admin", "adminpw");
        registar.setEnrollment(enroll);
        Enrollment reenroll = hfcaClient.reenroll(registar);
        System.out.println("key:   " + reenroll.getKey());
        System.out.println("certificate:   " + reenroll.getCert());*/
    }

    public static X509Certificate getCert(byte[] certBytes) throws CertificateException {
        FabricCaUtil instance = FabricCaUtil.getInstance();
        BufferedInputStream pem = new BufferedInputStream(new ByteArrayInputStream(certBytes));
        CertificateFactory certFactory = CertificateFactory.getInstance("X509");
        X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(pem);
        return certificate;
    }

    /**
     * 判断ca用户是否存在（基于admin允许查看的所有身份）
     *
     * @return
     */
    @Test
    public void userIsPresent() throws EnrollmentException, InvalidArgumentException, IdentityException {
        String userName = "DDDD";
        AppUser registar = new AppUser();
        registar.setName("admin");
        Enrollment enrollment = fabricServiceImpl.enroll("admin", "adminpw");
        registar.setEnrollment(enrollment);
        Collection<HFCAIdentity> hfcaIdentities = hfcaClient.getHFCAIdentities(registar);
        ArrayList<String> list = new ArrayList<>();
//        hfcaIdentities.forEach(caIdentity -> {
//            list.add(caIdentity.getEnrollmentId());
//        });
        System.out.println(list.contains(userName));
    }
}