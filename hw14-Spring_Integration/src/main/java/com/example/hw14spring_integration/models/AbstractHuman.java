package com.example.hw14spring_integration.models;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

@Slf4j
@Getter
public abstract class AbstractHuman {

    private final boolean readyToCoding;

    private int skillLevel;

    private String name;

    public AbstractHuman(String name, boolean readyToCoding) {
        this.name = name;
        this.readyToCoding = readyToCoding;
        this.skillLevel = 0;
    }

    public AbstractHuman(AbstractHuman abstractHuman) {
        this.name = abstractHuman.getName();
        this.skillLevel = abstractHuman.getSkillLevel();
        this.readyToCoding = abstractHuman.isReadyToCoding();
    }

    public AbstractHuman(String name) {
        this.skillLevel = 0;
        this.readyToCoding = generateSkillLevel();
        this.name = name + (this.isReadyToCoding() ? " (Developer)" : "");
    }

    public int skillLevelUp() {
        log.info("{} перешел на {} уровень навыков!", name, ++skillLevel);
        return skillLevel;
    }

    private boolean generateSkillLevel() {
        return RandomUtils.nextInt(1, 10) > 6;
    }

}
