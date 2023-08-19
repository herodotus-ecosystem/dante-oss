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

package cn.herodotus.oss.dialect.minio.converter.domain;

import cn.herodotus.engine.assistant.core.utils.DateTimeUtils;
import cn.herodotus.oss.dialect.core.exception.*;
import cn.herodotus.oss.specification.domain.base.OwnerDomain;
import cn.herodotus.oss.specification.domain.object.ObjectDomain;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import io.minio.messages.Owner;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.net.ConnectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: Minio Item 转 ObjectDomain 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/9 23:43
 */
public class ResultItemToDomainConverter implements Converter<Result<Item>, ObjectDomain> {

    private static final Logger log = LoggerFactory.getLogger(ResultItemToDomainConverter.class);

    private final String bucketName;

    public ResultItemToDomainConverter(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public ObjectDomain convert(Result<Item> result) {
        String function = "convert";

        try {
            Item item = result.get();
            ObjectDomain objectDomain = new ObjectDomain();
            objectDomain.setBucketName(bucketName);
            objectDomain.setObjectName(item.objectName());
            objectDomain.setDir(item.isDir());
            if (!item.isDir()) {
                objectDomain.setETag(item.etag());
                objectDomain.setLastModified(DateTimeUtils.zonedDateTimeToDate(item.lastModified()));
                if (ObjectUtils.isNotEmpty(item.owner())) {
                    Converter<Owner, OwnerDomain> toAttr = new OwnerToDomainConverter();
                    objectDomain.setOwner(toAttr.convert(item.owner()));
                }
                objectDomain.setSize(item.size());
                objectDomain.setStorageClass(item.storageClass());

            }
            return objectDomain;
        } catch (ErrorResponseException e) {
            log.error("[Herodotus] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorResponseException(e.getMessage());
        } catch (InsufficientDataException e) {
            log.error("[Herodotus] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssInsufficientDataException(e.getMessage());
        } catch (InternalException e) {
            log.error("[Herodotus] |- Minio catch InternalException in [{}].", function, e);
            throw new OssInternalException(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("[Herodotus] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssInvalidKeyException(e.getMessage());
        } catch (InvalidResponseException e) {
            log.error("[Herodotus] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssInvalidResponseException(e.getMessage());
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio catch IOException in [{}].", function, e);
            if (e instanceof ConnectException) {
                throw new OssConnectException(e.getMessage());
            } else {
                throw new OssIOException(e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("[Herodotus] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssNoSuchAlgorithmException(e.getMessage());
        } catch (ServerException e) {
            log.error("[Herodotus] |- Minio catch ServerException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } catch (XmlParserException e) {
            log.error("[Herodotus] |- Minio catch XmlParserException in [{}].", function, e);
            throw new OssXmlParserException(e.getMessage());
        }
    }
}
