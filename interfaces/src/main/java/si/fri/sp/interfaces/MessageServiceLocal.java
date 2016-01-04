package si.fri.sp.interfaces;

import si.fri.sp.entities.MessageEntity;
import si.fri.sp.interfaces.generic.ServiceEntryPoint;

import java.util.List;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */
public interface MessageServiceLocal extends ServiceEntryPoint<MessageEntity> {
    List<MessageEntity> getRelatedToNalog(int nalogId);
    List<MessageEntity> getRelatedToZahtevek(int nalogId);
    List<MessageEntity> getUsersOutgoing(int userId);
    List<MessageEntity> getUsersIncoming(int userId);


}
