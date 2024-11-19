/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante OSS Licensed under the Apache License, Version 2.0 (the "License");
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

package cn.herodotus.oss.solution.service;

import cn.herodotus.oss.dialect.core.exception.OssIOException;
import cn.herodotus.oss.specification.arguments.object.PutObjectArguments;
import cn.herodotus.oss.specification.core.repository.OssObjectRepository;
import cn.herodotus.oss.specification.domain.object.GetObjectDomain;
import cn.herodotus.oss.specification.domain.object.ObjectMetadataDomain;
import cn.herodotus.oss.specification.domain.object.PutObjectDomain;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * <p>Description: OSS 对象流式上传、下载 Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/17 16:36
 */
@Service
public class OssObjectStreamService {

    private static final Logger log = LoggerFactory.getLogger(OssObjectStreamService.class);

    private final OssObjectRepository ossObjectRepository;

    public OssObjectStreamService(OssObjectRepository ossObjectRepository) {
        this.ossObjectRepository = ossObjectRepository;
    }

    /**
     * 流式文件下载
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储对象名称
     * @param isOnline   true 在线显示，false 直接下载
     * @param response   {@link HttpServletResponse}
     * @throws IOException 输入输出错误
     */
    private void stream(String bucketName, String objectName, boolean isOnline, HttpServletResponse response) throws IOException {
        ObjectMetadataDomain objectMetadata = ossObjectRepository.getObjectMetadata(bucketName, objectName);

        String type = isOnline ? "inline" : "attachment";

        response.setContentType(objectMetadata.getContentType());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, type + ";filename=" + URLEncoder.encode(objectName, StandardCharsets.UTF_8));

        GetObjectDomain domain = ossObjectRepository.getObject(bucketName, objectName);
        InputStream is = domain.getObjectContent();
        IOUtils.copy(is, response.getOutputStream());
        IOUtils.closeQuietly(is);
    }

    /**
     * 以流的方式返回响应内容，前端可直接下载
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储对象名称
     * @param response   {@link HttpServletResponse}
     * @throws IOException 输入输出错误
     */
    public void download(String bucketName, String objectName, HttpServletResponse response) throws IOException {
        stream(bucketName, objectName, false, response);
    }

    /**
     * 以流的方式返回响应内容，前端可直接展示
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储对象名称
     * @param response   {@link HttpServletResponse}
     * @throws IOException 输入输出错误
     */
    public void display(String bucketName, String objectName, HttpServletResponse response) throws IOException {
        stream(bucketName, objectName, true, response);
    }

    /**
     * 普通文件上传
     *
     * @param bucketName 存储桶名称
     * @param file       文件 {@link MultipartFile}
     * @return 上传结果实体 {@link PutObjectDomain}
     */
    public PutObjectDomain upload(String bucketName, MultipartFile file) {

        try {
            PutObjectArguments arguments = new PutObjectArguments();
            arguments.setContentType(file.getContentType());
            arguments.setObjectSize(file.getSize());
            arguments.setPartSize(-1L);
            arguments.setInputStream(file.getInputStream());
            arguments.setObjectName(file.getOriginalFilename());
            arguments.setBucketName(bucketName);
            return ossObjectRepository.putObject(arguments);
        } catch (IOException e) {
            log.error("[Herodotus] |- Minio upload catch IOException.", e);
            throw new OssIOException(e.getMessage());
        }
    }
}
