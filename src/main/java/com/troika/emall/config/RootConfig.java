package com.troika.emall.config;

import java.util.regex.Pattern;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import com.troika.emall.config.RootConfig.WebPackage;

@Configuration
@Import(JpaConfig.class) // bring two configuration classes together by Import
@ComponentScan(basePackages={"com.troika.emall"}, 
    excludeFilters={
        @Filter(type=FilterType.CUSTOM, value=WebPackage.class)
    })
public class RootConfig {
  public static class WebPackage extends RegexPatternTypeFilter {
    public WebPackage() {
    	// TODO huajian
      super(Pattern.compile("com\\.troika\\.emall\\.web"));
    }    
  }
}
