package cn.citybrain.fabric.config;

import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

/**
 * created by yeric on 2019/11/28
 */
@Configuration
@Component
public class BeanConfig {

    @Value("${com.yunyang.caClient.url}")
    private String caClientUrl;

    private HFCAClient hfcaClient;

    @Bean
    public HFCAClient hfCaClient () throws MalformedURLException, IllegalAccessException, InvocationTargetException, InvalidArgumentException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException {
        hfcaClient = HFCAClient.createNewInstance(caClientUrl, null);
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        hfcaClient.setCryptoSuite(cryptoSuite);
        return hfcaClient;
    }
}
