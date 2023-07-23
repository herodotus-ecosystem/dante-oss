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

package cn.herodotus.oss.aliyun.logic.service;

import cn.herodotus.oss.aliyun.logic.definition.service.BaseAliyunClientService;
import cn.herodotus.oss.definition.core.client.AbstractOssClientObjectPool;
import cn.herodotus.oss.definition.core.exception.OssClientPoolErrorException;
import cn.herodotus.oss.definition.core.exception.OssServerException;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/23 16:31
 */
public class AliyunObjectService extends BaseAliyunClientService {

    private static final Logger log = LoggerFactory.getLogger(AliyunBucketService.class);

    public AliyunObjectService(AbstractOssClientObjectPool<OSS> ossClientObjectPool) {
        super(ossClientObjectPool);
    }

    public ObjectListing listObjects(ListObjectsRequest request) {
        String function = "listObjects";

        OSS ossClient = getClient();

        try {
            return ossClient.listObjects(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public ListObjectsV2Result listObjectsV2(ListObjectsV2Request request) {
        String function = "listObjectsV2";

        OSS ossClient = getClient();

        try {
            return ossClient.listObjectsV2(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public VersionListing listVersions(ListVersionsRequest request) {
        String function = "listVersions";

        OSS ossClient = getClient();

        try {
            return ossClient.listVersions(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public PutObjectResult putObject(PutObjectRequest request) {
        String function = "putObject";

        OSS ossClient = getClient();

        try {
            return ossClient.putObject(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public CopyObjectResult copyObject(CopyObjectRequest request) {
        String function = "copyObject";

        OSS ossClient = getClient();

        try {
            return ossClient.copyObject(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public OSSObject getObject(GetObjectRequest request) {
        String function = "getObject";

        OSS ossClient = getClient();

        try {
            return ossClient.getObject(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public SimplifiedObjectMeta getSimplifiedObjectMeta(GetObjectRequest request) {
        String function = "getSimplifiedObjectMeta";

        OSS ossClient = getClient();

        try {
            return ossClient.getSimplifiedObjectMeta(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public ObjectMetadata getObjectMetadata(GetObjectRequest request) {
        String function = "getObjectMetadata";

        OSS ossClient = getClient();

        try {
            return ossClient.getObjectMetadata(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public ObjectMetadata headObject(HeadObjectRequest request) {
        String function = "headObject";

        OSS ossClient = getClient();

        try {
            return ossClient.headObject(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public AppendObjectResult appendObject(AppendObjectRequest request) {
        String function = "appendObject";

        OSS ossClient = getClient();

        try {
            return ossClient.appendObject(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public VoidResult deleteObject(GenericRequest request) {
        String function = "deleteObject";

        OSS ossClient = getClient();

        try {
            return ossClient.deleteObject(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public DeleteObjectsResult deleteObjects(DeleteObjectsRequest request) {
        String function = "deleteObjects";

        OSS ossClient = getClient();

        try {
            return ossClient.deleteObjects(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public VoidResult deleteVersion(DeleteVersionRequest request) {
        String function = "deleteVersion";

        OSS ossClient = getClient();

        try {
            return ossClient.deleteVersion(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public DeleteVersionsResult deleteVersions(DeleteVersionsRequest request) {
        String function = "deleteVersions";

        OSS ossClient = getClient();

        try {
            return ossClient.deleteVersions(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public boolean doesObjectExist(GenericRequest request) {
        String function = "doesObjectExist";

        OSS ossClient = getClient();

        try {
            return ossClient.doesObjectExist(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public RestoreObjectResult restoreObject(RestoreObjectRequest request) {
        String function = "restoreObject";

        OSS ossClient = getClient();

        try {
            return ossClient.restoreObject(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public GenericResult processObject(ProcessObjectRequest request) {
        String function = "processObject";

        OSS ossClient = getClient();

        try {
            return ossClient.processObject(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public VoidResult renameObject(RenameObjectRequest request) {
        String function = "renameObject";

        OSS ossClient = getClient();

        try {
            return ossClient.renameObject(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }

    public AsyncProcessObjectResult asyncProcessObject(AsyncProcessObjectRequest request) {
        String function = "asyncProcessObject";

        OSS ossClient = getClient();

        try {
            return ossClient.asyncProcessObject(request);
        } catch (ClientException e) {
            log.error("[Herodotus] |- Aliyun OSS catch ClientException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (OSSException e) {
            log.error("[Herodotus] |- Aliyun OSS catch OSSException in [{}].", function, e);
            throw new OssClientPoolErrorException(e.getMessage());
        } finally {
            close(ossClient);
        }
    }
}
