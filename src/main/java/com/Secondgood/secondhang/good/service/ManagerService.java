package com.Secondgood.secondhang.good.service;


import com.Secondgood.secondhang.good.dao.ManagerDao;
import com.Secondgood.secondhang.good.dao.TokenofManagerDao;
import com.Secondgood.secondhang.good.entity.ManagerEntity;
import com.Secondgood.secondhang.good.entity.TokenOfmanagerEntity;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.util.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ManagerService {

    @Resource
    ManagerDao managerDao;

    @Resource
    TokenofManagerDao tokenofManagerDao;

    /**
     * 管理员登陆
     */

     public String login (String name , String password){

         List<ManagerEntity> check = managerDao.findByName(name);
         List<ManagerEntity> check1 = managerDao.findByPassword(password);

         if(check.size() == 0){

             throw new SecondRuntimeException("非管理员账号");
         }
         if(check1.size() == 0){

             throw new SecondRuntimeException("密码错误");
         }
         int id = check.get(0).getId();
         String token = Util.getUniqueId();
         tokenofManagerDao.save(new TokenOfmanagerEntity(id, token));
         return token;


     }
    /**
     * 退出登录
     */
    @Transactional
    public void exit(String token){

        tokenofManagerDao.deleteByToken(token);
    }


}
