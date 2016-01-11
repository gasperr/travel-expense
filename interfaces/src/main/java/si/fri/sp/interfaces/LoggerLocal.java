package si.fri.sp.interfaces;

import si.fri.sp.entities.Log;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author Gasper Andrejc, created on 12/jan/2016
 */


public interface LoggerLocal {
    void create(Log ent);
    List<Log> findInbetween(Timestamp a, Timestamp b);
    List<Log> findByType(int var);
    List<Log> findAll(int var);
}
