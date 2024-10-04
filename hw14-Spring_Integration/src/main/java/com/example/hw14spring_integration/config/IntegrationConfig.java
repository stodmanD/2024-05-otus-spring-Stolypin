package com.example.hw14spring_integration.config;

import com.example.hw14spring_integration.models.Developer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import com.example.hw14spring_integration.models.EasyRunner;
import com.example.hw14spring_integration.services.educationservices.OtusBasedCourse;
import com.example.hw14spring_integration.services.educationservices.OtusProfessionalCourse;
import com.example.hw14spring_integration.services.educationservices.AnyFreeCourse;

import static org.springframework.integration.dsl.MessageChannels.publishSubscribe;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> anyFreeChannel() {
        return MessageChannels.queue(100);
    }

    @Bean
    public MessageChannelSpec<?, ?> otusBaseChannel() {
        return publishSubscribe();
    }

    @Bean
    public MessageChannelSpec<?, ?> otusProChannel() {
        return MessageChannels.queue(100);
    }

    @Bean
    public MessageChannelSpec<?, ?> employeeChannel() {
        return publishSubscribe();
    }

    @Router(inputChannel = "splitBaseAndFree")
    public String resolveHumanChannel(EasyRunner easyRunner) {
        return (easyRunner.isReadyToCoding()) ? "otusBaseChannel" : "anyFreeChannel";
    }

    @Router(inputChannel = "splitProAndBase")
    public String resolveDeveloperChannel(Developer developer) {
        return (developer.isReadyToCoding()) ? "otusProChannel" : "otusBaseChannel";
    }

    @Bean
    public IntegrationFlow easyFlow(AnyFreeCourse anyFreeCourse) {
        return IntegrationFlow.from(anyFreeChannel())
                .split()
                .handle(anyFreeCourse, "teach")
                .route(this, "resolveHumanChannel")
                .get();
    }

    @Bean
    public IntegrationFlow baseFlow(OtusBasedCourse otusBasedCourse) {
        return IntegrationFlow.from(otusBaseChannel())
                .split()
                .handle(otusBasedCourse, "teach")
                .route(this, "resolveDeveloperChannel")
                .get();
    }

    @Bean
    public IntegrationFlow professionalFlow(OtusProfessionalCourse otusProfessionalCourse) {
        return IntegrationFlow.from(otusProChannel())
                .handle(otusProfessionalCourse, "teach")
                .channel(employeeChannel())
                .get();
    }
}
