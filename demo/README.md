# open-datashare-demo（java）

> 本目录为[java版open-datashare-sdk](https://)对接示例代码目录：

## 一、目录描述：

- open-datashare-demo-src-x.x.x为示例代码目录，**x.x.x**为版本号；

## 二、代码描述：

- 资源文件：
    imi/imi-config.properties为SDK参数配置文件；
    imi/imi-ks为数字身份密钥存储文件；
    demo代码中的以上两个配置文件为测试环境配置，生产环境配置由我司分配。

- 代码：
    “lock/*”包下为zookeeper分布式锁实现代码（🈶️客户自行实现）
    “TestHander.java” 为测试数据类；
    “QueryTester.java” 为数据查询与验证测试类；
    “ConcurrencyStoreTester.java” 为模拟并发数据上链存储测试类；

## 三、版本日志

  | 版本号   | 版本描述                                     |
  | ----- | ---------------------------------------- |
  | 1.0.0 | 基于open-datashare-sdk-1.0.0基础版本发布 |
