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

package cn.herodotus.oss.specification.arguments.multipart;

import cn.herodotus.oss.specification.arguments.base.BucketArguments;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>Description: 分片上传列表请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/13 20:33
 */
@Schema(name = "分片上传列表请求参数实体", title = "分片上传列表请求参数实体")
public class ListMultipartUploadsArguments extends BucketArguments {

    @Schema(name = "分隔符")
    private String delimiter;

    @Schema(name = "前缀")
    private String prefix;

    @Schema(name = "要返回的最大上传次数")
    private Integer maxUploads;

    /**
     * 指示结果中开始列出的位置的关键标记.
     * <p>
     * 与上传ID标记一起，指定列表开始后的多部分上传.
     * <p>
     * 如果未指定 uploadId 标记，则列表中只会包括按字典顺序大于指定对象标记的对象.
     * <p>
     * 如果指定了 uploadId 标记，则对于等于对象标记的密钥的任何多部分上传也可以包括在内，前提是这些分片上传的uploadIdD在字典上大于指定的标记.
     */
    @Schema(name = "指示结果中开始列出的位置的关键标记")
    private String keyMarker;

    /**
     * 指示结果中开始列出的位置的上载ID标记
     */
    @Schema(name = "指示结果中开始列出的位置的上载ID标记")
    private String uploadIdMarker;

    /**
     * 可选参数，指示要应用于响应的编码方法。
     * 对象键可以包含任何Unicode字符；但是，XML1.0解析器无法解析某些字符，例如ASCII值为0到10的字符。
     * 对于XML1.0中不支持的字符，可以添加此参数来请求AmazonS3对响应中的密钥进行编码.
     */
    @Schema(name = "应用于响应的编码方法")
    private String encodingType;

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getMaxUploads() {
        return maxUploads;
    }

    public void setMaxUploads(Integer maxUploads) {
        this.maxUploads = maxUploads;
    }

    public String getKeyMarker() {
        return keyMarker;
    }

    public void setKeyMarker(String keyMarker) {
        this.keyMarker = keyMarker;
    }

    public String getUploadIdMarker() {
        return uploadIdMarker;
    }

    public void setUploadIdMarker(String uploadIdMarker) {
        this.uploadIdMarker = uploadIdMarker;
    }

    public String getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }
}
