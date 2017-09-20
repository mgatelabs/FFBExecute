package com.mgatelabs.ffbe.shared.details;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by @mgatelabs (Michael Fuller) on 9/4/2017.
 */
public class ScriptDefinition {

    Map<String, StateDefinition> states;

    public Map<String, StateDefinition> getStates() {
        return states;
    }

    public void setStates(Map<String, StateDefinition> states) {
        this.states = states;
    }

    public static ScriptDefinition read(String scriptName) {
        File deviceFile = new File("scripts/" + scriptName + ".json");
        if (deviceFile.exists()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(deviceFile, ScriptDefinition.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}