# wejuai-weixin-server
微信支持服

### 结构
- core：gateway和callback共同部分
- gateway：请求微信服务
- callback：接收微信公众号通知后反应处理(后来转入小程序以后年久失修，可能已经无法使用，而且没有使用config-server)
  - handler：根据定制事件来反应处理

### 配置项
- gateway：`bootstrap.yml`引用的地址以及配置服务中的配置
- callback：resources中的配置文件
- 如果使用callback也需要在core中的`WxConstant.java`配置原始id和appId
- `build.gradle`中的github或者其他获得dto和entity以及工具包的仓库

### 本地启动
1. gradle构建成功
2. 配置项配置完成
3. 分别运行`xxApplication.java`的`main()`方法

### docker build以及运行
- 运行gradle中的docker build task
- 如果配置了其中的第三方仓库可以运行docker push，会先build再push
- 运行方式 docker run {image name:tag}，默认是运行的profile为dev，可以通过环境变量的方式修改，默认启动配置参数在Dockerfile中

### note
callback和gateway都会运行刷新微信Token的定时任务，但是是基于数据库中存储的过期时间，所以同时运行也不会有问题