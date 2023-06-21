package com.cleverpine.viravamanageacesscore.annotation;

import com.cleverpine.viravamanageacesscore.config.AMResponseEntityUtilConfig;
import com.cleverpine.viravamanageacesscore.config.AMAppConfig;
import com.cleverpine.viravamanageacesscore.init.ViravaAccessManagementInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@Import({ViravaAccessManagementInitializer.class, AMResponseEntityUtilConfig.class, AMAppConfig.class})
public @interface ViravaAccessManagement {
}
