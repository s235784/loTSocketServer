package pro.furry.lotsocketserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.furry.lotsocketserver.util.CommandUtil;

/**
 * @author NuoTian
 * @date 2023/7/10
 */
@Slf4j
@RestController
public class TestController {

    public CommandUtil commandUtil;

    @Autowired
    public void setCommandUtil(CommandUtil commandUtil) {
        this.commandUtil = commandUtil;
    }

    @GetMapping("/navi")
    public String navigation(@RequestParam String str) {
        return commandUtil.getRoute(str);
    }
}
