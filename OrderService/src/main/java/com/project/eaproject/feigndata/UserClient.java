package com.project.eaproject.feigndata;

import com.project.eaproject.config.MyClientConfig;
import com.project.eaproject.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "user-ms",configuration = MyClientConfig.class)
public interface UserClient {

    @GetMapping(value = "/api/v1/users/active")
    User getActiveUser();

}
