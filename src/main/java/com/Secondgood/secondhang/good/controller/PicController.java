package com.Secondgood.secondhang.good.controller;


import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.PicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Api(description = "图片接口")
@RestController
public class PicController {


    @Resource
    private PicService service;

    @ApiOperation(value = "上传图片")
    @RequestMapping(value = "/upload/{goodsid}", method = RequestMethod.POST)
    public Map<String, Object> upload(@PathVariable(value = "goodsid") String goodsid,
                                      @RequestParam("file") MultipartFile file){
        Map<String, Object> map = new HashMap<>();

        try {
            service.upload(goodsid, file.getInputStream(), file.getOriginalFilename());
            map.put("code", 0);
            map.put("msg", "success");

        } catch (IOException e) {
            map.put("code", 2);
            map.put("msg", e.getMessage());
        } catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }

        return map;
    }


}
