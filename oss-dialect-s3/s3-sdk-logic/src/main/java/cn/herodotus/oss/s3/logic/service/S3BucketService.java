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

package cn.herodotus.oss.s3.logic.service;

import cn.herodotus.oss.definition.core.exception.OssServerException;
import cn.herodotus.oss.s3.logic.definition.pool.S3ClientObjectPool;
import cn.herodotus.oss.s3.logic.definition.service.BaseS3ClientService;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: Amazon S3 存储桶管理 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/14 16:04
 */
@Service
public class S3BucketService extends BaseS3ClientService {

    private static final Logger log = LoggerFactory.getLogger(S3BucketService.class);

    public S3BucketService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 返回指定存储桶中版本的摘要信息列表
     *
     * @param request {@link ListVersionsRequest}
     * @return {@link VersionListing}
     */
    public VersionListing listVersions(ListVersionsRequest request) {
        String function = "listVersions";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listVersions(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return 是否存在 true 存在；false 不存在
     */
    public boolean doesBucketExist(String bucketName) {
        String function = "doesBucketExistV2";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.doesBucketExistV2(bucketName);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 此操作可用于确定存储桶是否存在以及您是否有权访问它。如果存储桶存在并且您有权访问，则此操作返回200 OK。
     *
     * @param request {@link HeadBucketRequest}
     * @return {@link HeadBucketResult}
     */
    public HeadBucketResult headBucket(HeadBucketRequest request) {
        String function = "headBucket";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.headBucket(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶列表
     *
     * @return 存储桶列表
     */
    public List<Bucket> listBuckets() {
        String function = "listBuckets";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.listBuckets();
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 删除存储桶
     *
     * @param request {@link CreateBucketRequest}
     */
    public void deleteBucket(DeleteBucketRequest request) {
        String function = "deleteBucket";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.deleteBucket(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储桶位置
     *
     * @param request {@link GetBucketLocationRequest}
     * @return 存储桶位置 {@link String}
     */
    public String getBucketLocation(GetBucketLocationRequest request) {
        String function = "getBucketLocation";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketLocation(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 创建存储桶
     *
     * @param request {@link CreateBucketRequest}
     * @return {@link Bucket}
     */
    public Bucket createBucket(CreateBucketRequest request) {
        String function = "createBucket";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.createBucket(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }


}
