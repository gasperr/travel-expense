package si.fri.sp.interfaces;

import si.fri.sp.entities.Zahtevek;
import si.fri.sp.interfaces.generic.ServiceEntryPoint;

import java.util.List;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
public interface ZahtevekServiceLocal extends ServiceEntryPoint<Zahtevek> {
    List<Zahtevek> getAllByReviewedBy(int uid, boolean archived);
    List<Zahtevek> getAllByUserId(int uid, boolean archived);
    Zahtevek findByNalog(int nid, boolean archived);
}
