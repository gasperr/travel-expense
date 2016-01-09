package si.fri.sp.interfaces;

import si.fri.sp.entities.Message;
import si.fri.sp.interfaces.generic.ServiceEntryPoint;

import java.util.List;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
public interface MessageServiceLocal extends ServiceEntryPoint<Message> {
    List<Message> getRelatedToNalog(int nalogId, boolean archived);
    List<Message> getRelatedToZahtevek(int nalogId, boolean archived);
    List<Message> getUsersOutgoing(int userId, boolean archived);
    List<Message> getUsersIncoming(int userId, boolean archived);


}
