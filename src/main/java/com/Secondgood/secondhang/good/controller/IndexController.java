//package com.Secondgood.secondhang.good.controller;
//
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.logging.Logger;
//
//
//@Api(description = "test接口")
//@RestController
//public class IndexController {
//
//    @ApiOperation(value = "test post")
//    @RequestMapping(value = "/test", method = RequestMethod.POST)
//    @ResponseBody
//    public String test(@RequestBody String ss) {
//        Logger.getLogger("test").fine(ss);
//        return "haha" + "$" + ss;
//    }
//}
