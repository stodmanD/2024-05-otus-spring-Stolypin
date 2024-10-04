package com.example.hw14spring_integration.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import com.example.hw14spring_integration.models.Beginner;
import com.example.hw14spring_integration.models.EasyRunner;

import java.util.Collection;

@MessagingGateway
public interface HumanEducationGateway {
    @Gateway(requestChannel = "anyFreeChannel", replyChannel = "splitProAndBase")
    Collection<EasyRunner> humanEducationProcess(Collection<Beginner> beginners);

}
