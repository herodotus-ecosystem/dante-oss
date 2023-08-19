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

package cn.herodotus.oss.solution.properties;

import cn.herodotus.oss.solution.constants.OssSolutionConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: OSS 代理配置参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/19 18:34
 */
@ConfigurationProperties(prefix = OssSolutionConstants.PROPERTY_OSS_PROXY)
public class OssProxyProperties {

    /**
     * 是否开启。默认开水器
     */
    private Boolean enabled = true;

    /**
     * 代理请求转发源地址。例如：前端 http://localhost:3000。注意如果有前端有配置代理需要加上
     */
    private String source;

    /**
     * 代理请求转发目的地址
     */
    private String destination;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
