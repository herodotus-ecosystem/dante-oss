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

package cn.herodotus.oss.definition.core.repository;

import cn.herodotus.oss.definition.arguments.load.*;
import cn.herodotus.oss.definition.domain.base.ObjectWriteDomain;
import cn.herodotus.oss.definition.domain.load.GetObjectDomain;
import cn.herodotus.oss.definition.domain.load.ObjectMetadataDomain;
import cn.herodotus.oss.definition.domain.load.PutObjectDomain;
import cn.herodotus.oss.definition.enums.HttpMethod;

import java.net.URL;

/**
 * <p>Description: Dante Java OSS API 对象上传、下载操作抽象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/15 12:11
 */
public interface OssObjectLoadRepository {

    /**
     * 获取（下载）对象
     *
     * @param arguments 获取（下载）对象请求参数实体 {@link GetObjectArguments}
     * @return 获取（下载）对象结果域对象 {@link GetObjectDomain}
     */
    GetObjectDomain getObject(GetObjectArguments arguments);

    /**
     * 放置（上传）对象
     *
     * @param arguments 放置（上传）对象请求参数实体 {@link PutObjectArguments}
     * @return 放置（上传）对象结果域对象 {@link PutObjectDomain}
     */
    PutObjectDomain putObject(PutObjectArguments arguments);


    /**
     * 创建预签名 URL
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param expiry     中止时间
     * @return 预签名地址 {@link URL}
     */
    default URL generatePreSignedUrl(String bucketName, String objectName, Integer expiry) {
        return generatePreSignedUrl(bucketName, objectName, expiry, HttpMethod.GET);
    }


    /**
     * 创建预签名 URL
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param expiry     中止时间
     * @param method     http 请求类型
     * @return 预签名地址 {@link URL}
     */
    default URL generatePreSignedUrl(String bucketName, String objectName, Integer expiry, HttpMethod method) {
        GeneratePreSignedUrlArguments arguments = new GeneratePreSignedUrlArguments();
        arguments.setBucketName(bucketName);
        arguments.setObjectName(objectName);
        arguments.setExpiry(expiry);
        arguments.setMethod(method);
        return generatePreSignedUrl(arguments);
    }

    /**
     * 创建预签名 URL
     *
     * @param arguments 创建预签名 URL 请求参数 {@link GeneratePreSignedUrlArguments}
     * @return {@link URL}
     */
    URL generatePreSignedUrl(GeneratePreSignedUrlArguments arguments);

    /**
     * 下载对象
     * <p>
     * 该方法与<code>getObject</code>不同，该方法要指明具体的文件{@link java.io.File} 或者文件名。这就意味着可以把该方法理解为服务端下载操作。
     *
     * @param arguments 下载对象请求参数实体 {@link DownloadObjectArguments}
     * @return 下载对象结果域对象 {@link ObjectMetadataDomain}
     */
    ObjectMetadataDomain download(DownloadObjectArguments arguments);

    /**
     * 上传对象
     * <p>
     * 该方法与<code>putObject</code>不同，该方法要指明具体的文件{@link java.io.File} 或者文件名。这就意味着可以把该方法理解为服务端下载操作
     *
     * @param arguments 下载对象请求参数实体 {@link UploadObjectArguments}
     * @return 下载对象结果域对象 {@link ObjectWriteDomain}
     */
    ObjectWriteDomain upload(UploadObjectArguments arguments);
}
