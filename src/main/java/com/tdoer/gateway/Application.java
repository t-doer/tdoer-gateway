/*
 * Copyright 2017-2019 T-Doer (tdoer.com).
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
 */
package com.tdoer.gateway;

import com.tdoer.springboot.autoconfigure.EnableErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */

@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class})
@EnableEurekaClient
@EnableErrorHandler({ErrorCodes.class})
@ComponentScan(basePackages = {
        "com.tdoer.gateway", // local components in the package
        "com.tdoer.interfaces.config", // Feign configuration
        "com.tdoer.delegate.bedrock", // Bedrock service providers
        "com.tdoer.delegate.token", // ResourceServerRefreshTokenServices
        "com.tdoer.delegate.user" // UserService
})
@EnableFeignClients(basePackages= {
        "com.tdoer.interfaces.bedrock", // Bedrock services to "tdoer-bedrock-serviceprovider"
        "com.tdoer.interfaces.token", // Toker services to "tdoer-auth"
        "com.tdoer.interfaces.user" // User service to "tdoer-core-data"

})
@Configuration
@EnableZuulProxy
@EnableCircuitBreaker
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
