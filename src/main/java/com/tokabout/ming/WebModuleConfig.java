package com.tokabout.ming;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.tokabout.ming.web.Interceptor.SecurityInterceptor;
import com.tokabout.ming.web.support.AuthenticationSessionFactory;
import com.tokabout.ming.web.support.ErrorResponseBody;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 8/25/13
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages= "com.tokabout.ming.web")
public class WebModuleConfig extends WebMvcConfigurerAdapter {

    private static final String THYMELEAF_CHARACTER_ENCODING = "utf-8";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/logout", "/login");
    }

    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }

    @Bean
    public AuthenticationSessionFactory authenticationSessionFactory() {
        return new AuthenticationSessionFactory();
    }

    @Bean
    public ServletContextTemplateResolver servletContextTemplateResolver() {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding(THYMELEAF_CHARACTER_ENCODING);
        templateResolver.setCacheable(false);

        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(servletContextTemplateResolver());

        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(springTemplateEngine());
        viewResolver.setCharacterEncoding(THYMELEAF_CHARACTER_ENCODING);
        viewResolver.setOrder(1);

        return viewResolver;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        Map<String, MediaType> mediaTypes = ImmutableMap.of("json", MediaType.APPLICATION_JSON
                , "xml", MediaType.APPLICATION_XML);

        configurer.mediaTypes(mediaTypes);
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Bean
    public MappingJackson2HttpMessageConverter JSONHttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setObjectMapper(jsonMapper());

        return jsonMessageConverter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter XMLHttpMessageConverter() {
        ArrayList<MediaType> mediaTypes = Lists.newArrayList(MediaType.APPLICATION_XML
                , MediaType.TEXT_XML
                , new MediaType("application", "*+xml"));

        MappingJackson2HttpMessageConverter xmlMessageConverter = new MappingJackson2HttpMessageConverter() {
            @Override
            protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                if(ErrorResponseBody.class.isAssignableFrom(object.getClass())) {
                    super.writeInternal(((ErrorResponseBody) object).toXmlBody(), outputMessage);
                } else {
                    super.writeInternal(object, outputMessage);
                }
            }
        };
        xmlMessageConverter.setSupportedMediaTypes(mediaTypes);
        xmlMessageConverter.setObjectMapper(xmlMapper());

        return xmlMessageConverter;
    }

    @Bean
    public ObjectMapper jsonMapper() {
        ObjectMapper jsonMapper = new ObjectMapper();

        return jsonMapper;
    }

    @Bean
    public XmlMapper xmlMapper() {
        JacksonXmlModule module = new JacksonXmlModule();
        XmlMapper xmlMapper = new XmlMapper(module);

        return xmlMapper;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setCacheManagerName("loginCacheManager");
        return ehCacheManagerFactoryBean;
    }
}
