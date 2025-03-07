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

package cn.herodotus.oss.dialect.autoconfigure.properties;

import cn.herodotus.oss.dialect.core.constants.OssConstants;
import cn.herodotus.oss.dialect.core.enums.Dialect;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: OSS 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/24 23:19
 */
@ConfigurationProperties(prefix = OssConstants.PROPERTY_PREFIX_OSS)
public class OssProperties {

    /**
     * 是否启用代理，防止前端直接访问
     */
    private Boolean useProxy = true;

    /**
     * 代理请求发送源地址。例如：前端 http://localhost:3000。注意如果有前端有配置代理需要加上
     */
    private String proxySourceEndpoint;

    /**
     * 采用 Minio SDK 作为默认实现
     */
    private Dialect dialect = Dialect.MINIO;

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
}
