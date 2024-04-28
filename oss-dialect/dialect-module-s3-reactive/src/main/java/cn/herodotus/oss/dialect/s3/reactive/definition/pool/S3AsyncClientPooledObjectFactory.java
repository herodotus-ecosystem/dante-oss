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

package cn.herodotus.oss.dialect.s3.reactive.definition.pool;

import cn.herodotus.oss.core.s3.properties.S3Properties;
import cn.herodotus.oss.dialect.core.client.AbstractOssClientPooledObjectFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

import java.net.URI;

/**
 * <p>Description: Amazon S3 Client 池化工厂 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/14 16:34
 */
public class S3AsyncClientPooledObjectFactory extends AbstractOssClientPooledObjectFactory<S3AsyncClient> {

    private final S3Properties s3Properties;

    public S3AsyncClientPooledObjectFactory(S3Properties s3Properties) {
        super(s3Properties);
        this.s3Properties = s3Properties;
    }


    @Override
    public S3AsyncClient create() throws Exception {

        // 创建 AWS 认证信息
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey()));

        return S3AsyncClient.crtBuilder()
                .credentialsProvider(credentialsProvider)
                .endpointOverride(URI.create(s3Properties.getEndpoint()))
                .region(Region.CN_NORTH_1)
                .targetThroughputInGbps(20.0)
                .minimumPartSizeInBytes(10 * 1025 * 1024L)
                .checksumValidationEnabled(false)
                .build();
    }
}
