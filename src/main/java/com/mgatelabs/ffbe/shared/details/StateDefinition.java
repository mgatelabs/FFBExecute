package com.mgatelabs.ffbe.shared.details;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sun.org.apache.xerces.internal.xs.LSInputList;

import java.util.List;
import java.util.Set;

/**
 * Created by @mgatelabs (Michael Fuller) on 9/4/2017.
 */
public class StateDefinition {
    private String name;
    @JsonIgnore
    private String id;

    private List<StatementDefinition> statements;

    private List<String> includes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<StatementDefinition> getStatements() {
        return statements;
    }

    public void setStatements(List<StatementDefinition> statements) {
        this.statements = statements;
    }

    public List<String> determineScreenIds() {
        Set<String> screenIds = Sets.newHashSet();
        for (StatementDefinition statementDefinition: statements) {
            screenIds.addAll(statementDefinition.determineScreenIds());
        }
        return ImmutableList.copyOf(screenIds);
    }

    public List<String> getIncludes() {
        return includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public void fix() {
        if (includes == null) {
            includes = Lists.newArrayList();
        }
        for (StatementDefinition statementDefinition : getStatements()) {
            statementDefinition.getCondition().fix();
        }
    }

    @Override
    public String toString() {
        return id + " - " +  name;
    }
}
