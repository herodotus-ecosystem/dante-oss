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

package cn.herodotus.oss.specification.arguments.base;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;

import java.util.Date;
import java.util.List;

/**
 * <p>Description: 基础的 Object Conditional Read 请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/15 13:52
 */
public abstract class ObjectConditionalReadArguments extends ObjectReadArguments {
    @Schema(name = "offset")
    @DecimalMin(value = "0", message = "offset 参数不能小于 0")
    private Long offset;

    @Schema(name = "length")
    @DecimalMin(value = "0", message = "length 参数不能小于 0")
    private Long length;

    @Schema(name = "ETag值反向匹配约束列表")
    private List<String> notMatchEtag;

    @Schema(name = "ETag值匹配约束列表")
    private List<String> matchETag;

    @Schema(name = "修改时间匹配约束")
    private Date modifiedSince;

    @Schema(name = "修改时间反向匹配约束")
    private Date unmodifiedSince;

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public List<String> getNotMatchEtag() {
        return notMatchEtag;
    }

    public void setNotMatchEtag(List<String> notMatchEtag) {
        this.notMatchEtag = notMatchEtag;
    }

    public List<String> getMatchETag() {
        return matchETag;
    }

    public void setMatchETag(List<String> matchETag) {
        this.matchETag = matchETag;
    }

    public Date getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(Date modifiedSince) {
        this.modifiedSince = modifiedSince;
    }

    public Date getUnmodifiedSince() {
        return unmodifiedSince;
    }

    public void setUnmodifiedSince(Date unmodifiedSince) {
        this.unmodifiedSince = unmodifiedSince;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("offset", offset)
                .add("length", length)
                .add("modifiedSince", modifiedSince)
                .add("unmodifiedSince", unmodifiedSince)
                .toString();
    }
}
