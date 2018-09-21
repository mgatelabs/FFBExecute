package com.mgatelabs.piper.shared.details;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgatelabs.piper.Runner;
import com.mgatelabs.piper.shared.util.JsonTool;

import java.io.File;
import java.io.IOException;

/**
 * Created by @mgatelabs (Michael Fuller) on 9/20/2017.
 */
public class ConnectionDefinition {

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public static ConnectionDefinition read() {
        File localFile = new File(Runner.WORKING_DIRECTORY, "connection.json");
        if (localFile.exists()) {
            ObjectMapper objectMapper = JsonTool.getInstance();
            try {
                return objectMapper.readValue(localFile, ConnectionDefinition.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ConnectionDefinition();
    }

    public boolean write() {
        File localFile = new File(Runner.WORKING_DIRECTORY, "connection.json");
        ObjectMapper objectMapper = JsonTool.getInstance();
        try {
            objectMapper.writeValue(localFile, this);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
