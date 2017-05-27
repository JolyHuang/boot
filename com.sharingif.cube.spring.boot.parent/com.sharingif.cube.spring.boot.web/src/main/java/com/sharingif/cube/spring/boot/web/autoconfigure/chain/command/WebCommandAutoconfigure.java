package com.sharingif.cube.spring.boot.web.autoconfigure.chain.command;

import com.sharingif.cube.security.web.handler.chain.command.user.CoreUserHttpSessionManageWebCommand;
import com.sharingif.cube.web.user.CoreUserHttpSessionManage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WebCommandAutoconfigure
 * 2017/5/27 下午1:13
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class WebCommandAutoconfigure {

    @Bean("coreUserHttpSessionManageWebCommand")
    public CoreUserHttpSessionManageWebCommand createCoreUserHttpSessionManageWebCommand(CoreUserHttpSessionManage coreUserHttpSessionManage) {
        CoreUserHttpSessionManageWebCommand coreUserHttpSessionManageWebCommand = new CoreUserHttpSessionManageWebCommand();
        coreUserHttpSessionManageWebCommand.setWebUserManage(coreUserHttpSessionManage);

        return coreUserHttpSessionManageWebCommand;
    }

}
