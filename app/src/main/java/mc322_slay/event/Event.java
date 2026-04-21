package mc322_slay.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import mc322_slay.entity.Hero;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Battle.class, name = "battle")
})
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public abstract class Event {
    public abstract boolean init(Hero hero);

    public abstract String getDescription();
}
