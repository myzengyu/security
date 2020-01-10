package cloud.spring_security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2 * @Author:
 * 3 * @Date: 2020/1/6 14:37
 * 4
 */
@RestController
public class Test {

    @PreAuthorize("hasAnyAuthority('petReceiver:update')")
    @RequestMapping("p1")
    public String p1() {
        return "调用p1方法";
    }

    @PreAuthorize("hasAnyAuthority('orderReport:view')")
    @RequestMapping("p2")
    public String p2() {
        return "调用p2方法";
    }
}
