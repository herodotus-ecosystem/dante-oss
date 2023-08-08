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

package cn.herodotus.oss.definition.domain.object;

import cn.herodotus.oss.definition.domain.base.OssDomain;
import cn.herodotus.oss.definition.domain.base.OwnerDomain;

import java.util.Date;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2023/7/28 22:46
 */
public class ObjectDomain implements OssDomain {

    /**
     * 存储桶名称
     */
    protected String bucketName;
    /**
     * 存储此对象的密钥
     */
    protected String key;
    /**
     * ETag。此对象内容的十六进制编码MD5哈希
     */
    protected String eTag;
    /**
     * 此对象的大小，以字节为单位
     */
    protected long size;
    /**
     * 对象最后一次被修改的日期
     */
    protected Date lastModified;
    /**
     * 存储此对象的存储类
     */
    protected String storageClass;
    /**
     * 如果请求者没有查看对象所有权信息的权限，则此对象的所有者可以为null
     */
    protected OwnerDomain owner;

}
