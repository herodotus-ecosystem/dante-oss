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

package cn.herodotus.oss.minio.scenario.proxy;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

/**
 * <p>Description: Minio 请求代理 </p>
 * <p>
 * 解决 PresignedObjectUrl 脱离权限体系不受控，无法与微服务架构整合问题。
 *
 * @author : gengwei.zheng
 * @date : 2023/6/3 9:48
 */
@Component
public class MinioPresignedObjectUrlProxy {

    private final MinioProxyAddressConverter converter;
    private final RestTemplate restTemplate;

    public MinioPresignedObjectUrlProxy(MinioProxyAddressConverter converter) {
        this.converter = converter;
        this.restTemplate = createRestTemplate();
    }

    private RestTemplate createRestTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    public ResponseEntity<String> delegate(HttpServletRequest request) {
        try {
            String target = converter.toPresignedObjectUrl(request);
            RequestEntity<byte[]> requestEntity = createRequestEntity(request, target);
            return restTemplate.exchange(requestEntity, String.class);
        } catch (Exception e) {
            return new ResponseEntity<>("Delegate ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 创建请求
     *
     * @param request 请求 {@link HttpServletRequest}
     * @param url     请求地址
     * @return 具体请求
     * @throws URISyntaxException uri语法错误
     * @throws IOException        io错误
     */
    private RequestEntity<byte[]> createRequestEntity(HttpServletRequest request, String url) throws URISyntaxException, IOException {
        String method = request.getMethod();
        HttpMethod httpMethod = method != null ? HttpMethod.valueOf(method) : null;
        MultiValueMap<String, String> headers = readRequestHeader(request);
        byte[] body = readRequestBody(request);
        return new RequestEntity<>(body, headers, httpMethod, new URI(url));
    }

    /**
     * 解析请求体
     *
     * @param request 请求 {@link HttpServletRequest}
     * @return request body
     * @throws IOException io错误
     */
    private byte[] readRequestBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }

    /**
     * 解析请求 Headers
     *
     * @param request 请求 {@link HttpServletRequest}
     * @return 请求头
     */
    private MultiValueMap<String, String> readRequestHeader(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        List<String> headerNames = Collections.list(request.getHeaderNames());
        for (String headerName : headerNames) {
            List<String> headerValues = Collections.list(request.getHeaders(headerName));
            for (String headerValue : headerValues) {
                headers.add(headerName, headerValue);
            }
        }

        // TODO: 如果传递 OAuth2 Token 会导致转发上传失败。猜测是因为 Minio Server 也是采用 OAuth2 认证，体系不一致导致。
        // 目前先临时将外部传入的 Token 取消，等摸清楚 Minio 认证体系集成方式后再行完善。
        headers.remove(HttpHeaders.AUTHORIZATION);

        return headers;
    }
}
