/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Herodotus Cloud.
 *
 * Herodotus Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Herodotus Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.vip>.
 */

package cn.herodotus.oss.dialect.reactive.s3.definition.pool;

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
