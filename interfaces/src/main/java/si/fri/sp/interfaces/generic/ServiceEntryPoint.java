package si.fri.sp.interfaces.generic;

import si.fri.sp.entities.generic.BasicResource;

import java.util.List;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
public interface ServiceEntryPoint<T extends BasicResource> {
    void create(T ent);
    void update(T ent);
    void delete(T ent);
    T read(int entId);
    List<T> readFromTo(int start, int end, boolean archived);
}
