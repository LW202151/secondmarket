package com.Secondgood.secondhang.good.controller;


import com.Secondgood.secondhang.good.dao.OrderDao;
import com.Secondgood.secondhang.good.entity.OrderEntity;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.CarService;
import com.Secondgood.secondhang.good.service.SecondcodeService;
import com.Secondgood.secondhang.good.util.Secondcode;
import com.sun.tools.javac.util.GraphUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(description = "二维码接口")
public class SecondcodeController {
    @Resource
    SecondcodeService secondcodeService;

    @Resource
    OrderDao orderDao;

    @Resource
    CarService carService;

    private static final String QR_CODE_SCAN_URL = "http://192.168.1.129:8000/scan/";




    @ApiOperation(value = "生成二维码")
    @RequestMapping(value = "/createQRcode/{tokenid}/{orderid}", method = RequestMethod.GET)
    @ResponseBody
    public void createQrCode ( @PathVariable(value = "tokenid") String tokenid,
            @PathVariable(value = "orderid") String orderid,
            HttpServletRequest request, HttpServletResponse response){
        StringBuffer url = request.getRequestURL();
        try {
            OutputStream os = response.getOutputStream();
            //从配置文件读取需要生成二维码的连接
     //       String requestUrl = GraphUtils.getProperties("requestUrl");
            //requestUrl:需要生成二维码的连接，logoPath：内嵌图片的路径，os：响应输出流，needCompress:是否压缩内嵌的图片
            List<OrderEntity> check = orderDao.findByOrderid(orderid);
            if(check.size() == 0){

                throw new SecondRuntimeException("订单ID出错");
            }

            Secondcode.encode(QR_CODE_SCAN_URL+tokenid+"/"+orderid, "/Users/ouchinhana/Desktop/个人资料/照片/生活照.jpg", os, true);
          //  secondcodeService.secondcode(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/scan/{tokenid}/{orderid}")
    public void qrScanCount(@PathVariable(value = "tokenid") String tokenid,
                            @PathVariable(value = "orderid") String orderid){

        OrderEntity orderEntity = orderDao.findByOrderid(orderid).get(0);

        orderEntity.setState("支付成功");
        orderDao.save(orderEntity);

        carService.buy(tokenid, orderid);


    }

}
