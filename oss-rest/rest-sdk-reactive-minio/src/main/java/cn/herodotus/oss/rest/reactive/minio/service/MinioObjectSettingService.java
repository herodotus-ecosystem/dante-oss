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

package cn.herodotus.oss.rest.reactive.minio.service;

import cn.herodotus.oss.core.minio.bo.ObjectSettingBusiness;
import cn.herodotus.oss.core.minio.converter.ResponseToStatObjectDomainConverter;
import cn.herodotus.oss.core.minio.domain.StatObjectDomain;
import cn.herodotus.oss.dialect.reactive.minio.service.MinioObjectService;
import cn.herodotus.oss.dialect.reactive.minio.service.MinioObjectTagsService;
import io.minio.StatObjectResponse;
import io.minio.messages.Tags;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>Description: Object 管理页面数据获取 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/11 9:55
 */
@Service
public class MinioObjectSettingService {

    private final Converter<StatObjectResponse, StatObjectDomain> toStatObjectDomain;

    private final MinioObjectService minioObjectService;
    private final MinioObjectTagsService minioObjectTagsService;

    public MinioObjectSettingService(MinioObjectService minioObjectService, MinioObjectTagsService minioObjectTagsService) {
        this.minioObjectService = minioObjectService;
        this.minioObjectTagsService = minioObjectTagsService;
        this.toStatObjectDomain = new ResponseToStatObjectDomainConverter();
    }

    public Mono<ObjectSettingBusiness> get(String bucketName, String region, String objectName) {
        Mono<StatObjectResponse> statObjectResponse = minioObjectService.statObject(bucketName, region, objectName);
        Mono<Tags> tags = minioObjectTagsService.getObjectTags(bucketName, region, objectName);

        return statObjectResponse.mapNotNull(toStatObjectDomain::convert).zipWith(tags)
                .map(item -> {
                    ObjectSettingBusiness business = new ObjectSettingBusiness();
                    business.setTags(item.getT2().get());
                    business.setRetentionMode(item.getT1().getRetentionMode());
                    business.setRetentionRetainUntilDate(item.getT1().getRetentionRetainUntilDate());
                    business.setLegalHold(item.getT1().getLegalHold());
                    business.setDeleteMarker(item.getT1().getDeleteMarker());
                    business.setEtag(item.getT1().getEtag());
                    business.setLastModified(item.getT1().getLastModified());
                    business.setSize(item.getT1().getSize());
                    business.setUserMetadata(item.getT1().getUserMetadata());
                    return business;
                });
    }
}
