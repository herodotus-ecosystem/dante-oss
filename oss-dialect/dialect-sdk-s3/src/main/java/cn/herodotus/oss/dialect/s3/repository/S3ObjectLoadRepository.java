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

package cn.herodotus.oss.dialect.s3.repository;

import cn.herodotus.oss.definition.arguments.load.GeneratePreSignedUrlArguments;
import cn.herodotus.oss.definition.arguments.load.GetObjectArguments;
import cn.herodotus.oss.definition.arguments.load.PutObjectArguments;
import cn.herodotus.oss.definition.core.repository.OssObjectLoadRepository;
import cn.herodotus.oss.definition.domain.load.GetObjectDomain;
import cn.herodotus.oss.definition.domain.load.PutObjectDomain;
import cn.herodotus.oss.dialect.core.client.AbstractOssClientObjectPool;
import cn.herodotus.oss.dialect.core.exception.OssServerException;
import cn.herodotus.oss.dialect.s3.converter.arguments.ArgumentsToGeneratePreSignedUrlRequestConverter;
import cn.herodotus.oss.dialect.s3.definition.service.BaseS3Service;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.net.URL;

/**
 * <p>Description: Amazon S3 Java OSS API 上传、下载操作实现 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/15 20:59
 */
public class S3ObjectLoadRepository extends BaseS3Service implements OssObjectLoadRepository {

    private static final Logger log = LoggerFactory.getLogger(S3ObjectLoadRepository.class);

    public S3ObjectLoadRepository(AbstractOssClientObjectPool<AmazonS3> ossClientObjectPool) {
        super(ossClientObjectPool);
    }

    @Override
    public GetObjectDomain getObject(GetObjectArguments arguments) {
        return null;
    }

    @Override
    public PutObjectDomain putObject(PutObjectArguments arguments) {
        return null;
    }

    @Override
    public URL generatePreSignedUrl(GeneratePreSignedUrlArguments arguments) {
        String function = "generatePreSignedUrl";

        Converter<GeneratePreSignedUrlArguments, GeneratePresignedUrlRequest> toRequest = new ArgumentsToGeneratePreSignedUrlRequestConverter();

        AmazonS3 client = getClient();

        try {
            return client.generatePresignedUrl(toRequest.convert(arguments));
        } catch (AmazonServiceException e) {
            log.error("[Herodotus] |- Amazon S3 catch AmazonServiceException in [{}].", function, e);
            throw new OssServerException(e.getMessage());
        } finally {
            close(client);
        }
    }

    @Override
    public void download() {

    }

    @Override
    public void upload() {

    }

    @Override
    public void downloadByPreSigned() {

    }

    @Override
    public void uploadByPreSigned() {

    }
}
