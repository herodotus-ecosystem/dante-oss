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
import cn.herodotus.oss.dialect.core.client.AbstractOssClientObjectPool;
import cn.herodotus.oss.dialect.core.exception.OssExecutionException;
import cn.herodotus.oss.dialect.core.exception.OssServerException;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>Description: Aliyun OSS 对象 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/23 16:31
 */
@Service
public class AliyunObjectService extends BaseAliyunService {

    private static final Logger log = LoggerFactory.getLogger(AliyunObjectService.class);

    public AliyunObjectService(AbstractOssClientObjectPool<OSS> ossClientObjectPool) {
        super(ossClientObjectPool);
    }

    public ObjectListing listObjects(ListObjectsRequest request) {
        String function = "listObjects";

        OSS client = getClient();

        try {
            return client.listObjects(request);
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

    public ListObjectsV2Result listObjectsV2(ListObjectsV2Request request) {
        String function = "listObjectsV2";

        OSS client = getClient();

        try {
            return client.listObjectsV2(request);
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

    public VersionListing listVersions(ListVersionsRequest request) {
        String function = "listVersions";

        OSS client = getClient();

        try {
            return client.listVersions(request);
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

    public PutObjectResult putObject(PutObjectRequest request) {
        String function = "putObject";

        OSS client = getClient();

        try {
            return client.putObject(request);
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

    public CopyObjectResult copyObject(CopyObjectRequest request) {
        String function = "copyObject";

        OSS client = getClient();

        try {
            return client.copyObject(request);
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

    public OSSObject getObject(GetObjectRequest request) {
        String function = "getObject";

        OSS client = getClient();

        try {
            return client.getObject(request);
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

    public SimplifiedObjectMeta getSimplifiedObjectMeta(GetObjectRequest request) {
        String function = "getSimplifiedObjectMeta";

        OSS client = getClient();

        try {
            return client.getSimplifiedObjectMeta(request);
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

    public ObjectMetadata getObjectMetadata(GetObjectRequest request) {
        String function = "getObjectMetadata";

        OSS client = getClient();

        try {
            return client.getObjectMetadata(request);
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

    public ObjectMetadata headObject(HeadObjectRequest request) {
        String function = "headObject";

        OSS client = getClient();

        try {
            return client.headObject(request);
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

    public AppendObjectResult appendObject(AppendObjectRequest request) {
        String function = "appendObject";

        OSS client = getClient();

        try {
            return client.appendObject(request);
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

    public DeleteObjectsResult deleteObjects(DeleteObjectsRequest request) {
        String function = "deleteObjects";

        OSS client = getClient();

        try {
            return client.deleteObjects(request);
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

    public VoidResult deleteVersion(DeleteVersionRequest request) {
        String function = "deleteVersion";

        OSS client = getClient();

        try {
            return client.deleteVersion(request);
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

    public DeleteVersionsResult deleteVersions(DeleteVersionsRequest request) {
        String function = "deleteVersions";

        OSS client = getClient();

        try {
            return client.deleteVersions(request);
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

    public boolean doesObjectExist(GenericRequest request) {
        String function = "doesObjectExist";

        OSS client = getClient();

        try {
            return client.doesObjectExist(request);
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

    public RestoreObjectResult restoreObject(RestoreObjectRequest request) {
        String function = "restoreObject";

        OSS client = getClient();

        try {
            return client.restoreObject(request);
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

    public GenericResult processObject(ProcessObjectRequest request) {
        String function = "processObject";

        OSS client = getClient();

        try {
            return client.processObject(request);
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

    public VoidResult createDirectory(CreateDirectoryRequest request) {
        String function = "createDirectory";

        OSS client = getClient();

        try {
            return client.createDirectory(request);
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

    public DeleteDirectoryResult deleteDirectory(DeleteDirectoryRequest request) {
        String function = "deleteDirectory";

        OSS client = getClient();

        try {
            return client.deleteDirectory(request);
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

    public VoidResult renameObject(RenameObjectRequest request) {
        String function = "renameObject";

        OSS client = getClient();

        try {
            return client.renameObject(request);
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
