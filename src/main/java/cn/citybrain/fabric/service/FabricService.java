package cn.citybrain.fabric.service;

import cn.citybrain.fabric.entity.AppUser;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;

import java.io.IOException;

/**
 * created by yeric on 2019/11/28
 */
public interface FabricService {
    /**
     * 创建channel
     *
     * @param channelName channel的名字
     * @param order       order的信息
     * @param txPath      创建channel所需的tx文件
     * @return
     */
    Channel createChannel(String channelName, Orderer order, String txPath, AppUser userInfo) throws IOException, org.hyperledger.fabric.sdk.exception.InvalidArgumentException, TransactionException;

    /**
     * 获取orderer节点
     * @param name
     * @param grpcUrl
     * @param tlsFilePath
     * @return
     */
    Orderer getOrderer(String name,String grpcUrl,String tlsFilePath,AppUser userInfo) throws org.hyperledger.fabric.sdk.exception.InvalidArgumentException;

    /**
     * 获取peer节点
     * @param name
     * @param grpcUrl
     * @param tlsFilePath
     * @return
     */
    Peer getPeer(String name, String grpcUrl, String tlsFilePath,AppUser userInfo) throws org.hyperledger.fabric.sdk.exception.InvalidArgumentException;

    String register(AppUser registar, AppUser register) throws Exception;
    Enrollment enroll(String username, String password) throws EnrollmentException, InvalidArgumentException;
}
