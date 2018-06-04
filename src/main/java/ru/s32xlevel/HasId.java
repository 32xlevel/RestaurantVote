package ru.s32xlevel;

public interface HasId {
    Integer getId();

    void setId(Integer id);

    default boolean isNew() {
        return getId() == null;
    }
}
