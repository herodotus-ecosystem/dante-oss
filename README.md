<h1 align="center">Dante OSS</h1>
<h2 align="center">丰富 · 全面 | 简单 · 便捷</h2>
<p align="center">让 Minio 的集成和使用更简单</p>

---

<p align="center">
    <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://shields.io/badge/Spring%20Boot-3.1.0-blue.svg?logo=spring" alt="Spring Boot 3.1.0"></a>
    <a href="#" target="_blank"><img src="https://shields.io/badge/Version-0.4.0-red.svg?logo=spring" alt="Version 0.4.0"></a>
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

## 简介 | Intro

Dante OSS 是一款简化Minio操作的开源框架。通过对原有 Minio Java SDK 的深度封装，简化 Minio API 使用复杂度，提升 Minio 使用的便捷性，降低 Minio 应用开发门槛。

MinIO 是一款高性能、分布式的对象存储系统。Minio这款开源的分布式对象存储服务在国外已经相当受欢迎，并且国内也有多中小型互联网公司使用它来作为对象存储服务。虽然 Minio 相关的资料和示例在网络上已经非常丰富，但是为什么还要推出 Dante OSS 这样的项目？

- 初次接触 Minio，特别是想要通过 Java 集成 Minio 开发对象存储应用是，还是需要投入一定的时间和精力去了解其原理阅读相关文档。
- Minio SDK 中的函数方法，涉及的参数较多，抽象层度比较高，每次使用都需要反复查阅源代码才能摸清具体使用方式。
- 想要与已有的应用进行整合，多少都要投入时间精力，进行一定程度的封装和改造。
- 网上相关资料多，要么比较零散不成体系，要么比较单一仅针对常规上传下载应用，Minio 自身很多特性都不涉及。

## 优点 | Advantages

- **零额外学习成本**: 开发者只要会 Spring 和 REST 基本开发，即可无缝集成和使用 Dante OSS 
- **降低开发者门槛**: 屏蔽 Minio 标准 Java SDK 使用复杂度，使用 Spring 环境标准方式对原有 API 进行简化封装。Service API 和 REST API 开箱即用
- **包含的功能丰富**: 改造了 Minio Java SDK 的几乎全部功能，且对大文件分片上传、秒传、直传、断点续传等功能，均采用业内最优解决方案进行实现和融合
- **规范优雅的代码**: 所有函数参数，并未破坏原有 Minio 代码构造器结构，而是在原有方式的基础上抽象简化，编程体验和代码可读性大幅提升
- **完善的注释文档**: 对请求参数、方法、REST API、Validation 提供详实的注释、说明和 OpenAPI 标注，用途用法一目了然，无需再翻阅 Minio 文档和源代码，帮助您节省更多时间
- **完整的前端示例**：前端采用一个完整的项目而非Demo的形式，全面的展示了前后端交互涉及、接口调用、参数使用、TS 类型定义等各方面内容，可直接用于实际项目或简单改造后构建自己的产品

## 对比 | Compare

### 1. 不只是简单的 Spring Boot Starter 构建

1. 构建统一的错误，可以返回更人性化、更易理解的错误信息，同时兼顾更详细错误信息的返回，方便开发人员理解和定位问题。
2. 采用更易理解和使用的格式对 Minio Java SDK 参数进行重新定义。规避 Minio 默认 XML 方式参数多、不易理解使用、与前端交互不方便等问题。
3. 隐藏 Minio Java SDK 不易理解和使用的细节，提供详实的注释说明，开发人员在使用时无需再通过翻阅 Minio 在线文档和源代码来了解各个 API 使用细节。
4. 提供统一标准的 REST API，以及 OpenAPI Swagger3 文档描述和准确的 Spring Validation 校验，可直接集成至系统中使用。
5. Minio Client 对象池、自定义极简 Minio Server 访问反向代理，提升

### 2. 标准化业务逻辑和解决方案集合

1. 不只是上传、下载等常用方法的封装，涵盖 Minio Java SDK 支持的所有方法和操作。
2. 选择业内最优的解决方案，实现和集成大文件分片上传、秒传、直传、断点续传等主要业务需求功能。
3. 结合自身应用经验和需求，将 Minio API 进一步组合成符合实际应用的业务逻辑和功能处理。
4. 采用一个基于 Vue3、Typescript5、Vite4、Pinia 2 的完整的前端项目作为集成示例，包括详细的 Typescript 类型定义以及 vue-simple-uploader 等主流组件集成和使用方法。
5. 提供基于 Spring Authorization Server 的单体版、微服务版案例，从 SDK、Spring Boot Starter 到完整项目任你选择。

## 结构 | Structure

```
dante-oss
├── oss-bom -- 工程Maven顶级依赖，统一控制版本和依赖
├── oss-minio -- Minio 模块
├    ├── minio-core -- Minio 通用代码包
├    ├── minio-sdk-logic -- Minio 基础 API 模块
├    ├── minio-sdk-rest -- Minio 基础 REST API模块
├    ├── minio-sdk-scenario -- Minio 扩展及应用方案整合模块
├    └── minio-spring-boot-starter -- 仅包含 Minio 基础 API 和 REST API 的 Starter
└── oss-spring-boot-starter -- 完整的、包含所有内容的 Starter
```

## 功能 | function

- [1] 基础功能

| 功能              | 说明                                                       |
|-----------------|----------------------------------------------------------|
| Bucket 列表       | Bucket 列表查询，包括 Service、REST API 和前端展示                    |
| Bucket 名称是否存在   | Bucket 名是否存在，包括 Service、REST API 和前端异步校验处理               |
| Bucket 创建       | 创建 Bucket，包括 Service、REST API 和前端Validation校验处理          |
| Bucket 删除       | 删除 Bucket，包括 Service、REST API 和前端展示处理                    |
| Bucket 加密设置获取   | 获取 Bucket Encryption 设置，包括 Service、REST API              |
| Bucket 修改加密设置   | 修改 Bucket Encryption 设置，包括 Service、REST API              |
| Bucket 删除加密设置   | 删除 Bucket Encryption 设置，包括 Service、REST API              |
| Bucket 访问策略设置获取 | 获取 Bucket Policy 设置，包括 Service、REST API                  |
| Bucket 修改访问策略设置 | 修改 Bucket Policy 设置，包括 Service、REST API                  |
| Bucket 删除访问策略设置 | 删除 Bucket Policy 设置，包括 Service、REST API                  |
| Bucket 标签获取     | 获取 Bucket Tags，包括 Service、REST API                       |
| Bucket 修改标签     | 修改 Bucket Tags，包括 Service、REST API                       |
| Bucket 删除标签     | 删除 Bucket Tags，包括 Service、REST API                       |
| Bucket 对象锁定设置获取 | 获取 Bucket ObjectLockConfiguration 设置，包括 Service、REST API |
| Bucket 修改对象锁定设置 | 修改 Bucket ObjectLockConfiguration 设置，包括 Service、REST API |
| Bucket 删除对象锁定设置 | 删除 Bucket ObjectLockConfiguration 设置，包括 Service、REST API |
| Object 列表       | Object 列表查询，包括 Service、REST API 和前端展示                    |
| Object 删除       | 删除 Object，包括 Service、REST API 和前端展示处理                    |
| Object 批量删除     | 批量删除 Object，包括 Service、REST API 和前端展示处理                  |
| Object 元信息获取    | 获取 Object Stat，包括 Service、                               |
| Object 下载(服务端)  | Object 下载(服务端下载，非流模式)，包括 Service、                        |
| Object 标签获取     | 获取 Object Tags，包括 Service、REST API                       |
| Object 修改标签     | 修改 Object Tags，包括 Service、REST API                       |
| Object 删除标签     | 删除 Object Tags，包括 Service、REST API                       |
| Object 获取保留设置   | 获取 Object Retention，包括 Service、REST API                  |
| Object 修改保留设置   | 修改 Object Retention，包括 Service、REST API                  |
| Object 开启持有设置   | 获取 Object LegalHold，包括 Service、REST API                  |
| Object 关闭持有设置   | 修改 Object LegalHold，包括 Service、REST API                  |
| 其它功能            | 正逐步完善，主要涉及前后端交互、以及可用性验证和前端相关功能的开发，敬请期待，欢迎 PR             |


- [2] 扩展功能

| 功能               | 说明                                                                   |
|------------------|----------------------------------------------------------------------|
| 创建分片上传请求         | 创建分片上传请求，返回 Minio UploadId                                           |
| 创建文件预上传地址        | 根据 UploadId 和 指定的分片数量，返回数量像匹配的 Minio 与上传地址                           |
| 获取所有分片文件         | 获取指定 uploadId 下所有的分片文件                                               |
| 创建大文件分片上传        | 统一的创建大文件分片上传业务逻辑封装，减少前后端反复交互， 包括 Service、REST API                    |
| 合并已经上传完成的分片      | 根据 UploadId 合并已经上传完成的分片，完成大文件分片上传 包括 Service、REST API                |
| 统一常量接口           | 将涉及的 Enums、常量以统一接口的方式返回给前端，方便展示使用， 包括 Service、REST API 和前端展示         |
| Minio Client 对象池 | 实现 Minio Client 对象池，减少 Minio Client 的反复创建和销毁，提升访问 Minio Server性能     |
| Bucket 设置        | 统一 Bucket 设置： Bucket 标签设置、访问策略、加密方式、对象锁定等， 包括 Service、REST API 和前端展示 |
| Object 设置        | 统一 Object 设置： Bucket 标签设置， 包括 Service、REST API 和前端展示                 |
| Object 下载(流模式)   | Minio 对象下载，采用流模式支持vue前端post方式下载， 包括 Service、REST API 和前端展示           |
| 超轻量级反向代理         | 实现轻量级反向代理解决 PresignedObjectUrl 方式直接向前端暴露 Minio Server地址问题            |


- [3] 主流方案

| 功能                                 | 说明                                                                      |
|------------------------------------|-------------------------------------------------------------------------|
| OpenAPI 支持                         | 在支持 Open API 环境下，显示 Swagger 文档，建议使用 Springdoc                           |
| 与 Spring Authorization Server 体系集成 | 提供完整的与 Spring Authorization Server 集成，实现认证、授权、鉴权、动态权限等完整案例，包括单体版和微服务版   |
| 大文件分片上传                            | 采用 PresignedObjectUrl 方案的大文件分片上传。扩展 Minio Client，封装相应的 Service、REST API |
| vue-simple-uploader                | 前端基于 vue-simple-uploader 组件，配合自定义 REST API 实现大文件分片上传                    |

## 使用 | How to use

### 一、基本使用

1. maven 中引入

```xml
<dependency>
    <groupId>cn.herodotus.oss</groupId>
    <artifactId>oss-spring-boot-starter</artifactId>
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
> 结合实际需求配置数据源

3. 统一错误处理

```java
// 参考代码
public static Result<String> resolveException(Exception ex， String path) {
    return GlobalExceptionHandler.resolveException(ex， path);
}

// 或者

@ExceptionHandler({HerodotusException.class， PlatformException.class})
public static Result<String> exception(Exception ex， HttpServletRequest request， HttpServletResponse response) {
    ······
}
```

4. 交互性错误信息反馈

```java
// 在系统统一错误处，调用以下代码即可返回包含自定义错误码的、更具交互性错误信息。
if (ex instanceof HerodotusException exception) {
    Result<String> result = exception.getResult();
    result.path(path);
    log.error("[Herodotus] |- Global Exception Handler, Error is : {}", result);
    return result;
}
```

### 二、选择使用

除了 `minio-core` 模块以外，其它所有模块均可以单独使用。可以根据自身需要，仅选择某个模块进行使用。

- **minio-sdk-logic**: 仅包含对 Minio 基础 API 封装的 Service 代码。使用注解 `@EnableHerodotusMinioLogic` 可开启相关内容。
- **minio-sdk-rest**: 包含对 Minio 基于 API 封装的 Service 以及 REST 代码。使用注解 `@EnableHerodotusMinioRest` 可开启相关内容。
- **minio-sdk-scenario**: 包含扩展应用以及各OSS常规场景应用， 注意：不包含 `minio-sdk-rest` 内容。使用注解 `@EnableHerodotusMinioScenario` 可开启相关内容。
- **minio-spring-boot-starter**: 包含 `minio-sdk-logic` 和 `minio-sdk-rest` 两部分内容，可直接引入使用。
- **oss-spring-boot-starter**: 包含所有内容，注意：需要依赖数据库等相关内容。

## 贡献 | Committer

1. 在Gitee fork项目到自己的repo 
2. 把fork过去的项目也就是你的项目clone到你的本地
3. 修改代码（记得一定要修改 develop 分支） 
4. commit 代码，push 到自己的库（develop分支） 
5. 登录Gitee 在你首页可以看到一个 pull request 按钮，点击它，填写一些说明信息，然后提交即可。 
6. 等待维护者合并

## 反馈 | Feedback

- 欢迎提交[ISSUS](https://gitee.com/herodotus/dante-oss/issues) ，请写清楚问题的具体原因，重现步骤和环境

## 演示 | Example

Dante OSS 作为 Dante Cloud 生态产品，不在单独提供演示环境和示例，请直接使用 Dante 环境查看效果和了解使用，这样也更贴近实际。

- 微服务演示环境：[https://gitee.com/dromara/dante-cloud](https://gitee.com/dromara/dante-cloud)
- 单体架构演示环境：[https://gitee.com/herodotus/dante-cloud-athena](https://gitee.com/herodotus/dante-cloud-athena)
- 前端工程演示：[https://gitee.com/herodotus/dante-cloud-ui](https://gitee.com/herodotus/dante-cloud-ui)

> 注意：如果您仅是想了解基本的使用方式和使用效果，建议使用单体架构演示环境。具体搭建方式参见[【在线文档】](http://www.herodotus.cn)中，单体版章节。

