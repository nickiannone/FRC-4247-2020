package frc.robot.utils;

import java.util.List;
import java.util.Optional;

import frc.robot.Robot;

public class StateMachine<COMPONENT> {

    private List<State<COMPONENT>> states;
    private State<COMPONENT> currentState = null;
    private COMPONENT component;
    private boolean currentStateInitiated = false;

    public StateMachine(List<State<COMPONENT>> states, State<COMPONENT> initialState, COMPONENT component) {
        this.states = states;
        this.currentState = initialState;
        this.component = component;
    }

    public String getCurrentState() {
        return this.currentState.getName();
    }

    public void update(Robot robot) {

        // If we're in a new state, run the onEnter handler and clear the flag.
        if (!currentStateInitiated) {
            this.currentState.onEnter(robot, component);
            currentStateInitiated = true;
        }

        // Run the onUpdate handler.
        this.currentState.onUpdate(robot, component);
        
        // Check for a state transition.
        this.currentState.checkTransition(robot, component).ifPresent(newStateName -> {
            // Try to look up the new state
            Optional<State<COMPONENT>> newState = this.states.stream().filter(state -> state.getName().equals(newStateName)).findFirst();

            // If we have it, transition; otherwise, just re-enter the current state.
            newState.ifPresent(state -> {
                this.currentState = state;
            });

            // Tell us to enter the new current state on the next update.
            this.currentStateInitiated = false;
        });
    }
}