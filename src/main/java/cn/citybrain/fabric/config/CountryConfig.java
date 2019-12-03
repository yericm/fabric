package cn.citybrain.fabric.config;

import cn.citybrain.fabric.entity.LanguageConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * created by yeric on 2019/12/3
 */
@Component
@Data
public class CountryConfig {
//    @Value("${country.countryConfig}")
//    private Map<String, Set<LanguageConfig>> countryConfig;
}
