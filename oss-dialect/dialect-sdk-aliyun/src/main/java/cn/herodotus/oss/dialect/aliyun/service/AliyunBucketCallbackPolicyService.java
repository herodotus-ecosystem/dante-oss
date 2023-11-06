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

package cn.herodotus.oss.dialect.aliyun.service;

import cn.herodotus.engine.assistant.core.definition.AbstractObjectPool;
import cn.herodotus.oss.dialect.aliyun.definition.service.BaseAliyunService;
import cn.herodotus.oss.dialect.core.exception.OssExecutionException;
import cn.herodotus.oss.dialect.core.exception.OssServerException;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GenericRequest;
import com.aliyun.oss.model.GetBucketCallbackPolicyResult;
import com.aliyun.oss.model.SetBucketCallbackPolicyRequest;
import com.aliyun.oss.model.VoidResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>Description: Aliyun OSS 存储桶回调策略 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/23 22:03
 */
@Service
public class AliyunBucketCallbackPolicyService extends BaseAliyunService {

    private static final Logger log = LoggerFactory.getLogger(AliyunBucketCallbackPolicyService.class);

    public AliyunBucketCallbackPolicyService(AbstractObjectPool<OSS> ossClientObjectPool) {
        super(ossClientObjectPool);
    }

    /**
     * 设置存储桶实例的回调策略
     *
     * @param request {@link SetBucketCallbackPolicyRequest}
     * @return {@link VoidResult}
     */
    public VoidResult setBucketCallbackPolicy(SetBucketCallbackPolicyRequest request) {
        String function = "setBucketCallbackPolicy";

        OSS client = getClient();

        try {
            return client.setBucketCallbackPolicy(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssExecutionException(e.getMessage());
        } finally {
            close(client);
        }
    }

    /**
     * 获取存储桶实例的回调策略
     *
     * @param request {@link GenericRequest}
     * @return {@link GetBucketCallbackPolicyResult}
     */
    public GetBucketCallbackPolicyResult getBucketCallbackPolicy(GenericRequest request) {
        String function = "getBucketCallbackPolicy";

        OSS client = getClient();

        try {
            return client.getBucketCallbackPolicy(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssExecutionException(e.getMessage());
        } finally {
            close(client);
        }
    }

    /**
     * 删除存储桶实例的回调策略
     *
     * @param request {@link GenericRequest}
     * @return {@link VoidResult}
     */
    public VoidResult deleteBucketCallbackPolicy(GenericRequest request) {
        String function = "deleteBucketCallbackPolicy";

        OSS client = getClient();

        try {
            return client.deleteBucketCallbackPolicy(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssExecutionException(e.getMessage());
        } finally {
            close(client);
        }
    }
}
