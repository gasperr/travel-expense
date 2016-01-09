package si.fri.sp.interfaces;

import si.fri.sp.entities.Nalog;
import si.fri.sp.interfaces.generic.ServiceEntryPoint;

import java.util.List;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
public interface NalogServiceLocal extends ServiceEntryPoint<Nalog> {
    List<Nalog> getAllByUserId(int uid, boolean archived);
    List<Nalog> getAllByApprovedUserId(int uid, boolean archived);
    Nalog findByZahtevek(int nid, boolean archived);
}
