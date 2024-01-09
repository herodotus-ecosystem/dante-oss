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

import cn.herodotus.stirrup.kernel.definition.support.AbstractObjectPool;
import cn.herodotus.oss.dialect.aliyun.definition.service.BaseAliyunService;
import cn.herodotus.oss.dialect.core.exception.OssExecutionException;
import cn.herodotus.oss.dialect.core.exception.OssServerException;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DoMetaQueryRequest;
import com.aliyun.oss.model.DoMetaQueryResult;
import com.aliyun.oss.model.GetMetaQueryStatusResult;
import com.aliyun.oss.model.VoidResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>Description: Aliyun OSS MetaQuery Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/23 22:05
 */
@Service
public class AliyunMetaQueryService extends BaseAliyunService {

    private static final Logger log = LoggerFactory.getLogger(AliyunMetaQueryService.class);

    public AliyunMetaQueryService(AbstractObjectPool<OSS> ossClientObjectPool) {
        super(ossClientObjectPool);
    }

    /**
     * 打开 OSS服务器 MetaQuery 配置
     *
     * @param bucketName 存储桶名称
     * @return {@link VoidResult}
     */
    public VoidResult openMetaQuery(String bucketName) {
        String function = "openMetaQuery";

        OSS client = getClient();

        try {
            return client.openMetaQuery(bucketName);
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
     * 获取 OSS服务器 MetaQueryStatus 配置
     *
     * @param bucketName 存储桶名称
     * @return {@link GetMetaQueryStatusResult}
     */
    public GetMetaQueryStatusResult getMetaQueryStatus(String bucketName) {
        String function = "getMetaQueryStatus";

        OSS client = getClient();

        try {
            return client.getMetaQueryStatus(bucketName);
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
     * 查询符合指定条件的文件
     *
     * @param request {@link DoMetaQueryRequest}
     * @return {@link DoMetaQueryResult}
     */
    public DoMetaQueryResult doMetaQuery(DoMetaQueryRequest request) {
        String function = "doMetaQuery";

        OSS client = getClient();

        try {
            return client.doMetaQuery(request);
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
     * 关闭元数据管理
     *
     * @param bucketName 存储桶名称
     * @return {@link VoidResult}
     */
    public VoidResult closeMetaQuery(String bucketName) {
        String function = "closeMetaQuery";

        OSS client = getClient();

        try {
            return client.closeMetaQuery(bucketName);
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
