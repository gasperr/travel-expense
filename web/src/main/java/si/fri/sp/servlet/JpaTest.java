package si.fri.sp.servlet;

import si.fri.sp.entities.MessageEntity;
import si.fri.sp.entities.NalogEntity;
import si.fri.sp.entities.UserEntity;
import si.fri.sp.entities.ZahtevekEntity;
import si.fri.sp.entities.enums.NalogStatus;
import si.fri.sp.entities.enums.UserType;
import si.fri.sp.entities.enums.ZahtevekStatus;
import si.fri.sp.interfaces.MessageServiceLocal;
import si.fri.sp.interfaces.NalogServiceLocal;
import si.fri.sp.interfaces.UserServiceLocal;
import si.fri.sp.interfaces.ZahtevekServiceLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @Author Gasper Andrejc, created on 04/jan/2016
 */

@WebServlet("/jpatest")
public class JpaTest extends HttpServlet{

    @EJB
    private UserServiceLocal userServiceLocal;

    @EJB
    private ZahtevekServiceLocal zahtevekServiceLocal;

    @EJB
    private NalogServiceLocal nalogServiceLocal;

    @EJB
    private MessageServiceLocal messageServiceLocal;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("gasper");
        userEntity.setSurname("andrejc");
        userEntity.setType(UserType.FINANCE);

        ZahtevekEntity zahtevekEntity = new ZahtevekEntity();
        zahtevekEntity.setContent("content1");
        zahtevekEntity.setCosts(200.0);
        zahtevekEntity.setFromDate(new Date());
        zahtevekEntity.setToDate(new Date());
        zahtevekEntity.setLocation("location");
        zahtevekEntity.setReviewedBy(userEntity);
        zahtevekEntity.setStatus(ZahtevekStatus.APPROVED);

//        userServiceLocal.create(userEntity);
//        zahtevekServiceLocal.create(zahtevekEntity);

        PrintWriter pw = resp.getWriter();
        for (ZahtevekEntity ze : zahtevekServiceLocal.getAllByUserId(3)){
            pw.println(ze.getId());
        }

        NalogEntity nalogEntity = new NalogEntity();
        nalogEntity.setApprovedBy(userServiceLocal.read(1));
        nalogEntity.setContent("nalog 1 content");
        nalogEntity.setFromDate(new Date());
        nalogEntity.setToDate(new Date());
        nalogEntity.setLocation("location");
        nalogEntity.setNotes("notes");
        nalogEntity.setPurpose("purpose");
        nalogEntity.setStatus(NalogStatus.ACTIVE);
        //nalogServiceLocal.create(nalogEntity);

        NalogEntity nalogEntity1 = new NalogEntity();
        nalogEntity1.setApprovedBy(userServiceLocal.read(1));
        nalogEntity1.setContent("nalog 2 content");
        nalogEntity1.setFromDate(new Date());
        nalogEntity1.setToDate(new Date());
        nalogEntity1.setLocation("location 2");
        nalogEntity1.setNotes("notes 2");
        nalogEntity1.setPurpose("purpose 2");
        nalogEntity1.setStatus(NalogStatus.ACTIVE);
        //nalogServiceLocal.create(nalogEntity1);

        NalogEntity nalogEntity2 = new NalogEntity();
        nalogEntity2.setApprovedBy(userServiceLocal.read(1));
        nalogEntity2.setContent("nalog 2 content");
        nalogEntity2.setFromDate(new Date());
        nalogEntity2.setToDate(new Date());
        nalogEntity2.setLocation("location 2");
        nalogEntity2.setNotes("notes 2");
        nalogEntity2.setPurpose("purpose 2");
        nalogEntity2.setStatus(NalogStatus.ACTIVE);
        //nalogServiceLocal.create(nalogEntity2);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setContent("message one");
        messageEntity.setFromUser(userServiceLocal.read(3));
        messageEntity.setToUser(userServiceLocal.read(5));
        messageEntity.setNalogRelated(nalogServiceLocal.read(2));
        messageEntity.setZahtevekRelated(zahtevekServiceLocal.read(2));
        //messageServiceLocal.create(messageEntity);

        MessageEntity messageEntity1 = new MessageEntity();
        messageEntity1.setContent("message two");
        messageEntity1.setFromUser(userServiceLocal.read(5));
        messageEntity1.setToUser(userServiceLocal.read(3));
        messageEntity1.setNalogRelated(nalogServiceLocal.read(2));
        messageEntity1.setZahtevekRelated(zahtevekServiceLocal.read(2));
        //messageServiceLocal.create(messageEntity1);

        MessageEntity messageEntity2 = new MessageEntity();
        messageEntity2.setContent("message three");
        messageEntity2.setFromUser(userServiceLocal.read(5));
        messageEntity2.setToUser(userServiceLocal.read(3));
        messageEntity2.setNalogRelated(nalogServiceLocal.read(2));
        messageEntity2.setZahtevekRelated(zahtevekServiceLocal.read(2));
        //messageServiceLocal.create(messageEntity2);
        pw.println("related to nalog 2");
        for(MessageEntity inc : messageServiceLocal.getRelatedToNalog(2)){
            pw.println(inc.getContent());
        }
        pw.println("related to zahtevek 3");
        for(MessageEntity out : messageServiceLocal.getRelatedToZahtevek(3)){
            pw.println(out.getContent());
        }












    }
}
