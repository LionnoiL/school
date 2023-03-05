package ua.gaponov.school.web;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.cache.configuration.MutableConfiguration;

@Configuration(proxyBeanMethods = false)
@EnableCaching
class CacheConfiguration {

  @Bean
  public JCacheManagerCustomizer schoolCacheConfigurationCustomizer() {
    return cm -> cm.createCache("school", cacheConfiguration());
  }

  private javax.cache.configuration.Configuration<Object, Object> cacheConfiguration() {
    return new MutableConfiguration<>().setStatisticsEnabled(true);
  }

}