/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante OSS licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante OSS 是 Dante Cloud 对象存储组件库 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante OSS 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.oss.dialect.s3.configuration;

import cn.herodotus.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import cn.herodotus.oss.dialect.s3.definition.pool.S3ClientPooledObjectFactory;
import cn.herodotus.oss.dialect.s3.properties.S3Properties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: S3 客户端配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/14 16:14
 */
@Configuration(proxyBeanMethods = false)
public class S3ClientConfiguration {

    private static final Logger log = LoggerFactory.getLogger(S3ClientConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- SDK [S3 Client] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public S3ClientObjectPool s3ClientObjectPool(S3Properties s3Properties) {
        S3ClientPooledObjectFactory factory = new S3ClientPooledObjectFactory(s3Properties);
        S3ClientObjectPool pool = new S3ClientObjectPool(factory);
        log.trace("[Herodotus] |- Bean [S3 Client Pool] Auto Configure.");
        return pool;
    }
}
