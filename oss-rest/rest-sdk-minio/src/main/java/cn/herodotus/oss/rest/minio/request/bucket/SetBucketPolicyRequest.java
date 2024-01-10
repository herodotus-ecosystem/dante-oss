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

package cn.herodotus.oss.rest.minio.request.bucket;

import cn.herodotus.oss.dialect.minio.domain.policy.PolicyDomain;
import cn.herodotus.oss.dialect.minio.domain.policy.StatementDomain;
import cn.herodotus.oss.dialect.minio.enums.PolicyEnums;
import cn.herodotus.oss.rest.minio.definition.BucketRequest;
import cn.herodotus.stirrup.kernel.engine.json.jackson2.utils.Jackson2Utils;
import com.google.common.collect.Lists;
import io.minio.SetBucketPolicyArgs;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * <p>Description: 设置存储桶访问策略请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/6 21:42
 */
@Schema(name = "设置存储桶访问策略请求参数实体", title = "设置存储桶访问策略请求参数实体")
public class SetBucketPolicyRequest extends BucketRequest<SetBucketPolicyArgs.Builder, SetBucketPolicyArgs> {

    private static final String DEFAULT_RESOURCE_PREFIX = "arn:aws:s3:::";
    private static final List<String> DEFAULT_ACTION_FOR_BUCKET = Lists.newArrayList("s3:GetBucketLocation", "s3:ListBucket", "s3:ListBucketMultipartUploads");
    private static final List<String> DEFAULT_ACTION_FOR_OBJECT = Lists.newArrayList("s3:DeleteObject", "s3:GetObject", "s3:ListMultipartUploadParts", "s3:PutObject", "s3:AbortMultipartUpload");

    @Schema(name = "访问策略类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private PolicyEnums type;

    @Schema(name = "访问策略配置", description = "如果为自定义类型那么必需输入配置信息")
    private PolicyDomain config;

    public PolicyEnums getType() {
        return type;
    }

    public void setType(PolicyEnums type) {
        this.type = type;
    }

    public PolicyDomain getConfig() {
        return config;
    }

    public void setConfig(PolicyDomain config) {
        this.config = config;
    }

    @Override
    public void prepare(SetBucketPolicyArgs.Builder builder) {

        PolicyDomain policyDomain;

        switch (getType()) {
            case PUBLIC -> policyDomain = getPublicPolicy();
            case CUSTOM -> policyDomain = getConfig();
            default -> policyDomain = getPrivatePolicy(getBucketName());
        }

        builder.config(Jackson2Utils.toJson(policyDomain));
        super.prepare(builder);
    }

    @Override
    public SetBucketPolicyArgs.Builder getBuilder() {
        return SetBucketPolicyArgs.builder();
    }

    private PolicyDomain getPublicPolicy() {
        return new PolicyDomain();
    }

    private PolicyDomain getPrivatePolicy(String bucketName) {
        StatementDomain bucketStatement = new StatementDomain();
        bucketStatement.setActions(DEFAULT_ACTION_FOR_BUCKET);
        bucketStatement.setResources(getDefaultResource(bucketName, true));

        StatementDomain objectStatement = new StatementDomain();
        objectStatement.setActions(DEFAULT_ACTION_FOR_OBJECT);
        objectStatement.setResources(getDefaultResource(bucketName, false));

        PolicyDomain policy = new PolicyDomain();
        policy.setStatements(Lists.newArrayList(bucketStatement, objectStatement));
        return policy;
    }

    private List<String> getDefaultResource(String bucketName, boolean isForBucket) {
        String suffix = isForBucket ? "" : "/*";
        return Lists.newArrayList(DEFAULT_RESOURCE_PREFIX + bucketName + suffix);
    }
}
