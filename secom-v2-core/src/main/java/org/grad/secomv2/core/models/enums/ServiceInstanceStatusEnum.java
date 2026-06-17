package org.grad.secomv2.core.models.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The SECOM ServiceInstanceStatus Enum.
 *
 * @author Jakob Svenningsen (email: jakob@dmc.international)
 * 0 = Provisional
 * 1 = Released
 * 2 = Deprecated
 * 3 = Deleted
 */
public enum ServiceInstanceStatusEnum {

    PROVISIONAL(0),
    RELEASED(1),
    DEPRECATED(2),
    DELETED(3);

    private final int value;

    ServiceInstanceStatusEnum(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static ServiceInstanceStatusEnum fromValue(int value) {
        switch (value) {
            case 0: return PROVISIONAL;
            case 1: return RELEASED;
            case 2: return DEPRECATED;
            case 3: return DELETED;
            default:
                throw new IllegalArgumentException(
                        "Unknown ServiceInstanceStatus value: " + value);
        }
    }
}