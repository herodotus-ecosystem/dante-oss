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

package cn.herodotus.oss.dialect.minio.repository;

import cn.herodotus.oss.core.arguments.bucket.CreateBucketArguments;
import cn.herodotus.oss.core.arguments.bucket.DeleteBucketArguments;
import cn.herodotus.oss.core.definition.repository.OssBucketRepository;
import cn.herodotus.oss.core.domain.bucket.BucketDomain;
import cn.herodotus.oss.core.minio.converter.domain.BucketToDomainConverter;
import cn.herodotus.oss.core.minio.utils.MinioConverterUtils;
import cn.herodotus.oss.dialect.minio.converter.arguments.ArgumentsToMakeBucketArgsConverter;
import cn.herodotus.oss.dialect.minio.converter.arguments.ArgumentsToRemoveBucketArgsConverter;
import cn.herodotus.oss.dialect.minio.definition.service.BaseMinioService;
import cn.herodotus.oss.dialect.minio.service.MinioBucketService;
import cn.herodotus.stirrup.core.definition.support.AbstractObjectPool;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: Minio Java OSS API 存储桶操作实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/24 19:13
 */
@Service
public class MinioBucketRepository extends BaseMinioService implements OssBucketRepository {

    private static final Logger log = LoggerFactory.getLogger(MinioBucketRepository.class);

    private final MinioBucketService minioBucketService;

    public MinioBucketRepository(AbstractObjectPool<MinioClient> ossClientObjectPool, MinioBucketService minioBucketService) {
        super(ossClientObjectPool);
        this.minioBucketService = minioBucketService;
    }

    @Override
    public boolean doesBucketExist(String bucketName) {
        return minioBucketService.bucketExists(bucketName);
    }

    @Override
    public List<BucketDomain> listBuckets() {
        return MinioConverterUtils.toDomains(minioBucketService.listBuckets(), new BucketToDomainConverter());
    }

    @Override
    public BucketDomain createBucket(String bucketName) {
        minioBucketService.makeBucket(bucketName);
        return null;
    }

    @Override
    public BucketDomain createBucket(CreateBucketArguments arguments) {
        Converter<CreateBucketArguments, MakeBucketArgs> toArgs = new ArgumentsToMakeBucketArgsConverter();
        minioBucketService.makeBucket(toArgs.convert(arguments));
        return null;
    }

    @Override
    public void deleteBucket(String bucketName) {
        minioBucketService.removeBucket(bucketName);
    }

    @Override
    public void deleteBucket(DeleteBucketArguments arguments) {
        Converter<DeleteBucketArguments, RemoveBucketArgs> toArgs = new ArgumentsToRemoveBucketArgsConverter();
        minioBucketService.removeBucket(toArgs.convert(arguments));
    }


}
