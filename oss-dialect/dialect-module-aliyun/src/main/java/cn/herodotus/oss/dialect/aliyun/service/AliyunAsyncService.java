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

import cn.herodotus.oss.dialect.aliyun.definition.service.BaseAliyunService;
import cn.herodotus.oss.dialect.core.exception.OssExecutionException;
import cn.herodotus.oss.dialect.core.exception.OssServerException;
import cn.herodotus.stirrup.core.definition.support.AbstractObjectPool;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>Description: Aliyun Java SDK 异步相关操作 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/23 21:50
 */
@Service
public class AliyunAsyncService extends BaseAliyunService {

    private static final Logger log = LoggerFactory.getLogger(AliyunAsyncService.class);

    public AliyunAsyncService(AbstractObjectPool<OSS> clientObjectPool) {
        super(clientObjectPool);
    }

    /**
     * 设置异步获取任务
     *
     * @param request {@link SetAsyncFetchTaskRequest}
     * @return {@link SetAsyncFetchTaskResult}
     */
    public SetAsyncFetchTaskResult setAsyncFetchTask(SetAsyncFetchTaskRequest request) {
        String function = "setAsyncFetchTask";

        OSS client = getClient();

        try {
            return client.setAsyncFetchTask(request);
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
     * 获取异步获取任务信息
     *
     * @param request {@link GetAsyncFetchTaskRequest}
     * @return {@link GetAsyncFetchTaskResult}
     */
    public GetAsyncFetchTaskResult getAsyncFetchTask(GetAsyncFetchTaskRequest request) {
        String function = "getAsyncFetchTask";

        OSS client = getClient();

        try {
            return client.getAsyncFetchTask(request);
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
     * 对指定的文件应用异步操作.
     *
     * @param request {@link AsyncProcessObjectRequest}
     * @return {@link AsyncProcessObjectResult}
     */
    public AsyncProcessObjectResult asyncProcessObject(AsyncProcessObjectRequest request) {
        String function = "asyncProcessObject";

        OSS client = getClient();

        try {
            return client.asyncProcessObject(request);
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