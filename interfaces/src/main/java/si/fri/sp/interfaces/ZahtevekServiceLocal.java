package si.fri.sp.interfaces;

import si.fri.sp.entities.ZahtevekEntity;
import si.fri.sp.interfaces.generic.ServiceEntryPoint;

import java.util.List;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
public interface ZahtevekServiceLocal extends ServiceEntryPoint<ZahtevekEntity> {
    List<ZahtevekEntity> getAllByUserId(int uid);
}
