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

package cn.herodotus.oss.definition.arguments.base;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: 基础的 Object Conditional Read 请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/15 13:52
 */
public abstract class ObjectConditionalReadArguments extends ObjectReadArguments {

    /**
     * ETag值反向匹配约束列表，该列表将复制请求约束为仅在源对象的ETag与任何指定的ETag约束值不匹配时执行。
     */
    @Schema(name = "ETag值反向匹配约束列表")
    private final List<String> nonmatchingEtagConstraints = new ArrayList<>();
    @Schema(name = "offset")
    @DecimalMin(value = "0", message = "offset 参数不能小于 0")
    private Long offset;
    @Schema(name = "length")
    @DecimalMin(value = "0", message = "length 参数不能小于 0")
    private Long length;
    /**
     * ETag值匹配约束列表，该列表约束复制请求仅在源对象的ETag与指定的ETag值之一匹配时执行。
     */
    @Schema(name = "ETag值匹配约束列表")
    private List<String> matchingETagConstraints = new ArrayList<>();
    /**
     * 可选字段，用于将复制请求限制为仅在源对象自指定日期以来已被修改时才执行。
     */
    @Schema(name = "修改时间匹配约束")
    private Date modifiedSinceConstraint;

    /**
     * 可选字段，用于将复制请求限制为仅在源对象自指定日期以来未修改时执行
     */
    @Schema(name = "修改时间反向匹配约束")
    private Date unmodifiedSinceConstraint;

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

    public List<String> getMatchingETagConstraints() {
        return matchingETagConstraints;
    }

    public void setMatchingETagConstraints(List<String> matchingETagConstraints) {
        this.matchingETagConstraints = matchingETagConstraints;
    }

    public List<String> getNonmatchingEtagConstraints() {
        return nonmatchingEtagConstraints;
    }

    public Date getModifiedSinceConstraint() {
        return modifiedSinceConstraint;
    }

    public void setModifiedSinceConstraint(Date modifiedSinceConstraint) {
        this.modifiedSinceConstraint = modifiedSinceConstraint;
    }

    public Date getUnmodifiedSinceConstraint() {
        return unmodifiedSinceConstraint;
    }

    public void setUnmodifiedSinceConstraint(Date unmodifiedSinceConstraint) {
        this.unmodifiedSinceConstraint = unmodifiedSinceConstraint;
    }
}
