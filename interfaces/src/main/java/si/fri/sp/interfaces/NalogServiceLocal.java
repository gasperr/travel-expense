package si.fri.sp.interfaces;

import si.fri.sp.entities.NalogEntity;
import si.fri.sp.interfaces.generic.ServiceEntryPoint;

import java.util.List;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
public interface NalogServiceLocal extends ServiceEntryPoint<NalogEntity> {
    List<NalogEntity> getAllByUserId(int uid);
}
