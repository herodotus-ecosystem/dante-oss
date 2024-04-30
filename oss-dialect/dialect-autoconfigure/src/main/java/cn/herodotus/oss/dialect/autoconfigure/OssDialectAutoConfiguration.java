/*
 * Copyright (c) 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante OSS 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-oss>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-oss>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.oss.dialect.autoconfigure;

import cn.herodotus.oss.dialect.aliyun.configuration.OssDialectAliyunConfiguration;
import cn.herodotus.oss.dialect.autoconfigure.annotation.ConditionalOnUseAliyunDialect;
import cn.herodotus.oss.dialect.autoconfigure.annotation.ConditionalOnUseMinioDialect;
import cn.herodotus.oss.dialect.autoconfigure.annotation.ConditionalOnUseS3Dialect;
import cn.herodotus.oss.dialect.autoconfigure.customizer.OssErrorCodeMapperBuilderCustomizer;
import cn.herodotus.oss.dialect.autoconfigure.properties.OssProperties;
import cn.herodotus.oss.dialect.minio.config.OssDialectMinioConfiguration;
import cn.herodotus.oss.dialect.reactive.minio.config.OssDialectMinioReactiveConfiguration;
import cn.herodotus.oss.dialect.s3.config.OssDialectS3Configuration;
import cn.herodotus.oss.dialect.s3.reactive.config.OssDialectS3ReactiveConfiguration;
import cn.herodotus.stirrup.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: OSS Dialect 自动配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/19 15:30
 */
@AutoConfiguration
@EnableConfigurationProperties(OssProperties.class)
public class OssDialectAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OssDialectAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [OSS Dialect] Configure.");
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer ossErrorCodeMapperBuilderCustomizer() {
        OssErrorCodeMapperBuilderCustomizer customizer = new OssErrorCodeMapperBuilderCustomizer();
        log.trace("[Herodotus] |- Strategy [Oss ErrorCodeMapper Builder Customizer] Configure.");
        return customizer;
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnUseAliyunDialect
    @Import({
            OssDialectAliyunConfiguration.class
    })
    static class UserAliyunDialectConfiguration {

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnUseMinioDialect
    @Import({
            OssDialectMinioConfiguration.class,
            OssDialectMinioReactiveConfiguration.class
    })
    static class UserMinioDialectConfiguration {

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnUseS3Dialect
    @Import({
            OssDialectS3Configuration.class,
            OssDialectS3ReactiveConfiguration.class
    })
    static class UserS3DialectConfiguration {

    }
}
