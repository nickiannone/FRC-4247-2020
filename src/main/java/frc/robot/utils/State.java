package frc.robot.utils;

import java.util.Optional;

import frc.robot.Robot;

public abstract class State<COMPONENT> {

    private final String name;

    public State(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    protected abstract void onEnter(Robot r, COMPONENT component);
    protected abstract void onUpdate(Robot r, COMPONENT component);
    protected abstract Optional<String> checkTransition(Robot r, COMPONENT component);
}