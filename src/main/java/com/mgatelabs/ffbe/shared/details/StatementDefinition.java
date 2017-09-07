package com.mgatelabs.ffbe.shared.details;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

/**
 * Created by @mgatelabs (Michael Fuller) on 9/4/2017.
 */
public class StatementDefinition {
    private ConditionDefinition condition;
    private List<ActionDefinition> actions;

    public ConditionDefinition getCondition() {
        return condition;
    }

    public void setCondition(ConditionDefinition condition) {
        this.condition = condition;
    }

    public List<ActionDefinition> getActions() {
        return actions;
    }

    public void setActions(List<ActionDefinition> actions) {
        this.actions = actions;
    }

    public Set<String> determineScreenIds() {
        return getCondition().determineScreenIds();
    }
}
