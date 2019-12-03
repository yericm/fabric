package cn.citybrain.fabric.entity;

import lombok.Data;
import org.apache.catalina.startup.UserConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author allen
 */
@Data
public class OrgConfig {

    private String id;

    private String name;

    private List<UserConfig> users = new ArrayList<>();

    private List<String> channels;
    private CaEntity ca;

/*    *//**
     * 获取admin用户
     * @return
     *//*
    public UserConfig getAdmin() {
        if ( null == users || users.isEmpty() ) {
            return null;
        }
        for (UserConfig userConfig: users) {
            if ( userConfig.getIsAdmin() ) {
                return userConfig;
            }
        }
        return null;
    }*/


}
