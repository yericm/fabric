package cn.citybrain.fabric.config;

import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * https://github.com/yericm/fabric.git
 * 192.168.137.7 peer0.org1.example.com peer1.org1.example.com peer0.org2.example.com peer1.org2.example.com
 * https://blog.csdn.net/zhayujie5200/article/details/80221361
 * created by yeric on 2019/11/28
 */
@Configuration
@Component
public class BeanConfig {

    @Value("${com.yunyang.caClient.url}")
    private String caClientUrl;

    private HFCAClient hfcaClient;
    private HFClient hfClient;

    @Bean
    public HFCAClient hfCaClient () throws IOException, IllegalAccessException, InvocationTargetException, InvalidArgumentException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException {
        Properties testprops = new Properties();

        testprops.setProperty("pemFile", "src/crypto-config/peerOrganizations/HZSZF.citybrain.com/ca/ca.HZSZF.citybrain.com-cert.pem"); // has 1
//        String path = this.getClass().getClassLoader().getResource("crypto-config/peerOrganizations/HZSZF.citybrain.com/ca/ca.HZSZF.citybrain.com-cert.pem").getPath();
//        testprops.setProperty("pemFile", path.substring(1, path.length()));
        testprops.put("allowAllHostNames", "true");
//        testprops.put("pemBytes", Files.readAllBytes(Paths.get("src/crypto-config/peerOrganizations/HZSZF.citybrain.com/ca/ca.HZSZF.citybrain.com-cert.pem")));
//        hfcaClient = HFCAClient.createNewInstance(caClientUrl, testprops);
//        hfcaClient = HFCAClient.createNewInstance("http://192.168.137.7:8054", null);
        hfcaClient = HFCAClient.createNewInstance(caClientUrl, testprops);
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        hfcaClient.setCryptoSuite(cryptoSuite);
        return hfcaClient;
    }
    @Bean
    public HFClient hfClient () throws IllegalAccessException, InvocationTargetException, InvalidArgumentException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException {
        hfClient = HFClient.createNewInstance();
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        hfClient.setCryptoSuite(cryptoSuite);
//        hfClient.setUserContext(userContext);
        return hfClient;
    }
}
