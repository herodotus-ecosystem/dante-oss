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
 * <p>Description: 对象存储文件上传记录 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/26 11:06
 */
@Schema(name = "文件上传信息记录")
@Entity
@Table(name = "oss_upload_record", indexes = {@Index(name = "oss_upload_record_id_idx", columnList = "record_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = MinioConstants.REGION_OSS_UPLOAD_RECORD)
public class UploadRecord extends BaseEntity {

    @Schema(name = "文件上传记录ID")
    @Id
    @UuidGenerator
    @Column(name = "record_id", length = 64)
    private String recordId;

    @Schema(name = "文件名")
    @Column(name = "file_name", length = 512)
    private String fileName;

    @Schema(name = "文件路径")
    @Column(name = "file_path", length = 1000)
    private String filePath;

    @Schema(name = "文件后缀")
    @Column(name = "suffix", length = 20)
    private String suffix;

    @Schema(name = "文件标识", description = "文件的唯一标识，通常为根据文件生成的 MD5 值")
    @Column(name = "identifier", length = 64)
    private String identifier;

    @Schema(name = "存储桶名称")
    @Column(name = "bucket_name", length = 128)
    private String bucketName;

    @Schema(name = "文件大小")
    @Column(name = "total_size")
    private BigInteger totalSize;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
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

    public BigInteger getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(BigInteger totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("recordId", recordId)
                .add("fileName", fileName)
                .add("filePath", filePath)
                .add("suffix", suffix)
                .add("identifier", identifier)
                .add("bucketName", bucketName)
                .add("totalSize", totalSize)
                .toString();
    }
}
