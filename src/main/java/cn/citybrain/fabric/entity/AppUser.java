package cn.citybrain.fabric.entity;

import lombok.Data;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.io.Serializable;
import java.security.Security;
import java.util.Set;

/**
 * created by yeric on 2019/11/28
 */
@Data
public class AppUser implements User, Serializable {
    private String name;

    private Set<String> roles;

    private String account;

    private String affiliation;

    private Enrollment enrollment;

    private String mspId;

    static{
        Security.addProvider(new BouncyCastleProvider());
    }
}
