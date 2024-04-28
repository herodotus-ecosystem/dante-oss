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

import cn.herodotus.oss.dialect.core.client.AbstractOssClientPooledObjectFactory;
import cn.herodotus.stirrup.core.definition.support.AbstractObjectPool;
import software.amazon.awssdk.services.s3.S3AsyncClient;

/**
 * <p>Description: Amazon S3 Client 对象池 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/14 16:33
 */
public class S3AsyncClientObjectPool extends AbstractObjectPool<S3AsyncClient> {

    public S3AsyncClientObjectPool(AbstractOssClientPooledObjectFactory<S3AsyncClient> factory) {
        super(factory, factory.getOssProperties().getPool());
    }
}
