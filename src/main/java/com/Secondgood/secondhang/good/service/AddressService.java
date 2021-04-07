package com.Secondgood.secondhang.good.service;


import com.Secondgood.secondhang.good.dao.*;
import com.Secondgood.secondhang.good.entity.AdOfUserEntity;
import com.Secondgood.secondhang.good.entity.AddressEntity;
import com.Secondgood.secondhang.good.entity.TokenEntity;
import com.Secondgood.secondhang.good.entity.V2User;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.inner.AddressContent;
import com.Secondgood.secondhang.good.util.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Resource
    private AddressDao addressDao;

    @Resource
    private V2UserDao userDao;

    @Resource
    private AdOfUserDao adOfUserDao;

    @Resource
    private V2UserService v2UserService;

    @Resource
    TokenDao tokenDao;

    /**
     * 添加地址
     * @param tokenid
     * @param name
     * @param phone
     * @param province
     * @param city
     * @param detail
     * @return
     * @throws SecondRuntimeException
     */
    public String creatAddress(String tokenid , String name ,String phone , String province, String city, String detail) throws SecondRuntimeException{

       // List<V2User> check = userDao.findById(userid);
        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);
        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();


        if (!v2UserService.checkUserIdExist(userid)) {
            throw new SecondRuntimeException("用户不存在");
        }
        String addressid = Util.getUniqueId();
        addressDao.save(new AddressEntity(addressid, userid,name,phone,province,city,detail));
        adOfUserDao.save(new AdOfUserEntity(Util.getUniqueId(),userid,addressid));
        return addressid;
    }

    /**
     * 删除地址
     * @param tokenid
     * @param addressid
     */
    @Transactional
    public void deleteAddress(String tokenid , String addressid){

        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);
        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();

     List<AdOfUserEntity> check = adOfUserDao.findByUseridAndAddressid(userid, addressid);
     if(check.size() == 0){

         throw new SecondRuntimeException("地址与用户不匹配，没有权限修改");

     }
     addressDao.deleteById(addressid);
     adOfUserDao.deleteByAddressid(addressid);


    }

    /**
     * 查看地址
     * @param tokenid
     * @return
     */
    public List<AddressEntity> lookAddress(String tokenid){

        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);
        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();

        if (!v2UserService.checkUserIdExist(userid)) {
            throw new SecondRuntimeException("用户不存在");
        }
        List<AddressEntity> Entity = addressDao.findByUserid(userid);

        return Entity;

    }

    /**
     * 查看具体地址
     * @param tokenid
     * @param addressid
     * @return
     */
    public List<AddressEntity> look(String tokenid , String addressid){

        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);
        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();

        if (!v2UserService.checkUserIdExist(userid)) {
            throw new SecondRuntimeException("用户不存在");
        }
        List<AddressEntity> Entity = addressDao.findByUseridAndId(userid,addressid);

        return Entity;

    }

}
