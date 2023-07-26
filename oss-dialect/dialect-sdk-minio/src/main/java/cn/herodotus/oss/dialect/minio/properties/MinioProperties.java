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

package cn.herodotus.oss.dialect.minio.properties;

import cn.herodotus.oss.dialect.core.constants.OssConstants;
import cn.herodotus.oss.dialect.core.definition.properties.AbstractOssProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: Minio 配置参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/8 10:31
 */
@ConfigurationProperties(prefix = OssConstants.PROPERTY_OSS_MINIO)
public class MinioProperties extends AbstractOssProperties {

    private String bucketNamePrefix;

    /**
     * 文件名中时间标识内容格式。
     */
    private String timestampFormat = "yyyy-MM-dd";

    /**
     * 是否启用代理，防止前端直接访问
     */
    private Boolean useProxy = true;

    /**
     * 代理请求发送源地址。例如：前端 http://localhost:3000。注意如果有前端有配置代理需要加上
     */
    private String proxySourceEndpoint;

    public String getBucketNamePrefix() {
        return bucketNamePrefix;
    }

    public void setBucketNamePrefix(String bucketNamePrefix) {
        this.bucketNamePrefix = bucketNamePrefix;
    }

    public String getTimestampFormat() {
        return timestampFormat;
    }

    public void setTimestampFormat(String timestampFormat) {
        this.timestampFormat = timestampFormat;
    }

    public Boolean getUseProxy() {
        return useProxy;
    }

    public void setUseProxy(Boolean useProxy) {
        this.useProxy = useProxy;
    }

    public String getProxySourceEndpoint() {
        return proxySourceEndpoint;
    }

    public void setProxySourceEndpoint(String proxySourceEndpoint) {
        this.proxySourceEndpoint = proxySourceEndpoint;
    }
}
