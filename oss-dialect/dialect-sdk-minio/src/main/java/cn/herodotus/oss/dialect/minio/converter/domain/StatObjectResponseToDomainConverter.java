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

package cn.herodotus.oss.dialect.minio.converter.domain;

import cn.herodotus.stirrup.core.foundation.utils.type.DateTimeUtils;
import cn.herodotus.oss.core.domain.object.ObjectMetadataDomain;
import io.minio.StatObjectResponse;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>Description: Minio StatObjectResponse 转 ObjectMetadataDomain 转换器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/8/17 15:59
 */
public class StatObjectResponseToDomainConverter implements Converter<StatObjectResponse, ObjectMetadataDomain> {
    @Override
    public ObjectMetadataDomain convert(StatObjectResponse source) {

        ObjectMetadataDomain domain = new ObjectMetadataDomain();
        domain.setUserMetadata(source.userMetadata());
        domain.setContentLength(source.size());
        domain.setContentType(source.contentType());
        domain.setLastModified(DateTimeUtils.zonedDateTimeToDate(source.lastModified()));
        domain.setEtag(source.etag());
        domain.setVersionId(source.versionId());
        domain.setBucketName(source.bucket());
        domain.setRegion(source.region());
        domain.setObjectName(source.object());

        return domain;
    }
}
