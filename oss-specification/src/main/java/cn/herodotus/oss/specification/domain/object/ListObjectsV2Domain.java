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

package cn.herodotus.oss.specification.domain.object;

import cn.herodotus.oss.specification.arguments.object.ListObjectsV2Arguments;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * <p>Description: V2对象列表结果 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/11 15:03
 */
public class ListObjectsV2Domain extends ListObjectsV2Arguments {

    @Schema(name = "对象列表")
    private List<ObjectDomain> summaries;

    /**
     * 指示这是否是一个完整的列表，或者调用者是否需要向AmazonS3发出额外请求以查看S3 bucket的完整对象列表
     */
    @Schema(name = "否是一个完整的列表")
    private boolean isTruncated;

    /**
     * KeyCount是此请求返回的密钥数。KeyCount将始终小于或等于MaxKeys字段
     */
    @Schema(name = "Key 数量")
    private int keyCount;

    /**
     * 当 isTruncated为 true 时，发送 NextContinuationToken，这意味着存储桶中可以列出更多对象。请求亚马逊
     * 可以通过提供此NextContinuationToken来继续下一个列表
     */
    @Schema(name = "下一个列表标记")
    private String nextContinuationToken;

    public boolean isTruncated() {
        return isTruncated;
    }

    public void setTruncated(boolean truncated) {
        isTruncated = truncated;
    }

    public int getKeyCount() {
        return keyCount;
    }

    public void setKeyCount(int keyCount) {
        this.keyCount = keyCount;
    }

    public String getNextContinuationToken() {
        return nextContinuationToken;
    }

    public void setNextContinuationToken(String nextContinuationToken) {
        this.nextContinuationToken = nextContinuationToken;
    }

    public List<ObjectDomain> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<ObjectDomain> summaries) {
        this.summaries = summaries;
    }
}
