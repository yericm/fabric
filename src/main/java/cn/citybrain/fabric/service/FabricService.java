package cn.citybrain.fabric.service;

import cn.citybrain.fabric.entity.AppUser;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;

/**
 * created by yeric on 2019/11/28
 */
public interface FabricService {
    String register(AppUser registar, AppUser register) throws Exception;
    Enrollment enroll(String username, String password) throws EnrollmentException, InvalidArgumentException;
}
