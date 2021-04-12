package com.Secondgood.secondhang.good.service;

import com.Secondgood.secondhang.good.dao.*;
import com.Secondgood.secondhang.good.entity.*;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.inner.InfoContent;
import com.Secondgood.secondhang.good.service.inner.InnerUser;
import com.Secondgood.secondhang.good.util.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class V2UserService {

    @Resource
    private V2UserDao userDao;

    @Resource
    private AdOfUserDao adOfUserDao;

    @Resource
    private AddressDao addressDao;

    @Resource
    private AddressService addressservice;

    @Resource
    private EmailCodeDao emailCodeDao;

    @Resource
    TokenDao tokenDao;

    /**
     * 判断用户是否存在
     * @param userId
     * @return
     */
    public boolean checkUserIdExist(String userId) {
        return userDao.findById(userId).isPresent();
    }

    /**
     * 用户注册
     * @param email
     * @param name
     * @param password
     * @param emailcode
     * @return
     * @throws SecondRuntimeException
     */
    public String register(String email, String name, String password,String emailcode) throws SecondRuntimeException {

        String userId = Util.getUniqueId();

        List<V2User> checkEmail = userDao.findByEmail(email);
        List<V2User> checkName = userDao.findByName(name);
        List<V2User> checkCode = userDao.findByEmailcode(emailcode);
        List<EmailCodeEntity>  checkEmailcode = emailCodeDao.findByCodeAndEmail(emailcode,email);

        if (checkEmail.size() > 0) {
            throw  new SecondRuntimeException("邮箱已被注册");
        }

        if (checkName.size() > 0) {
            throw  new SecondRuntimeException("该名称已存在！");
        }
        if (checkCode.size() > 0  || checkEmailcode.size() == 0) {
            throw  new SecondRuntimeException("验证码错误");
        }

        userDao.save(new V2User(userId, email, name, password,emailcode));

        return userId;
    }

    /**
     * 用户登陆
     * @param email
     * @param password
     * @return
     */
    public TokenEntity login(String email, String password) {
        List<V2User> check = userDao.findByEmailAndPassword(email, password);
        List<V2User> checkEmail = userDao.findByEmail(email);
        if (checkEmail.size() == 0) {
            throw  new SecondRuntimeException(" 请先注册邮箱！");
        }
        if (check.size() == 0) {
            throw  new SecondRuntimeException(" 账号或者密码错误");
        }
        String userid = check.get(0).getId();
        String tokenid = Util.getUniqueId();
        tokenDao.save(new TokenEntity(userid,tokenid));

        return new TokenEntity(userid ,tokenid);
    }

    /**
     * 获取用户个人信息（token）
     * @param tokenid
     * @return
     */

    public List<V2User> userInfo(String tokenid){
        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);
        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String uid = entity.get(0).getUserid();

        List<V2User> user = userDao.findByid(uid);

        return user;
    }


    public List<InnerUser> getUserInfo(List<V2User> list) {
        List<InnerUser> res = new ArrayList<>();
        for (V2User entity : list) {

            String tokenid = null;

            List<TokenEntity> tokenEntity = tokenDao.findByUserid(entity.getId());
            if (tokenEntity.size() == 0) {
                tokenid = null;
            } else {
                 tokenid = tokenEntity.get(0).getTokenid();

                res.add(new InnerUser(entity, tokenid));
            }
        }
        return res;
    }


    /**
     * 修改密码
     * @param tokenid
     * @param email
     * @param code
     * @param password
     * @return
     */
    public String changePassword(String tokenid, String email,String password ,String code){
/*
      覆盖个人信息
       */
        List<V2User> checkEmail = userDao.findByEmail(email);
        List<V2User> checkCode = userDao.findByEmailcode(code);
        List<EmailCodeEntity>  checkEmailcode = emailCodeDao.findByCodeAndEmail(code,email);

        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);

        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();

        if (checkEmail.size()==0) {
            throw  new SecondRuntimeException("该用户不存在，无法修改密码");
        }
        if (checkCode.size() > 0  || checkEmailcode.size() == 0) {
            throw  new SecondRuntimeException("验证码错误");
        }
        V2User userop =userDao.findById(userid).get();
        userop.setPassword(password);
        userDao.save(userop);
        return password;
    }

    /**
     * 忘记密码
     * @param email
     * @param password
     * @param code
     * @return
     */
    public String forgetPassword( String email,String password ,String code){
/*
      覆盖个人信息
       */
        List<V2User> checkEmail = userDao.findByEmail(email);
        List<V2User> checkCode = userDao.findByEmailcode(code);
        List<EmailCodeEntity>  checkEmailcode = emailCodeDao.findByCodeAndEmail(code,email);

        if (checkEmail.size()==0) {
            throw  new SecondRuntimeException("该用户不存在，无法找回密码");
        }
        if (checkCode.size() > 0  || checkEmailcode.size() == 0) {
            throw  new SecondRuntimeException("验证码错误");
        }
        V2User userop =userDao.findByEmail(email).get(0);
        userop.setPassword(password);
        userDao.save(userop);
        return password;
    }


    /**
     * 退出登录
     * @param tokenid
     */
    @Transactional
    public  void  exit(String tokenid){

        tokenDao.deleteByTokenid(tokenid);

    }


}
