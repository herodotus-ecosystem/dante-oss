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
import com.amazonaws.services.s3.model.GetObjectLockConfigurationRequest;
import com.amazonaws.services.s3.model.GetObjectLockConfigurationResult;
import com.amazonaws.services.s3.model.SetObjectLockConfigurationRequest;
import com.amazonaws.services.s3.model.SetObjectLockConfigurationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>Description: Amazon S3 对象访合法持有 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/16 21:14
 */
@Service
public class S3ObjectLockConfigurationService extends BaseS3ClientService {

    private static final Logger log = LoggerFactory.getLogger(S3ObjectLockConfigurationService.class);

    public S3ObjectLockConfigurationService(S3ClientObjectPool s3ClientObjectPool) {
        super(s3ClientObjectPool);
    }

    /**
     * 获取对象锁定配置
     *
     * @param request {@link GetObjectLockConfigurationRequest}
     * @return {@link GetObjectLockConfigurationResult}
     */
    public GetObjectLockConfigurationResult getObjectLockConfiguration(GetObjectLockConfigurationRequest request) {
        String function = "getObjectLockConfiguration";

        AmazonS3 amazonS3 = getAmazonS3();
        try {
            return amazonS3.getObjectLockConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }

    /**
     * 设置对象锁定配置
     *
     * @param request {@link SetObjectLockConfigurationRequest}
     * @return {@link SetObjectLockConfigurationResult}
     */
    public SetObjectLockConfigurationResult setObjectLockConfiguration(SetObjectLockConfigurationRequest request) {
        String function = "setObjectLockConfiguration";

        AmazonS3 amazonS3 = getAmazonS3();
        try {
            return amazonS3.setObjectLockConfiguration(request);
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(amazonS3);
        }
    }
}
