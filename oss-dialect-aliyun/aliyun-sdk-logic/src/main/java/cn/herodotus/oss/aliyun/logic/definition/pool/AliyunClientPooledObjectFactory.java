/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
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

package cn.herodotus.oss.aliyun.logic.definition.pool;

import cn.herodotus.oss.aliyun.logic.properties.AliyunProperties;
import cn.herodotus.oss.definition.core.client.AbstractOssClientPooledObjectFactory;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.STSAssumeRoleSessionCredentialsProvider;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.pool2.PooledObject;

/**
 * <p>Description: Aliyun OSS 基础 Client 池化工厂 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/23 11:48
 */
public class AliyunClientPooledObjectFactory extends AbstractOssClientPooledObjectFactory<OSS> {

    private final AliyunProperties aliyunProperties;

    public AliyunClientPooledObjectFactory(AliyunProperties aliyunProperties) {
        super(aliyunProperties);
        this.aliyunProperties = aliyunProperties;
    }

    @Override
    public OSS create() throws Exception {

        // 创建STSAssumeRoleSessionCredentialsProvider实例。
        STSAssumeRoleSessionCredentialsProvider credentialsProvider = CredentialsProviderFactory
                .newSTSAssumeRoleSessionCredentialsProvider(
                        aliyunProperties.getRegion(),
                        aliyunProperties.getAccessKey(),
                        aliyunProperties.getSecretKey(),
                        aliyunProperties.getRole());

        ClientBuilderConfiguration configuration = new ClientBuilderConfiguration();

        return new OSSClientBuilder().build(aliyunProperties.getEndpoint(), credentialsProvider, configuration);
    }

    @Override
    public void destroyObject(PooledObject<OSS> p) throws Exception {
        OSS client = p.getObject();
        if (ObjectUtils.isNotEmpty(client)) {
            client.shutdown();
        }
    }
}
