package com.client.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RoomClient {
    private final StringProperty id;
    private final StringProperty level;
    private final StringProperty capacity;
    private final StringProperty type;

    public RoomClient(String id, String level, String capacity, String type) {
        this.id = new SimpleStringProperty(id);
        this.level = new SimpleStringProperty(level);
        this.capacity = new SimpleStringProperty(capacity);
        this.type = new SimpleStringProperty(type);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getLevel() {
        return level.get();
    }

    public StringProperty levelProperty() {
        return level;
    }

    public void setLevel(String level) {
        this.level.set(level);
    }

    public String getCapacity() {
        return capacity.get();
    }

    public StringProperty capacityProperty() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity.set(capacity);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }
}
