package com.javarush.jira.common.config;

import com.github.dockerjava.api.command.InspectContainerResponse;
import org.testcontainers.containers.PostgreSQLContainer;

public class PGContainer extends PostgreSQLContainer<PGContainer> {
    private static final String IMAGE_VERSION = "postgres:15";
    private static PGContainer container;

    private PGContainer() {
        super(IMAGE_VERSION);
    }

    public static PGContainer getInstance(){
        if (container == null){
            container = new PGContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    @Override
    protected void containerIsStarted(InspectContainerResponse containerInfo) {
        super.containerIsStarted(containerInfo);
    }
}