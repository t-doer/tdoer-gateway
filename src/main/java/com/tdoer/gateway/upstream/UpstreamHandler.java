/*
 * Copyright 2019 T-Doer (tdoer.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.tdoer.gateway.upstream;

import com.netflix.zuul.context.RequestContext;
import com.tdoer.bedrock.CloudEnvironment;
import com.tdoer.bedrock.Platform;
import com.tdoer.bedrock.PlatformConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2019-11-16
 */
@Component
@Slf4j
public class UpstreamHandler {
    /**
     * Check if the request to a service method is permitted
     *
     * @return
     */
    public boolean checkAccess(HttpServletRequest request){
        return Platform.getCurrentEnvironment().getContextConfig().permitServiceMethodAccess(request.getMethod(),
                request.getRequestURI());
    }

    public void forwardRequest(){
        CloudEnvironment env = Platform.getCurrentEnvironment();
        if (env != null) {
            RequestContext ctx = RequestContext.getCurrentContext();
            // Add request header for backend service
            ctx.addZuulRequestHeader(PlatformConstants.ENVIRONMENT_DIGEST, env.getDigest().toDigestString());
            ctx.addZuulRequestHeader(PlatformConstants.SERVICE_CODE, Platform.getCurrentService().getCode());
        }
    }

}
