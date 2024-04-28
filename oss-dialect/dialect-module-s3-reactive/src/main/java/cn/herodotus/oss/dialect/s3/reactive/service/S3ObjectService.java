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

package cn.herodotus.oss.dialect.s3.reactive.service;

import cn.herodotus.oss.dialect.s3.reactive.definition.service.BaseS3AsyncService;
import cn.herodotus.stirrup.core.definition.support.AbstractObjectPool;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.file.Path;

/**
 * <p>Description: S3 对象服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2024/2/22 16:47
 */
@Service
public class S3ObjectService extends BaseS3AsyncService {

    public S3ObjectService(AbstractObjectPool<S3AsyncClient> objectPool) {
        super(objectPool);
    }

    public Mono<CopyObjectResponse> copyObject(CopyObjectRequest request) {
        return fromFuture(client -> client.copyObject(request));
    }

    public Mono<DeleteObjectResponse> deleteObject(DeleteObjectRequest deleteObjectRequest) {
        return fromFuture(client -> client.deleteObject(deleteObjectRequest));
    }

    public Mono<DeleteObjectsResponse> deleteObjects(DeleteObjectsRequest deleteObjectsRequest) {
        return fromFuture(client -> client.deleteObjects(deleteObjectsRequest));
    }

    public Mono<GetObjectResponse> getObject(GetObjectRequest getObjectRequest, Path destinationPath) {
        return fromFuture(client -> client.getObject(getObjectRequest, destinationPath));
    }

    public Mono<ListObjectsResponse> listObjects(ListObjectsRequest listObjectsRequest) {
        return fromFuture(client -> client.listObjects(listObjectsRequest));
    }

    public Mono<ListObjectsV2Response> listObjectsV2(ListObjectsV2Request listObjectsV2Request) {
        return fromFuture(client -> client.listObjectsV2(listObjectsV2Request));
    }

    public Flux<ListObjectsV2Response> listObjectsV2Paginator(ListObjectsV2Request listObjectsV2Request) {
        return from(client -> client.listObjectsV2Paginator(listObjectsV2Request));
    }

    public Mono<ListObjectVersionsResponse> listObjectVersions(ListObjectVersionsRequest listObjectVersionsRequest) {
        return fromFuture(client -> client.listObjectVersions(listObjectVersionsRequest));
    }

    public Flux<ListObjectVersionsResponse> listObjectVersionsPaginator(ListObjectVersionsRequest listObjectVersionsRequest) {
        return from(client -> client.listObjectVersionsPaginator(listObjectVersionsRequest));
    }

    public Mono<PutObjectResponse> putObject(PutObjectRequest putObjectRequest, Path sourcePath) {
        return fromFuture(client -> client.putObject(putObjectRequest, sourcePath));
    }

    public Mono<RestoreObjectResponse> restoreObject(RestoreObjectRequest restoreObjectRequest) {
        return fromFuture(client -> client.restoreObject(restoreObjectRequest));
    }

    public Mono<Void> selectObjectContent(SelectObjectContentRequest selectObjectContentRequest, SelectObjectContentResponseHandler asyncResponseHandler) {
        return fromFuture(client -> client.selectObjectContent(selectObjectContentRequest, asyncResponseHandler));
    }

    public Mono<WriteGetObjectResponseResponse> writeGetObjectResponse(WriteGetObjectResponseRequest writeGetObjectResponseRequest, Path sourcePath) {
        return fromFuture(client -> client.writeGetObjectResponse(writeGetObjectResponseRequest, sourcePath));
    }
}
