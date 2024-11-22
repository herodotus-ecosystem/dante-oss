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

package cn.herodotus.oss.dialect.s3.service;

import cn.herodotus.oss.dialect.core.exception.OssServerException;
import cn.herodotus.oss.dialect.s3.definition.pool.S3ClientObjectPool;
import cn.herodotus.oss.dialect.s3.definition.service.BaseS3Service;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>Description: Amazon S3 存储桶访问策略 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/16 17:23
 */
@Service
public class S3BucketPolicyService extends BaseS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3BucketPolicyService.class);

    public S3BucketPolicyService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 删除存储桶访问策略
     *
     * @param request {@link DeleteBucketPolicyRequest}
     */
    public void deleteBucketPolicy(DeleteBucketPolicyRequest request) {
        String function = "deleteBucketPolicy";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.deleteBucketPolicy(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储访问策略
     *
     * @param request {@link GetBucketPolicyRequest}
     * @return {@link BucketPolicy}
     */
    public BucketPolicy getBucketPolicy(GetBucketPolicyRequest request) {
        String function = "getBucketPolicy";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketPolicy(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 获取存储访问策略状态
     *
     * @param request {@link GetBucketPolicyStatusRequest}
     * @return {@link GetBucketPolicyStatusResult}
     */
    public GetBucketPolicyStatusResult getBucketPolicyStatus(GetBucketPolicyStatusRequest request) {
        String function = "getBucketPolicyStatus";

        AmazonS3 amazonS3 = getClient();
        try {
            return amazonS3.getBucketPolicyStatus(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置存储访问策略
     *
     * @param request {@link SetBucketPolicyRequest}
     */
    public void setBucketOwnershipControls(SetBucketPolicyRequest request) {
        String function = "setBucketOwnershipControls";

        AmazonS3 amazonS3 = getClient();
        try {
            amazonS3.setBucketPolicy(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
