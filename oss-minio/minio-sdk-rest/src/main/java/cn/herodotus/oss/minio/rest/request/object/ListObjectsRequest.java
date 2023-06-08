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

package cn.herodotus.oss.minio.rest.request.object;

import cn.herodotus.oss.minio.rest.definition.BucketRequest;
import io.minio.ListObjectsArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.apache.commons.lang3.BooleanUtils;

/**
 * <p>Description: 对象列表请求参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/4/16 21:51
 */
@Schema(name = "对象列表请求参数实体", title = "对象列表请求参数实体")
public class ListObjectsRequest extends BucketRequest<ListObjectsArgs.Builder, ListObjectsArgs> {

    @Schema(name = "分隔符", description = "如果recursive为true，那么默认值为'', 否则默认值为'/'")
    private String delimiter = "";
    @Schema(name = "使用UrlEncoding", description = "默认开启，默认值为 true")
    private Boolean useUrlEncodingType = true;
    @Schema(name = "关键字")
    private String keyMarker;
    @Min(value = 1, message = "maxKeys 值不能小于 1")
    @Max(value = 1000, message = "maxKeys 值不能大于 1000")
    @Schema(name = "最大关键字数量", description = "关键字数量必须大于1，同时小于等于1000, 默认值 1000")
    private Integer maxKeys = 1000;
    @Schema(name = "前缀")
    private String prefix;
    @Schema(name = "是否递归", description = "当前默认设置为 false")
    private Boolean recursive = false;
    @Schema(name = "是否使用V1 版本API", description = "当前默认设置为 true")
    private Boolean useApiVersion1 = true;
    @Schema(name = "是否包含版本信息", description = "当前默认设置为 false")
    private Boolean includeVersions = false;
    @Schema(name = "持续集成Token", description = "仅当使用 V2 版本 API 时需要，即 useApiVersion1 == false")
    private String continuationToken;
    @Schema(name = "获取Owner信息", description = "仅当使用 V2 版本 API 时需要，即 useApiVersion1 == false")
    private Boolean fetchOwner = false;
    @Schema(name = "包含用户扩展自定义信息", description = "仅当使用 V2 版本 API 时需要，即 useApiVersion1 == false")
    private Boolean includeUserMetadata = false;
    @Schema(name = "版本ID标记", description = "仅在GetObjectVersions情况下使用")
    private String versionIdMarker;

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public Boolean getUseUrlEncodingType() {
        return useUrlEncodingType;
    }

    public void setUseUrlEncodingType(Boolean useUrlEncodingType) {
        this.useUrlEncodingType = useUrlEncodingType;
    }

    public String getKeyMarker() {
        return keyMarker;
    }

    public void setKeyMarker(String keyMarker) {
        this.keyMarker = keyMarker;
    }

    public Integer getMaxKeys() {
        return maxKeys;
    }

    public void setMaxKeys(Integer maxKeys) {
        this.maxKeys = maxKeys;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Boolean getRecursive() {
        return recursive;
    }

    public void setRecursive(Boolean recursive) {
        this.recursive = recursive;
    }

    public Boolean getUseApiVersion1() {
        return useApiVersion1;
    }

    public void setUseApiVersion1(Boolean useApiVersion1) {
        this.useApiVersion1 = useApiVersion1;
    }

    public Boolean getIncludeVersions() {
        return includeVersions;
    }

    public void setIncludeVersions(Boolean includeVersions) {
        this.includeVersions = includeVersions;
    }

    public String getContinuationToken() {
        return continuationToken;
    }

    public void setContinuationToken(String continuationToken) {
        this.continuationToken = continuationToken;
    }

    public Boolean getFetchOwner() {
        return fetchOwner;
    }

    public void setFetchOwner(Boolean fetchOwner) {
        this.fetchOwner = fetchOwner;
    }

    public Boolean getIncludeUserMetadata() {
        return includeUserMetadata;
    }

    public void setIncludeUserMetadata(Boolean includeUserMetadata) {
        this.includeUserMetadata = includeUserMetadata;
    }

    public String getVersionIdMarker() {
        return versionIdMarker;
    }

    public void setVersionIdMarker(String versionIdMarker) {
        this.versionIdMarker = versionIdMarker;
    }

    @Override
    public void prepare(ListObjectsArgs.Builder builder) {

        builder.delimiter(getDelimiter());
        builder.useUrlEncodingType(getUseUrlEncodingType());
        builder.maxKeys(getMaxKeys());
        builder.prefix(getPrefix());
        builder.recursive(getRecursive());
        builder.keyMarker(getKeyMarker());
        builder.includeVersions(getIncludeVersions());

        if (BooleanUtils.isTrue(getUseApiVersion1())) {
            builder.continuationToken(null);
            builder.fetchOwner(false);
            builder.includeUserMetadata(false);
        } else {
            builder.keyMarker(getKeyMarker());
            builder.continuationToken(getContinuationToken());
            builder.fetchOwner(getFetchOwner());
            builder.includeUserMetadata(getIncludeUserMetadata());
        }

        builder.versionIdMarker(getVersionIdMarker());

        super.prepare(builder);
    }

    @Override
    public ListObjectsArgs.Builder getBuilder() {
        return ListObjectsArgs.builder();
    }
}
