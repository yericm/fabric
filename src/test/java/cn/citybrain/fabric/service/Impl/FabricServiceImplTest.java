package cn.citybrain.fabric.service.Impl;

import cn.citybrain.fabric.config.OrgListConfig;
import cn.citybrain.fabric.entity.AppUser;
import cn.citybrain.fabric.entity.OrgConfig;
import com.sun.deploy.ui.AppInfo;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
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
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void testRegister() {
        AppUser register = new AppUser();
        AppUser registar = new AppUser();
        try {
            register.setName("yeric3");
            register.setAffiliation("org1.department1");
            Enrollment enrollment = fabricServiceImpl.enroll("admin", "adminpw");
            registar.setName("admin");
            registar.setAffiliation("org1");
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
        Enrollment enroll = fabricServiceImpl.enroll("admin", "adminpw");

        System.out.println("key:   " + enroll.getKey());
        System.out.println("certificate:   " + enroll.getCert());
    }
    @Test
    public void testOther () throws InvalidArgumentException, AffiliationException, EnrollmentException, IdentityException, InfoException, HFCACertificateException {
        HFCACertificateRequest certReq = hfcaClient.newHFCACertificateRequest();
        AppUser registar = new AppUser();
        registar.setName("admin");
        Enrollment enrollment = fabricServiceImpl.enroll("admin", "adminpw");
        registar.setEnrollment(enrollment);
        HFCAAffiliation hfcaAffiliations = hfcaClient.getHFCAAffiliations(registar);
        HFCACertificateResponse resp = hfcaClient.getHFCACertificates(registar, certReq);
        String caName = hfcaClient.getCAName();
        CryptoSuite cryptoSuite = hfcaClient.getCryptoSuite();
        Collection<HFCAIdentity> hfcaIdentities = hfcaClient.getHFCAIdentities(registar);
        HFCAInfo info = hfcaClient.info();
        HFCAAffiliation hfcaAffiliation = hfcaClient.newHFCAAffiliation("org1");
        Enrollment enroll = hfcaClient.enroll("yeric", "bCzmvWrUdIKu");
//        certReq.setEnrollmentID("yeric");
        HFCACertificateResponse certG = hfcaClient.getHFCACertificates(registar, certReq);
        System.out.println();
    }
    @Test
    public void reenroll () throws EnrollmentException, InvalidArgumentException, CertificateException, HFCACertificateException {
        List<OrgConfig> orgs = orgListConfig.getOrgs();
        System.out.println();
        EnrollmentRequest testEnrollReq = new EnrollmentRequest();
        HFCACertificateRequest certReq = hfcaClient.newHFCACertificateRequest();
        certReq = hfcaClient.newHFCACertificateRequest();
        AppUser registar = new AppUser();
        registar.setName("admin");
        String s = "-----BEGIN CERTIFICATE-----\n" +
                "MIICczCCAhmgAwIBAgIUQUyqMsKqZuhdo+/pNNTYJO5/C7QwCgYIKoZIzj0EAwIw\n" +
                "eTELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNVBAcTDVNh\n" +
                "biBGcmFuY2lzY28xHDAaBgNVBAoTE0haU1pGLmNpdHlicmFpbi5jb20xHzAdBgNV\n" +
                "BAMTFmNhLkhaU1pGLmNpdHlicmFpbi5jb20wHhcNMTkxMjAzMTMwNzAwWhcNMjAx\n" +
                "MjAyMTMxMjAwWjAtMRwwDQYDVQQLEwZjbGllbnQwCwYDVQQLEwRvcmcxMQ0wCwYD\n" +
                "VQQDEwRteHkxMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAELfGG3iKrSlCQ8up7\n" +
                "D+lKdj0iyHKmUKklFNF08sJWu5Ggw8j1umGBQE/aTOi2gHB6FNdjmgxiRHDdTBkT\n" +
                "9PEqVaOByjCBxzAOBgNVHQ8BAf8EBAMCB4AwDAYDVR0TAQH/BAIwADAdBgNVHQ4E\n" +
                "FgQUpLHQSDSgy0YoH1IVfN0/UgJh+nMwKwYDVR0jBCQwIoAg1vN455hgYfxzQPHZ\n" +
                "R9KjNxDLBRD+/XB7aIemD0h8ItYwWwYIKgMEBQYHCAEET3siYXR0cnMiOnsiaGYu\n" +
                "QWZmaWxpYXRpb24iOiJvcmcxIiwiaGYuRW5yb2xsbWVudElEIjoibXh5MSIsImhm\n" +
                "LlR5cGUiOiJjbGllbnQifX0wCgYIKoZIzj0EAwIDSAAwRQIhAPjczcE9C8vM2WWj\n" +
                "JG5b9VHh1hvWbJQ1wiJWD7SvttXYAiBP1yNeqDqZZH41ToFZHhSm4pnZdeWkVf8c\n" +
                "CbCHIky4SQ==\n" +
                "-----END CERTIFICATE-----";
        X509Certificate cert = getCert(s.getBytes());
        String serial = cert.getSerialNumber().toString(16);
        certReq.setSerial(serial);
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
    private X509Certificate getCert(byte[] certBytes) throws CertificateException {
        BufferedInputStream pem = new BufferedInputStream(new ByteArrayInputStream(certBytes));
        CertificateFactory certFactory = CertificateFactory.getInstance("X509");
        X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(pem);
        return certificate;
    }
}