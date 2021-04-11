package com.Secondgood.secondhang.good.service;

import com.Secondgood.secondhang.good.dao.PicGoodDao;
import com.Secondgood.secondhang.good.dao.PicuserDao;
import com.Secondgood.secondhang.good.dao.TokenDao;
import com.Secondgood.secondhang.good.entity.PicGoodEntity;
import com.Secondgood.secondhang.good.entity.PicuserEntity;
import com.Secondgood.secondhang.good.entity.TokenEntity;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.util.COSUtil;
import com.Secondgood.secondhang.good.util.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class PicService {

    @Resource
    private PicGoodDao dao;


    @Resource
    private SeegoodsService seegoodsService;

    @Resource
    PicuserDao picuserDao;

    @Resource
    TokenDao tokenDao;


    /**
     * 上传商品图片
     * @param goodsid goodsid
     * @param fileInputStream 文件输入流
     * @param fileName 文件名
     */
    public String upload(String goodsid, InputStream fileInputStream, String fileName) throws SecondRuntimeException {


        if (!seegoodsService.checkGoodExist(goodsid)) {
            throw new SecondRuntimeException("物品id不存在，无法发布图片，请发布此物品");
        }

        String fileType = Util.fromNameGetType(fileName);
        String key = Util.getUniqueId().substring(16) + "-" + Util.getNowTime() + "." + fileType;


        // upload
        try {
            COSUtil.uploadWithInputStream(fileInputStream, key);
        } catch (IOException e) {
            throw new RuntimeException("COS exception:" + e.getMessage());
        }

        dao.save(new PicGoodEntity(Util.getUniqueId(), goodsid, key));

        List<PicGoodEntity> temp =dao.findByGoodsid(goodsid);
        String url = COSUtil.getUrlFromObjectKey(temp.get(0).getPickey());

        return url;
    }


    /**
     * 上传头像
     * @param tokenid
     * @param fileInputStream
     * @param fileName
     * @throws SecondRuntimeException
     */
    public String uploaduser(String tokenid, InputStream fileInputStream, String fileName) throws SecondRuntimeException {

        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = Entity.get(0).getUserid();

        String fileType = Util.fromNameGetType(fileName);
        String key = Util.getUniqueId().substring(16) + "-" + Util.getNowTime() + "." + fileType;


        // upload
        try {
            COSUtil.uploadWithInputStream(fileInputStream, key);
        } catch (IOException e) {
            throw new RuntimeException("COS exception:" + e.getMessage());
        }

        picuserDao.save(new PicuserEntity(Util.getUniqueId(), userid, key));
        List<PicuserEntity> temp = picuserDao.findByUserid(userid);
       String url = COSUtil.getUrlFromObjectKey(temp.get(0).getPickey());

       return url;

    }


    /**
     * 根据goodid得到图片真实地址
     * @param goodId goodId
     * @return String
     */
    public String fromGoodIdGetUrl(String goodId) {

        List<PicGoodEntity> temp = dao.findByGoodsid(goodId);

        if (temp.size() == 0) {
            return null;
        }

        return COSUtil.getUrlFromObjectKey(temp.get(0).getPickey());
    }

}
