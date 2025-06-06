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

package cn.herodotus.oss.dialect.minio.enums;

import cn.herodotus.engine.assistant.definition.enums.BaseUiEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 保留模式 </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/6/5 21:45
 */
@Schema(name = "保留模式枚举")
public enum RetentionModeEnums implements BaseUiEnum<Integer> {

    /**
     * 治理模式。用户不能覆盖或删除对象版本或更改其锁定设置。
     * 要覆盖或删除治理模式保留设置，用户必须拥有 `s3:BypassGovernanceRetention` 权限，并且必须明确包括 `x-amz-bypass-governance-retention:true` 作为任何要求覆盖治理模式的请求的请求头。
     */
    GOVERNANCE(0, "治理模式"),
    /**
     * 合规模式。任何用户都不能覆盖或删除受保护对象版本
     */
    COMPLIANCE(1, "合规模式");

    private static final Map<Integer, RetentionModeEnums> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (RetentionModeEnums retentionModeEnums : RetentionModeEnums.values()) {
            INDEX_MAP.put(retentionModeEnums.getValue(), retentionModeEnums);
            JSON_STRUCTURE.add(retentionModeEnums.getValue(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", retentionModeEnums.getValue())
                            .put("key", retentionModeEnums.name())
                            .put("text", retentionModeEnums.getDescription())
                            .put("index", retentionModeEnums.getValue())
                            .build());
        }
    }

    @Schema(name = "枚举值")
    private final Integer value;
    @Schema(name = "文字描述")
    private final String description;

    RetentionModeEnums(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static RetentionModeEnums get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     * <p>
     * 不使用@JsonValue @JsonDeserializer类里面要做相应的处理
     *
     * @return Enum枚举值
     */
    @JsonValue
    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
