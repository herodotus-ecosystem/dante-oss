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

package cn.herodotus.oss.core.domain.object;

import cn.herodotus.oss.core.arguments.object.ListObjectsArguments;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * <p>Description: 对象结果 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/9 16:36
 */
@Schema(name = "对象结果")
public class ListObjectsDomain extends ListObjectsArguments {

    @Schema(name = "对象列表")
    private List<ObjectDomain> summaries;

    /**
     * 用于请求下一页结果的标记-仅当isTruncated成员指示此对象列表被截断时才会填充
     */
    @Schema(name = "请求下一页结果的标记", description = "仅当isTruncated成员指示此对象列表被截断时才会填充")
    private String nextMarker;

    /**
     * 指示这是否是一个完整的列表，或者调用者是否需要向AmazonS3发出额外请求以查看S3 bucket的完整对象列表
     */
    @Schema(name = "否是一个完整的列表")
    private Boolean isTruncated;

    public List<ObjectDomain> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<ObjectDomain> summaries) {
        this.summaries = summaries;
    }

    public String getNextMarker() {
        return nextMarker;
    }

    public void setNextMarker(String nextMarker) {
        this.nextMarker = nextMarker;
    }

    public Boolean getTruncated() {
        return isTruncated;
    }

    public void setTruncated(Boolean truncated) {
        isTruncated = truncated;
    }
}
