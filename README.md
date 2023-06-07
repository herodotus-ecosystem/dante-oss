<h1 align="center">Dante OSS</h1>
<h2 align="center">丰富 · 全面 | 简单 · 便捷</h2>
<p align="center">让 Minio 的集成和使用更简单</p>

---

<p align="center">
    <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://shields.io/badge/Spring%20Boot-3.1.0-blue.svg?logo=spring" alt="Spring Boot 3.1.0"></a>
    <a href="#" target="_blank"><img src="https://shields.io/badge/Version-0.2.0-red.svg?logo=spring" alt="Version 0.2.0"></a>
    <a href="https://bell-sw.com/pages/downloads/#downloads" target="_blank"><img src="https://img.shields.io/badge/JDK-17%2B-green.svg?logo=openjdk" alt="Java 17"></a>
    <a href="./LICENSE"><img src="https://shields.io/badge/License-Apache--2.0-blue.svg?logo=apache" alt="License Apache 2.0"></a>
    <a href="https://www.herodotus.cn"><img src="https://visitor-badge.laobi.icu/badge?page_id=dante-cloud&title=Total%20Visits" alt="Total Visits"></a>
    <a href="https://blog.csdn.net/Pointer_v" target="_blank"><img src="https://shields.io/badge/Author-%E7%A0%81%E5%8C%A0%E5%90%9B-orange" alt="码匠君"></a>
    <a href="https://gitee.com/dromara/dante-cloud"><img src="https://img.shields.io/github/stars/herodotus-cloud/dante-oss?style=flat&logo=github" alt="Github star"></a>
    <a href="https://gitee.com/dromara/dante-cloud"><img src="https://img.shields.io/github/forks/herodotus-cloud/dante-oss?style=flat&logo=github" alt="Github fork"></a>
    <a href="https://gitee.com/herodotus/dante-oss"><img src="https://gitee.com/herodotus/dante-oss/badge/star.svg?theme=dark" alt="Gitee star"></a>
    <a href="https://gitee.com/herodotus/dante-oss"><img src="https://gitee.com/herodotus/dante-oss/badge/fork.svg?theme=dark" alt="Gitee fork"></a>
</p>

<p align="center">
    <a href="https://gitee.com/dromara/dante-cloud">示例微服务应用</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/herodotus/dante-cloud-athena">示例单体应用</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/herodotus/dante-cloud-ui">示例前端应用</a>
</p>

<h1 align="center"> 如果您觉得有帮助，请点右上角 "Star" 支持一下，谢谢！</h1>

## 项目简介

Dante OSS 是一款 Minio Java SDK 扩展增强工具包。通过对原有 Minio Java SDK 的深度封装，简化 Minio API 使用复杂度，提升 Minio 使用的便捷性，降低 Minio 应用开发门槛。

## 项目背景

MinIO 是一款高性能、分布式的对象存储系统。Minio这款开源的分布式对象存储服务在国外已经相当受欢迎，并且国内也有多中小型互联网公司使用它来作为对象存储服务。虽然 Minio 相关的资料和示例在网络上已经非常丰富，但是为什么还要推出 Dante OSS 这样的项目？

- 原因一：使用不方便
  - 初次接触 Minio，特别是想要通过 Java 集成 Minio 开发对象存储应用是，还是需要投入一定的时间和精力去了解其原理阅读相关文档。
  - Minio SDK 中的函数方法，涉及的参数较多，抽象层度比较高，每次使用都需要反复查阅源代码才能摸清具体使用方式。
  - 想要与已有的应用进行整合，多少都要投入时间精力，进行一定程度的封装和改造。
  - 网上相关资料多，要么比较零散不成体系，要么比较单一仅针对常规上传下载应用，Minio 自身很多特性都不涉及。
- 原因二：与 Dante 绑定过深
  - 现有代码原来作为 Dante Cloud 基础组件进行使用，必须依赖于 Dante 环境。
  - 是独立性较高的内容，但是却融入在一个庞大的架构环境中，不利于理解和单独使用

因此，单独将 Minio 相关代码，从 Dante Cloud 中剥离，成为一个独立的项目产品。一方面提升 Dante 项目和 Minio 应用各自应用的独立性，减少互相干扰; 另一方面，在 Minio Java SDK 的基础之上，只做扩展不做改变。同时融合大文件分片上传、秒传、端点续传等常规解决方案，形成开箱即用的、可以快速与应用项目集成的 Spring Boot 组件。


## 工程结构

```
dante-oss
├── oss-bom -- 工程Maven顶级依赖，统一控制版本和依赖
├── oss-minio -- Minio 模块
├    ├── minio-core -- Minio 通用代码包
├    ├── minio-sdk-api -- Minio 基础API封装模块
├    ├── minio-sdk-logic -- 基于Minio扩展应用模块
├    ├── minio-sdk-rest -- 基于Minio扩展的REST接口模块
└──  └── minio-spring-boot-starter -- Minio Starter
```

## 主要功能

| 功能                     | 说明                                                        |   进展   |
|------------------------|-----------------------------------------------------------|:------:|
| Minio 基础 API 封装        | 将Minio基础API封装成便于使用的 Service                               |  已完成   |
| 线程池支持                  | 提供 Minio Client 对象池支持，提升访问 Minio Server性能                 |  已完成   |
| REST 参数封装              | Minio API **Args 参数封装为支持 Validation 的请求参数对象               |  已完成   |
| REST 接口开发              | Bucket、Object 增、删、改、显示REST接口开发                            |  已完成   |
| OpenAPI 支持             | 在支持 Open API 环境下，显示 Swagger 文档，建议使用 Springdoc             |  已完成   |
| 大文件分片上传                | 采用 PresignedObjectUrl 方案的大文件分片上传                          |  已完成   |
| 大文件分片上传                | 采用 PresignedObjectUrl 方案的大文件分片上传                          |  已完成   |
| 超轻量级反向代理               | 实现轻量级反向代理解决 PresignedObjectUrl 方式直接向前端暴露 Minio Server地址问题 |  已完成   |
| 与 SAS 鉴权体系整合           | PresignedObjectUrl 方式融入鉴权体系                               |  已完成   |
| vue-simple-uploader 集成 | 使用vue-simple-uploader集成大文件分片上传                            |  已完成   |
| 其它功能                   |                                                           | 开发中... |



## Spring Boot 环境集成

1. maven 中引入

```xml
<dependency>
    <groupId>cn.herodotus.oss</groupId>
    <artifactId>minio-spring-boot-starter</artifactId>
    <version>最新版本</version>
</dependency>
```

2. 配置 yml 参数

```yaml
herodotus:
  oss:
    minio:
      endpoint: http://127.0.0.1:9000
      access-key: xxxxxx
      secret-key: xxxxxx
```

3. 统一错误处理

```java
// 参考代码
public static Result<String> resolveException(Exception ex, String path) {
    return GlobalExceptionHandler.resolveException(ex, path);
}

// 或者

@ExceptionHandler({HerodotusException.class, PlatformException.class})
public static Result<String> exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
    ······
}
```

## 参与贡献

1. 在Gitee fork项目到自己的repo 
2. 把fork过去的项目也就是你的项目clone到你的本地
3. 修改代码（记得一定要修改 develop 分支） 
4. commit 代码，push 到自己的库（develop分支） 
5. 登录Gitee 在你首页可以看到一个 pull request 按钮，点击它，填写一些说明信息，然后提交即可。 
6. 等待维护者合并

## 交流反馈

- 欢迎提交[ISSUS](https://gitee.com/herodotus/dante-oss/issues) ，请写清楚问题的具体原因，重现步骤和环境

## 演示工程

Dante OSS 作为 Dante Cloud 生态产品，不在单独提供演示环境和示例，请直接使用 Dante 环境查看效果和了解使用，这样也更贴近实际。

- 微服务演示环境：[https://gitee.com/dromara/dante-cloud](https://gitee.com/dromara/dante-cloud)
- 单体架构演示环境：[https://gitee.com/herodotus/dante-cloud-athena](https://gitee.com/herodotus/dante-cloud-athena)
- 前端工程演示：[https://gitee.com/herodotus/dante-cloud-ui](https://gitee.com/herodotus/dante-cloud-ui)

> 注意：如果您仅是想了解基本的使用方式和使用效果，建议使用单体架构演示环境。具体搭建方式参见[【在线文档】](http://www.herodotus.cn)中，单体版章节。

