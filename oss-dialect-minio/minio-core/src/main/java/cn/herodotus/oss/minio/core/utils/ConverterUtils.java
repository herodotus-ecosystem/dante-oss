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

package cn.herodotus.oss.minio.core.utils;

import io.minio.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Description: Minio Iterable 类型返回值处理工具类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/11 17:35
 */
public class ConverterUtils {

    public static <T, R> List<R> toDomains(List<T> items, Converter<T, R> toDomain) {
        if (CollectionUtils.isNotEmpty(items)) {
            return items.stream().map(toDomain::convert).toList();
        }
        return new ArrayList<>();
    }

    public static <T, R> List<R> toDomains(Iterable<Result<T>> iterable, Converter<Result<T>, R> toDomain) {
        List<R> domains = new ArrayList<>();
        if (!IterableUtils.isEmpty(iterable)) {
            for (Result<T> result : iterable) {
                R response = toDomain.convert(result);
                domains.add(response);
            }
        }
        return domains;
    }

    public static Map<String, String> toMap(Map<String, List<String>> multimap) {
        if (MapUtils.isNotEmpty(multimap)) {
            return multimap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> StringUtils.collectionToCommaDelimitedString(entry.getValue())));
        }
        return new HashMap<>();
    }
}
