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

package cn.herodotus.oss.rest.scenario.service;

import cn.herodotus.oss.dialect.core.exception.OssIOException;
import cn.herodotus.oss.dialect.minio.converter.ResponseToObjectWriteDomainConverter;
import cn.herodotus.oss.definition.domain.base.ObjectWriteDomain;
import cn.herodotus.oss.dialect.minio.service.MinioObjectService;
import io.minio.ObjectWriteResponse;
import io.minio.StatObjectResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * <p>Description: Object 流式处理服务 </p>
 * <p>
 * 对与以流方式处理上传和下载的操作，统一归并至该服务。
 *
 * @author : gengwei.zheng
 * @date : 2023/6/9 15:27
 */
@Service
public class MinioObjectStreamService {

    private static final Logger log = LoggerFactory.getLogger(MinioObjectStreamService.class);

    private final MinioObjectService minioObjectService;
    private final Converter<ObjectWriteResponse, ObjectWriteDomain> toObjectWriteDomain;

    public MinioObjectStreamService(MinioObjectService minioObjectService) {
        this.minioObjectService = minioObjectService;
        this.toObjectWriteDomain = new ResponseToObjectWriteDomainConverter();
    }

    /**
     * 文件下载
     * <p>
     * 该方法与 downloadObject 的不同主要在于，downloadObject 是服务端下载，指定文件名和路径即可。
     * 现有方法是利用 getObject 输出流，与前端进行交互
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储对象名称
     * @param response   {@link HttpServletResponse}
     * @throws IOException 输入输出错误。
     */
    public void download(String bucketName, String objectName, HttpServletResponse response) throws IOException {
        StatObjectResponse statObject = minioObjectService.statObject(bucketName, objectName);

        response.setContentType(statObject.contentType());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(objectName, StandardCharsets.UTF_8));

        InputStream is = minioObjectService.getObject(bucketName, objectName);
        IOUtils.copy(is, response.getOutputStream());
        IOUtils.closeQuietly(is);
    }

    /**
     * 普通文件上传
     *
     * @param bucketName 存储桶名称
     * @param file       文件 {@link MultipartFile}
     * @return 上传结果实体 {@link ObjectWriteDomain}
     */
    public ObjectWriteDomain upload(String bucketName, MultipartFile file) {
        try {
            ObjectWriteResponse response = minioObjectService.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), file.getSize(), file.getContentType());
            return toObjectWriteDomain.convert(response);
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio upload catch IOException.", e);
            throw new OssIOException(e.getMessage());
        }
    }
}
