package com.Secondgood.secondhang.good.service;

import com.Secondgood.secondhang.good.dao.PicGoodDao;
import com.Secondgood.secondhang.good.entity.PicGoodEntity;
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


    /**
     * 上传作文图片
     * @param goodsid goodsid
     * @param fileInputStream 文件输入流
     * @param fileName 文件名
     */
    public void upload(String goodsid, InputStream fileInputStream, String fileName) throws SecondRuntimeException {


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
