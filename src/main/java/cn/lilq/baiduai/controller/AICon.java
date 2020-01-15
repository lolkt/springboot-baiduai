package cn.lilq.baiduai.controller;

import cn.lilq.baiduai.api.Client;
import cn.lilq.baiduai.dao.AIDAO;
import cn.lilq.baiduai.pojo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AICon {

    @Autowired
    private Client client;

    public AICon() {
    }

    @ApiOperation(value = "注册用户映射，人脸", notes = "{\n" +
            "\"image\": \"BASE64编码的字符串\",\n" +
            "\"imageType\": \"BASE64\"\n" +
            "}\n或\n{\n" +
            "  \"image\": \"http://inews.gtimg.com/newsapp_bt/0/10954592796/1000.jpg\",\n" +
            "  \"imageType\": \"URL\"\n" +
            "}")
    @RequestMapping(value = "/baiduai/v1/faceset/face", method = RequestMethod.POST)
    public Response faceAdd(@RequestBody PostMess postMess) {
        User user = new User("test",  postMess.getName());
        FaceAddAPI faceAddAPI = client.add(postMess.getImage(), postMess.getImageType(), user.getGroupId(), user.getId());
        if (!"SUCCESS".equals(faceAddAPI.getErrorMsg())) {
            return new Response(faceAddAPI.getErrorMsg(), null);
        }
        System.out.println("添加成功" + faceAddAPI.toString());

        return new Response(faceAddAPI.getErrorMsg(), null);
    }

    //获得全部用户映射
    @RequestMapping(value = "/baiduai/v1/faceset/users", method = RequestMethod.GET)
    public Response users() {
        return new Response("SUCCESS", new Response.Result(null, null, null));
    }

    //增加用户映射
    @RequestMapping(value = "/baiduai/v1/faceset/users", method = RequestMethod.POST)
    public Response users(@RequestBody List<User> users) {
//        if (aidao.addUser(users)) {
//            return new Response("SUCCESS", null);
//        }
        return new Response("add user mapping error", null);
    }


    @ApiOperation(value = "人脸搜索 m-n", notes = "{\n" +
            "  \"image\": \"BASE64编码的字符串\",\n" +
            "  \"imageType\": \"BASE64\",\n" +
            "  \"name\": \"sunss\"\n" +
            "}\n或\n{\n" +
            "  \"image\": \"http://n.sinaimg.cn/sinacn10/719/w960h559/20180716/475a-hfkffak3792414.jpg\",\n" +
            "  \"imageType\": \"URL\",\n" +
            "  \"name\": \"naza2\"\n" +
            "}")
    @RequestMapping(value = "/baiduai/v1/face/multi-search", method = RequestMethod.POST)
    public Response faceSearch(@RequestBody PostMess postMess) {
//        try {
//            imgParam = URLEncoder.encode(base64, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        FaceSearchAPI faceSearchAPI = client.search(postMess.getImage(), postMess.getImageType(), "test");
        Response response = new Response();
        Response.Result resultNew = new Response.Result();
        if (faceSearchAPI.getResult() != null) {
            FaceSearchAPI.Result result = faceSearchAPI.getResult();
            //获取并设置人脸数量
            resultNew.setNum(result.getNum());
            //响应人脸列表
            List<Response.Face> facesNew = new ArrayList<>();
            //获取人脸列表
            List<FaceSearchAPI.Face> faces = result.getFaceList();
            faces.forEach(face -> {
                //响应报文对象
                Response.Face faceNew = new Response.Face();
                //获取人脸位置
                FaceSearchAPI.Location location = face.getLocation();
                //添加人脸位置
                faceNew.setLocation(new Response.Location(location.getLeft(), location.getTop(), location.getWidth(), location.getHeight(), location.getRotation()));
                //获取概率最大用户
                FaceSearchAPI.User user = face.getUserList().stream().max((o1, o2) -> o1.getScore() >= o2.getScore() ? 1 : -1).get();
                System.out.println("用户概率最大" + user);
                //人脸对应用户
                faceNew.setUser(new Response.User(user.getId(), user.getScore()));
                //添加用户
                facesNew.add(faceNew);
            });
            resultNew.setFaceList(facesNew);
            response.setResult(resultNew);
        }
        response.setErrorMsg(faceSearchAPI.getErrorMsg());
        return response;
    }
}
