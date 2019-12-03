package cn.citybrain.fabric.config;

import cn.citybrain.fabric.entity.OrgConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author allen
 */
@Component
@ConfigurationProperties(prefix = "com.yunyang")
@Data
public class OrgListConfig {
    private List<OrgConfig> orgs = new ArrayList<>();

    /**
     * 根据组织名称获取组织的配置信息
     * @param name
     * @return
     */
    public OrgConfig findOrgByName(final String name) {
        for (OrgConfig orgConfig : orgs ) {
            if ( orgConfig.getName().equals(name) ) {
                return orgConfig;
            }
        }
        return null;
    }

}
