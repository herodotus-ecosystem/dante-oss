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

package cn.herodotus.oss.specification.arguments.object;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>Description: 对象列表V2请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/11 14:24
 */
@Schema(name = "对象列表V2请求参数实体", title = "对象列表V2请求参数实体")
public class ListObjectsV2Arguments extends ListObjectsArguments {

    @Schema(name = "允许从特定点继续列表", description = "ContinuationToken在截断的列表结果中提供")
    private String continuationToken;

    @Schema(name = "是否包括所有者字段", description = "默认情况下，ListObjectsV2结果中不存在所有者字段。如果此标志设置为true，则将包括所有者字段。")
    private Boolean fetchOwner = false;

    @Schema(name = "版本ID标记", description = "仅在 Minio GetObjectVersions情况下使用")
    private String versionIdMarker;

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

    public String getVersionIdMarker() {
        return versionIdMarker;
    }

    public void setVersionIdMarker(String versionIdMarker) {
        this.versionIdMarker = versionIdMarker;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("continuationToken", continuationToken)
                .add("fetchOwner", fetchOwner)
                .add("versionIdMarker", versionIdMarker)
                .toString();
    }
}
