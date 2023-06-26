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

package cn.herodotus.oss.minio.scenario.entity;

import cn.herodotus.engine.data.core.entity.BaseEntity;
import cn.herodotus.oss.minio.core.constants.MinioConstants;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigInteger;

/**
 * <p>Description: 对象存储文件上传分片记录 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/26 11:09
 */
@Schema(name = "文件上传分片信息记录")
@Entity
@Table(name = "oss_upload_chunk_record", indexes = {@Index(name = "oss_upload_chunk_record_id_idx", columnList = "record_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = MinioConstants.REGION_OSS_UPLOAD_CHUNK_RECORD)
public class UploadChunkRecord extends BaseEntity {

    @Schema(name = "分片ID", description = "数据表主键")
    @Id
    @UuidGenerator
    @Column(name = "record_id", length = 64)
    private String recordId;

    @Schema(name = "上传ID")
    @Column(name = "upload_id", length = 64)
    private String uploadId;

    @Schema(name = "分片数量")
    @Column(name = "chunk_count")
    private Integer chunkCount;

    @Schema(name = "分片序号")
    @Column(name = "chunk_number")
    private Integer chunkNumber;

    @Schema(name = "分片大小", description = "对文件进行分片都会有一个标准大小，记录一下方便调试")
    @Column(name = "chunk_size")
    private BigInteger chunkSize;

    @Schema(name = "当前分片大小", description = "文件分片无法做到每个分片大小一致，特别是最后一个分片，增加字段进行记录")
    @Column(name = "current_chunk_size")
    private BigInteger currentChunkSize;

    @Schema(name = "文件标识", description = "文件的唯一标识，通常为根据文件生成的 MD5 值")
    @Column(name = "identifier", length = 64)
    private String identifier;

    @Schema(name = "存储桶名称")
    @Column(name = "bucket_name", length = 128)
    private String bucketName;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public Integer getChunkCount() {
        return chunkCount;
    }

    public void setChunkCount(Integer chunkCount) {
        this.chunkCount = chunkCount;
    }

    public Integer getChunkNumber() {
        return chunkNumber;
    }

    public void setChunkNumber(Integer chunkNumber) {
        this.chunkNumber = chunkNumber;
    }

    public BigInteger getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(BigInteger chunkSize) {
        this.chunkSize = chunkSize;
    }

    public BigInteger getCurrentChunkSize() {
        return currentChunkSize;
    }

    public void setCurrentChunkSize(BigInteger currentChunkSize) {
        this.currentChunkSize = currentChunkSize;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("recordId", recordId)
                .add("uploadId", uploadId)
                .add("chunkCount", chunkCount)
                .add("chunkNumber", chunkNumber)
                .add("chunkSize", chunkSize)
                .add("currentChunkSize", currentChunkSize)
                .add("identifier", identifier)
                .add("bucketName", bucketName)
                .toString();
    }
}
