/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante OSS licensed under the Apache License, Version 2.0 (the "License");
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

package cn.herodotus.oss.specification.arguments.object;

import cn.herodotus.oss.specification.arguments.base.BucketArguments;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * <p>Description: 对象列表请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/9 15:54
 */
@Schema(name = "对象列表请求参数实体", title = "对象列表请求参数实体")
public class ListObjectsArguments extends BucketArguments {

    @Schema(name = "前缀")
    private String prefix;

    @Schema(name = "关键字", description = "ListObjectV2 版本中对应的名称为 startMarker, 这里为了方便统一使用 marker")
    private String marker;

    @Schema(name = "分隔符", description = "如果recursive为true，那么默认值为'', 否则默认值为'/'")
    private String delimiter = "";

    @Min(value = 1, message = "maxKeys 值不能小于 1")
    @Max(value = 1000, message = "maxKeys 值不能大于 1000")
    @Schema(name = "最大关键字数量", description = "关键字数量必须大于1，同时小于等于1000, 默认值 1000")
    private Integer maxKeys = 1000;

    @Schema(name = "encodingType")
    private String encodingType;

    @Schema(name = "是否递归", description = "该属性仅在 Minio 环境下使用")
    private Boolean recursive = false;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public Integer getMaxKeys() {
        return maxKeys;
    }

    public void setMaxKeys(Integer maxKeys) {
        this.maxKeys = maxKeys;
    }

    public String getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }

    public Boolean getRecursive() {
        return recursive;
    }

    public void setRecursive(Boolean recursive) {
        this.recursive = recursive;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("prefix", prefix)
                .add("marker", marker)
                .add("delimiter", delimiter)
                .add("maxKeys", maxKeys)
                .add("encodingType", encodingType)
                .add("recursive", recursive)
                .toString();
    }
}
