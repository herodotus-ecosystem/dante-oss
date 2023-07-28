<h1 align="center">Dante OSS</h1>
<h2 align="center">丰富 · 全面 | 简单 · 便捷</h2>
<p align="center">让 Minio 的集成和使用更简单</p>

---

<p align="center">
    <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://shields.io/badge/Spring%20Boot-3.1.2-blue.svg?logo=spring" alt="Spring Boot 3.1.2"></a>
    <a href="#" target="_blank"><img src="https://shields.io/badge/Version-1.2.0-red.svg?logo=spring" alt="Version 1.2.0"></a>
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
    <a href="https://www.murphysec.com/console/report/1669933209810452480/1669933209848201216" alt="Security Status"><img src="https://www.murphysec.com/platform3/v31/badge/1669933209848201216.svg" /></a>
</p>
<p align="center">
    <a href="https://gitee.com/dromara/dante-cloud">示例微服务应用</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/herodotus/dante-cloud-athena">示例单体应用</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/herodotus/dante-cloud-ui">示例前端应用</a>
</p>

<h1 align="center"> 如果您觉得有帮助，请点右上角 "Star" 支持一下，谢谢！</h1>

## 简介 | Intro

Dante OSS 是一款简化 Minio 操作的开源框架。通过对原有 Minio Java SDK 的深度封装，简化 Minio API 使用复杂度，更方便的实现复杂的 Minio 管理操作，降低 Minio 应用开发门槛。

MinIO 是一款高性能、分布式的对象存储系统。Minio 这款开源的分布式对象存储服务在国外已经相当受欢迎，并且国内也有多中小型互联网公司使用它来作为对象存储服务。虽然 Minio 相关的资料和示例在网络上已经非常丰富，但是为什么还要推出 Dante OSS 这样的项目？

- 初次接触 Minio，特别是想要通过 Java 集成 Minio 开发对象存储应用是，还是需要投入一定的时间和精力去了解其原理阅读相关文档。
- Minio SDK 中的函数方法，涉及的参数较多，抽象层度比较高，每次使用都需要反复查阅源代码才能摸清具体使用方式。
- 想要与已有的应用进行整合，多少都要投入时间精力，进行一定程度的封装和改造。
- 网上相关资料多，要么比较零散不成体系，要么比较单一仅针对常规上传下载应用，Minio 自身很多特性都不涉及。

## 设计 | Design thinking

Dante OSS 最初的设计目标，是深度封装 Minio Java SDK，可以更方便的实现复杂的 Minio 管理操作。随着版本的不断迭代，以及更多用户需求的收集，Amazon S3、阿里云等 OSS 操作也不断地被融入进来，Dante OSS 的设计思想也在不断迭代。

目前 Amazon S3 已经成为 OSS 事实的标准，各 OSS 产品大多兼容 S3 标准。常规的、基础的 OSS 操作除了使用各厂商 OSS 提供的 SDK 外，使用 Amazon S3 SDK 也可以支持。如果你的需求仅是常规的上传、下载等，那么使用 Amazon S3 SDK 作为统一实现可完全满足。

但是如果您的需求涉及更丰富、更新细致的OSS管理功能，那么 Amazon S3 SDK 是无法满足的。因为即使相同的功能，比如说 Tagging、Replication，不同的 OSS 产品实现不同，至少请求参数和返回值就不同；还存在各 OSS 结合自身实际个性化定义的功能，比如说阿里云的 Qos、Vpcip 等。

因此，Dante OSS 在维持原有简化 Minio 常规及复杂管理操作目标的基础之上，借鉴 JPA 标准化设计思想，逐步提取和抽象 OSS 标准化操作，形成统一的 Java API 定义，同时封装可操作任意厂商的、统一的 REST API，形成定义统一、动态实现的应用模式（类似于 Hibernate 是 JPA 的一种实现），以方便不同 OSS 的切换和迁移。

## 优点 | Advantages

- **零额外学习成本**: 开发者只要会 Spring 和 REST 基本开发，即可无缝集成和使用 Dante OSS
- **降低开发者门槛**: 屏蔽 Minio 标准 Java SDK 使用复杂度，使用 Spring 环境标准方式对原有 API 进行简化封装。Service API 和 REST API 开箱即用
- **包含的功能丰富**: 改造了 Minio Java SDK 的几乎全部功能，且对大文件分片上传、秒传、直传、断点续传等功能，均采用业内最优解决方案进行实现和融合
- **规范优雅的代码**: 所有函数参数，并未破坏原有 Minio 代码构造器结构，而是在原有方式的基础上抽象简化，编程体验和代码可读性大幅提升
- **完善的注释文档**: 对请求参数、方法、REST API、Validation 提供详实的注释、说明和 OpenAPI 标注，用途用法一目了然，无需再翻阅 Minio 文档和源代码，帮助您节省更多时间
- **丰富的稳定保障**: 统一的、人性化的错误体系、内置的 REST API 防刷、幂等保护、详实准确的 Spring Validation 校验。
- **完整的前端示例**：前端采用一个完整的项目而非 Demo 的形式，全面的展示了前后端交互涉及、接口调用、参数使用、TS 类型定义等各方面内容，可直接用于实际项目或简单改造后构建自己的产品

## 对比 | Compare

### 1. 不只是简单的 Spring Boot Starter 构建

1. 构建统一的错误，可以返回更人性化、更易理解的错误信息，同时兼顾更详细错误信息的返回，方便开发人员理解和定位问题。
2. 采用更易理解和使用的格式对 Minio Java SDK 参数进行重新定义。规避 Minio 默认 XML 方式参数多、不易理解使用、与前端交互不方便等问题。
3. 隐藏 Minio Java SDK 不易理解和使用的细节，提供详实的注释说明，开发人员在使用时无需再通过翻阅 Minio 在线文档和源代码来了解各个 API 使用细节。
4. 提供统一标准的 REST API，以及 OpenAPI Swagger3 文档描述和准确的 Spring Validation 校验，可直接集成至系统中使用。
5. Minio Client 对象池、自定义极简 Minio Server 访问反向代理，提升
6. 逐步丰富不同厂商 OSS 操作，作为不同 OSS 实现。
7. 抽象统一 REST API，实现统一接口操作不同厂商 OSS。

### 2. 标准化业务逻辑和解决方案集合

1. 不只是上传、下载等常用方法的封装，涵盖 Minio Java SDK 支持的所有方法和操作。
2. 选择业内最优的解决方案，实现和集成大文件分片上传、秒传、直传、断点续传等主要业务需求功能。
3. 结合自身应用经验和需求，将 Minio API 进一步组合成符合实际应用的业务逻辑和功能处理。
4. 采用一个基于 Vue3、Typescript5、Vite4、Pinia 2 的完整的前端项目作为集成示例，包括详细的 Typescript 类型定义以及 vue-simple-uploader 等主流组件集成和使用方法。
5. 提供基于 Spring Authorization Server 的单体版、微服务版案例，从 SDK、Spring Boot Starter 到完整项目任你选择。

### 3. 具体差异说明

具体差异，参见在线文档[【功能说明章节】](http://www.herodotus.cn/ecosphere/oss/how-to-use.html)

## 结构 | Structure

```
dante-oss
├── oss-bom -- Dante OSS 顶级 Maven 依赖，统一控制依赖及其版本
├── oss-definition -- OSS 抽象定义
├── oss-dialect -- 不同厂商 OSS 实现。
├    ├── dialect-core -- 不同厂商 OSS 实现通用代码模块
├    ├── dialect-sdk-aliyun -- Aliyun OSS Java SDK 封装代码模块
├    ├── dialect-sdk-minio -- Minio OSS Java SDK 封装代码模块
├    └── dialect-sdk-s3 -- Amazon S3 OSS Java SDK 封装代码模块
├── oss-rest -- OSS 操作 REST 模块
├    ├── rest-sdk-integration -- 支持不同厂商OSS的统一通用 REST API，
├    ├── rest-sdk-minio -- Minio 基础 REST API模块
├    └── rest-sdk-scenario -- Minio 扩展及应用方案整合模块
├── oss-starter -- Dante OSS 相关 Starter
├    ├── oss-aliyun-spring-boot-starter -- 用于独立使用的 Aliyun OSS Java SDK 封装 Starter。
├    ├── oss-minio-spring-boot-starter -- 用于独立使用的 Aliyun OSS Java SDK 封装 Starter。
├    ├── oss-s3-spring-boot-starter -- 用于独立使用的 Aliyun OSS Java SDK 封装 Starter。
└──  └── oss-spring-boot-starter -- Dante OSS 统一 Starter
```

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
    dialect: minio
    minio:
      endpoint: http://127.0.0.1:9000
      access-key: XXXXXXXXX
      secret-key: XXXXXXXXX
      use-proxy: true
      proxy-source-endpoint: http://localhost:3000/api
```

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

除了 `dialect-core` 和 `oss-definition` 模块以外，其它所有模块均可以单独使用。可以根据自身需要，仅选择某个模块进行使用。

#### 1. dialect-sdk-aliyun

包含对 Aliyun 基础 API 封装的 Service 代码, 可以单独使用，也可作为 OSS 统一抽象的实现方式之一，通过修改配置生效使用。使用 `oss-aliyun-spring-boot-starter` 可开启自动配置。

目前暂不提供 Aliyun REST API 封装，请根据自身的需要直接申请使用阿里云 REST API。

#### 2. dialect-sdk-s3

包含对 Amazon S3 基础 API 封装的 Service 代码, 可以单独使用，也可作为 OSS 统一抽象的实现方式之一，通过修改配置生效使用。使用 `oss-s3-spring-boot-starter` 可开启自动配置。

目前暂不提供 Amazon REST API 封装，如需使用根据设置需要申请使用。

#### 3. dialect-sdk-minio

包含对 Minio 基础 API 封装的 Service 代码, 可以单独使用，也可作为 OSS 统一抽象的实现方式之一，通过修改配置生效使用。

提供 Minio 标准操作 REST API 封装 `rest-sdk-minio`（不包含大文件分片上传等扩展性业务功能）。

使用 `oss-minio-spring-boot-starter` 可统一开启 Minio Service 和 REST API 自动配置。

#### 4. rest-sdk-integration

`rest-sdk-integration` 是通过对 Minio、Aliyun、Amazon S3 现有 API 共性内容抽象，形成的统一操作 REST API。目标是形成类似于 Spring Data Repository 形式的统一 REST API，一套 REST API 支持不同的 OSS 厂商。

#### 5. rest-sdk-minio

提供 Minio 标准操作 REST API 封装。使用 `oss-minio-spring-boot-starter` 可统一开启 Minio Service 和 REST API 自动配置。

#### 6. rest-sdk-scenario

Minio 标准操作 API 之外的，大文件分片、端点续传等主流对象存储业务解决方案以及 Minio 管理 API 的封装。目前仅支持 Minio 相关操作

#### 7. oss-spring-boot-starter

Dante OSS 的所有内容，可直接引入使用。以 OSS 共性抽象为基础，通过 Spring Boot 配置，实现不同 OSS 操作实现的切换。目前以 Minio 作为默认实现，同时提供 Minio 相关完整的 REST API。Aliyun 和 Amazon S3 目前仅包含对其 Java SDK 封装的 Service API，作为不同 OSS 实现的可选项。如要使用其 REST API 的方式，请直接按照对应厂商官网文档操作申请即可，暂不考虑将这一部分融入 Dante OSS。

## 贡献 | Committer

1. 在Gitee fork项目到自己的repo 
2. 把fork过去的项目也就是你的项目clone到你的本地
3. 修改代码（记得一定要修改 develop 分支） 
4. commit 代码，push 到自己的库（develop分支） 
5. 登录Gitee 在你首页可以看到一个 pull request 按钮，点击它，填写一些说明信息，然后提交即可。 
6. 等待维护者合并

## 反馈 | Feedback

- 欢迎提交[ISSUE](https://gitee.com/herodotus/dante-oss/issues) ，请写清楚问题的具体原因，重现步骤和环境

## 演示 | Example

Dante OSS 作为 Dante Cloud 生态产品，不在单独提供演示环境和示例，请直接使用 Dante 环境查看效果和了解使用，这样也更贴近实际。

- 微服务演示环境：[https://gitee.com/dromara/dante-cloud](https://gitee.com/dromara/dante-cloud)
- 单体架构演示环境：[https://gitee.com/herodotus/dante-cloud-athena](https://gitee.com/herodotus/dante-cloud-athena)
- 前端工程演示：[https://gitee.com/herodotus/dante-cloud-ui](https://gitee.com/herodotus/dante-cloud-ui)

> 注意：如果您仅是想了解基本的使用方式和使用效果，建议使用单体架构演示环境。具体搭建方式参见[【在线文档】](http://www.herodotus.cn)中，单体版章节。

## 许可 | License 

本项目基于 Apache License Version 2.0 开源协议，可用于商业项目，但必须遵守以下补充条款。

- 不得将本软件应用于危害国家安全、荣誉和利益的行为，不能以任何形式用于非法为目的的行为。
- 在延伸的代码中（修改现有源代码衍生的代码中）需要带有原来代码中的协议、版权声明和其他原作者 规定需要包含的说明（请尊重原作者的著作权，不要删除或修改文件中的Copyright和@author信息） 更不要，全局替换源代码中的 Dante OSS、Herodotus 或 码匠君 等字样，否则你将违反本协议条款承担责任。
- 您若套用本软件的一些代码或功能参考，请保留源文件中的版权和作者，需要在您的软件介绍明显位置 说明出处，举例：本软件基于 Dante Cloud 微服务架构 或 Dante OSS，并附带链接：https://www.herodotus.cn
- 任何基于本软件而产生的一切法律纠纷和责任，均于作者无关。
- 如果你对本软件有改进，希望可以贡献给我们，双向奔赴互相成就才是王道。
- 本项目已申请软件著作权，请尊重开源。