package org.example.login.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.login.config.UserProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserStore {
    private final UserProperties props;
    private final ObjectMapper objectMapper;
    private final Map<String, String> userMap = new ConcurrentHashMap<>();

    public UserStore(UserProperties props, ObjectMapper objectMapper) {
        this.props = props;
        this.objectMapper = objectMapper;
        loadFromFile();
    }

    private synchronized void loadFromFile() {
        Path path = Path.of(props.getDataFile());
        if (!Files.exists(path)) return;
        try {
            Map<String, String> loaded = objectMapper.readValue(path.toFile(), new TypeReference<>() {});
            userMap.putAll(loaded);
        } catch (IOException e) {
            throw new IllegalStateException("加载用户数据失败", e);
        }
    }

    public boolean exists(String username) {
        return userMap.containsKey(username);
    }

    public boolean register(String username, String encodedPassword) {
        return userMap.putIfAbsent(username, encodedPassword) == null;
    }

    public String getPassword(String username) {
        return userMap.get(username);
    }

    public synchronized void persist() {
        try {
            Path path = Path.of(props.getDataFile());
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            objectMapper.writeValue(path.toFile(), userMap);
        } catch (IOException e) {
            throw new IllegalStateException("持久化用户数据失败", e);
        }
    }
}