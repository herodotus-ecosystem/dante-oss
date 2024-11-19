/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante OSS Licensed under the Apache License, Version 2.0 (the "License");
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

package cn.herodotus.oss.solution.business;

import cn.herodotus.oss.solution.definition.OssBusiness;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 创建分片上传返回结果业务对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/19 11:57
 */
@Schema(name = "创建分片上传返回结果业务对象", title = "创建分片上传返回结果业务对象")
public class CreateMultipartUploadBusiness implements OssBusiness {

    @Schema(name = "上传ID")
    private String uploadId;

    @Schema(name = "分片上传URL", description = "分片上传所有分片对相应的预签名地址")
    private List<String> uploadUrls;

    public CreateMultipartUploadBusiness(String uploadId) {
        this.uploadId = uploadId;
        this.uploadUrls = new ArrayList<>();
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public List<String> getUploadUrls() {
        return uploadUrls;
    }

    public void setUploadUrls(List<String> uploadUrls) {
        this.uploadUrls = uploadUrls;
    }

    public void append(String uploadUrl) {
        uploadUrls.add(uploadUrls.size(), uploadUrl);
    }
}
