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

package cn.herodotus.oss.minio.logic.service;

import cn.herodotus.oss.minio.api.service.ObjectService;
import io.minio.StatObjectResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * <p>Description: Object 下载处理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/9 15:27
 */
@Service
public class ObjectDownloadService {

    private final ObjectService objectService;

    public ObjectDownloadService(ObjectService objectService) {
        this.objectService = objectService;
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
        StatObjectResponse statObject = objectService.statObject(bucketName, objectName);

        response.setContentType(statObject.contentType());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(objectName, StandardCharsets.UTF_8));

        InputStream is = objectService.getObject(bucketName, objectName);
        IOUtils.copy(is, response.getOutputStream());
        IOUtils.closeQuietly(is);
    }
}
