# 山东泛生态实时信道数据接入(shandong-hbt-streaming)

## 模块
```
1. 待办消息 needdeal
2. 通知消息 notice
```

## 本地开发测试
 ```
 1、显示工具栏（View-appearance-toolbar）
2、Select Run/Debug configuration
3、编辑配置
4、Add Application
5、Main class固定填写：com.yunli.com.yunli.bigdata.streaming.bootstrap.StreamBootstrap
6、Use classpath of module: 选择3个子工程的任意一个
7、Working directory选择对应的文件夹路径
8、运行或调试即可
```

## 开发上线jar包上传
``` 
mvn clean package -DskipTests
```
