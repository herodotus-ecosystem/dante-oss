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

package cn.herodotus.oss.specification.core.repository;

import cn.herodotus.oss.specification.arguments.bucket.CreateBucketArguments;
import cn.herodotus.oss.specification.arguments.bucket.DeleteBucketArguments;
import cn.herodotus.oss.specification.domain.bucket.BucketDomain;

import java.util.List;

/**
 * <p>Description: Dante Java OSS API 存储桶操作抽象定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/24 18:29
 */
public interface OssBucketRepository {

    /**
     * 检查指定的存储桶是否存在。使用此方法可以确定指定的存储桶名称是否已经存在，因此不能用于创建新的存储桶
     *
     * @param bucketName 存储桶名称
     * @return 如果指定名称的存储桶存在，则该值为true；如果指定名称的存储桶不存在，则值为false
     */
    boolean doesBucketExist(String bucketName);

    /**
     * 返回当前帐户的所有存储桶实例列表
     *
     * @return 存储桶 {@link BucketDomain} 列表
     */
    List<BucketDomain> listBuckets();

    /**
     * 创建存储桶实例
     * <p>
     * 说明：Minio 的 createBucket 方法没有返回值
     *
     * @param bucketName 存储桶名称
     * @return 存储桶信息 {@link BucketDomain}
     */
    BucketDomain createBucket(String bucketName);

    /**
     * 创建存储桶实例
     * <p>
     * 说明：Minio 的 createBucket 方法没有返回值
     *
     * @param arguments 参数实体 {@link CreateBucketArguments}
     * @return 存储桶信息 {@link BucketDomain}
     */
    BucketDomain createBucket(CreateBucketArguments arguments);

    /**
     * 删除存储桶实例
     * <p>
     * 说明：Aliyun 的 deleteBucket 方法会返回一个 VoidResult。目前用不到，等用到时再重构
     *
     * @param bucketName 存储桶名称
     */
    void deleteBucket(String bucketName);

    /**
     * 删除存储桶实例
     * <p>
     * 说明：Aliyun 的 deleteBucket 方法会返回一个 VoidResult。目前用不到，等用到时再重构
     *
     * @param arguments 参数实体 {@link DeleteBucketArguments}
     */
    void deleteBucket(DeleteBucketArguments arguments);
}
