# springboot-baiduai


打开 http://localhost:8989/swagger-ui.html#/

 1、执行注册用户映射，人脸
 
/baiduai/v1/faceset/face

2、人脸搜索 m-n

/baiduai/v1/face/multi-search

image的值为BASE64编码的字符串，在Base64Util main方法中

或

image的值为url，imageType=URL


------------------------------------------------------
百度API

https://ai.baidu.com/ai-doc/FACE/8k37c1rqz